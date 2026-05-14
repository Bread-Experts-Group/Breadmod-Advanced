package org.bread_experts_group.breadmod_advanced.system_native

import org.bread_experts_group.breadmod_advanced.system_native.AllocatorAnchor.cppAnalyze
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.int
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.`void*`
import org.bread_experts_group.ffi.autoArena
import org.bread_experts_group.generic.Flaggable
import java.io.File
import java.lang.classfile.ClassFile
import java.lang.classfile.Opcode
import java.lang.classfile.TypeKind
import java.lang.constant.ClassDesc
import java.lang.constant.ConstantDescs
import java.lang.constant.MethodTypeDesc
import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.lang.reflect.AccessFlag
import java.lang.reflect.Constructor
import java.lang.reflect.Method
import kotlin.reflect.KCallable
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.jvm.javaGetter
import kotlin.reflect.jvm.javaSetter

data class ObjectAnalysis<T : Any>(
	val of: T,
	val vtable: Collection<Method>,
	val positions: Map<Long, ObjectAnalysis<*>>,
	val size: Long,
	val alignment: Long
) {
	constructor(of: T, size: Long) : this(
		of,
		emptyList(), emptyMap(),
		size, size
	)
}

fun align(n: Long, to: Long): Long = ((n / to) + (if ((n % to) != 0L) 1 else 0)) * to

@Suppress("unused")
object AllocatorAnchor {
	@JvmStatic
	fun layout(clazz: Class<*>): MemoryLayout = clazz.layout

	@JvmStatic
	fun <T : Flaggable> selectFlag(flags: Array<T>, mask: Long): T? = flags.firstOrNull { mask and it.position != 0L }

	@JvmStatic
	fun <T> image(clazz: Class<T>, linker: Linker, segment: MemorySegment): T = clazz.image(linker, segment)

	@JvmStatic
	fun <T : Any> cppAnalyze(
		v: T
	): ObjectAnalysis<T> = when (v) {
		is Byte, is UByte -> ObjectAnalysis(v, 1)
		is Short, is UShort, is Char -> ObjectAnalysis(v, 2)
		is Int, is UInt, is Float -> ObjectAnalysis(v, 4)
		is Long, is ULong, is Double -> ObjectAnalysis(v, 8)
		is Pointer<*>, is Array<*>, is MemorySegment, is MethodHandle -> ObjectAnalysis(v, `void*`.byteSize())
		else if v::class.java.isEnum -> ObjectAnalysis(v, 4) // TODO: enum may be larger
		else -> {
			val classes = mutableListOf<Class<*>>()
			var clazz: Class<*>? = v::class.java
			while (clazz != null) {
				classes.addFirst(clazz)
				clazz = clazz.superclass
			}
			val fields = sortedMapOf<Long, KCallable<*>>()
			val methods = sortedMapOf<Long, Method>()
			var fieldsOffset = 0L
			var methodsOffset = 0L
			for (clazz in classes) {
				val localFields = mutableMapOf<Long, KCallable<*>>()
				for (field in clazz.kotlin.declaredMembers) {
					val index = field.annotations.firstNotNullOfOrNull { it as? DefinedProperty }?.index ?: continue
					val precursor = localFields.put(index, field)
					if (precursor != null) throw IllegalArgumentException("$field @ $index already exists by $precursor")
					if (index < 0) throw ArrayIndexOutOfBoundsException("$field @ $index must be >= 0")
				}
				for ((index, field) in localFields)
					if (index > localFields.size) throw ArrayIndexOutOfBoundsException("$field @ $index > ${localFields.size}")
				val localMethods = mutableMapOf<Long, Method>()
				for (method in clazz.declaredMethods) {
					val index = (method.getAnnotation(VirtualFunction::class.java)?.index ?: continue)
					val precursor = localMethods.put(index, method)
					if (precursor != null) throw IllegalArgumentException("$method @ $index already exists by $precursor")
					if (index < 0) throw ArrayIndexOutOfBoundsException("$method @ $index must be >= 0")
				}
				for ((index, method) in localMethods)
					if (index > localMethods.size) throw ArrayIndexOutOfBoundsException("$method @ $index > ${localMethods.size}")
				for ((index, field) in localFields) fields[index + fieldsOffset] = field
				for ((index, method) in localMethods) methods[index + methodsOffset] = method
				fieldsOffset += localFields.size
				methodsOffset += localMethods.size
			}

			var alignment = 0L
			var offset = if (methods.isNotEmpty()) {
				alignment = `void*`.byteAlignment()
				`void*`.byteSize()
			} else 0L
			val properties = mutableMapOf<Long, ObjectAnalysis<*>>()
			for ((_, field) in fields) {
				val value = field.call(v) ?: throw IllegalArgumentException("Property $field can not be null")
				val analysis = cppAnalyze(value)
				if (analysis.alignment > alignment) alignment = analysis.alignment
				offset = align(offset, analysis.alignment)
				properties[offset] = analysis
				offset += analysis.size
			}
			ObjectAnalysis(v, methods.values, properties, offset, alignment)
		}
	}

	@JvmStatic
	fun allocate(analysis: ObjectAnalysis<*>, arena: Arena, linker: Linker): MemorySegment = analysis.allocate(arena, linker)
}

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
val Class<*>.layout: MemoryLayout
	get() = when (this) {
		MemorySegment::class.java -> ValueLayout.ADDRESS
		Long::class.java -> ValueLayout.JAVA_LONG
		Float::class.java, java.lang.Float::class.java -> ValueLayout.JAVA_FLOAT
		Int::class.java, Integer::class.java -> ValueLayout.JAVA_INT
		Short::class.java, java.lang.Short::class.java -> ValueLayout.JAVA_SHORT
		Byte::class.java, java.lang.Byte::class.java -> ValueLayout.JAVA_BYTE
		Boolean::class.java, java.lang.Boolean::class.java -> ValueLayout.JAVA_BOOLEAN
		else if this.isEnum -> int // TODO: Could be another type if larger
		else -> throw IllegalArgumentException("Unsupported type $this")
	}

val Class<*>.nativePrimitive: Boolean
	get() = this.typeKind != TypeKind.REFERENCE || this.isEnum || this == MemorySegment::class.java

fun composeUpcall(
	arena: Arena, linker: Linker,
	handle: MethodHandle//, method: Method? = null
): MemorySegment {
	val type = handle.type()
	val parameterTypes = type.parameterArray()
	var handle = handle
//	if (method != null) method.parameters.forEachIndexed { i, parameter ->
//		println(parameter.parameterizedType)
//	}
	parameterTypes.forEachIndexed { i, parameter ->
		if (parameter.isEnum) {
			val filter = if (Flaggable::class.java.isAssignableFrom(parameter)) {
				MethodHandles.explicitCastArguments(
					MethodHandles.insertArguments(
						MethodHandles.lookup().findStatic(
							AllocatorAnchor::class.java, "selectFlag",
							MethodType.methodType(
								Flaggable::class.java,
								Array<Flaggable>::class.java, Long::class.java
							)
						),
						0, parameter.enumConstants
					),
					MethodType.methodType(
						parameter,
						Int::class.java
					)
				)
			} else {
				val linear = parameter.enumConstants
				MethodHandles.insertArguments(
					MethodHandles.arrayElementGetter(linear::class.java),
					0, linear
				)
			}
			handle = MethodHandles.filterArguments(
				handle, i,
				filter
			)
		}
	}
	val resultLayout = if (type.returnType() == Void.TYPE) null else type.returnType().layout
	val parameterLayouts = parameterTypes.map { it.layout }.toTypedArray()
	return linker.upcallStub(
		handle,
		if (resultLayout != null) FunctionDescriptor.of(resultLayout, *parameterLayouts)
		else FunctionDescriptor.ofVoid(*parameterLayouts),
		arena
	)
}

fun getPublicMethodHandle(method: Method, receiver: Any): MethodHandle = MethodHandles.publicLookup().bind(
	receiver,
	method.name,
	MethodType.methodType(
		method.returnType,
		method.parameterTypes
	)
)

fun ObjectAnalysis<*>.allocate(
	arena: Arena,
	linker: Linker
): MemorySegment {
	val segment = arena.allocate(this.size, this.alignment)
	if (this.vtable.isNotEmpty()) {
		val vtbl = arena.allocate(`void*`, this.vtable.size.toLong())
		var i = 0L
		for (method in this.vtable) {
			val linked = composeUpcall(
				arena, linker,
				getPublicMethodHandle(method, this.of)
			)
			vtbl.setAtIndex(`void*`, i++, linked)
		}
		segment.set(`void*`, 0, vtbl)
	}
	for ((position, analysis) in this.positions) {
		when (val value = analysis.of) {
			is Byte -> segment.set(ValueLayout.JAVA_BYTE, position, value)
			is UByte -> segment.set(ValueLayout.JAVA_BYTE, position, value.toByte())
			is Short -> segment.set(ValueLayout.JAVA_SHORT, position, value)
			is UShort -> segment.set(ValueLayout.JAVA_SHORT, position, value.toShort())
			is Int -> segment.set(ValueLayout.JAVA_INT, position, value)
			is UInt -> segment.set(ValueLayout.JAVA_INT, position, value.toInt())
			is Long -> segment.set(ValueLayout.JAVA_LONG, position, value)
			is ULong -> segment.set(ValueLayout.JAVA_LONG, position, value.toLong())
			is Float -> segment.set(ValueLayout.JAVA_FLOAT, position, value)
			is Double -> segment.set(ValueLayout.JAVA_DOUBLE, position, value)
			is MemorySegment -> segment.set(ValueLayout.ADDRESS, position, value)

			is Pointer<*> -> {
				val deref = value.`*`
				if (deref is SegmentExposed) {
					segment.set(ValueLayout.ADDRESS, position, deref.segment)
				} else if (deref != null) {
					val distant = cppAnalyze(deref).allocate(arena, linker)
					segment.set(ValueLayout.ADDRESS, position, distant)
				}
			}

			is MethodHandle -> segment.set(
				ValueLayout.ADDRESS, position,
				composeUpcall(
					arena, linker,
					value
				)
			)

			else if value::class.java.isEnum -> segment.set(
				ValueLayout.JAVA_INT, position,
				(value as Enum<*>).ordinal
			) // TODO: Enum may be larger
			else -> {
				val distant = analysis.allocate(autoArena, linker)
				MemorySegment.copy(distant, 0L, segment, position, distant.byteSize())
			}
		}
	}
	return segment
}

val Class<*>.classDesc: ClassDesc
	get() = when (this) {
		Void.TYPE -> ConstantDescs.CD_void
		Boolean::class.java -> ConstantDescs.CD_boolean
		Byte::class.java -> ConstantDescs.CD_byte
		Char::class.java -> ConstantDescs.CD_char
		Short::class.java -> ConstantDescs.CD_short
		Int::class.java -> ConstantDescs.CD_int
		Long::class.java -> ConstantDescs.CD_long
		Float::class.java -> ConstantDescs.CD_float
		Double::class.java -> ConstantDescs.CD_double
		else -> ClassDesc.ofInternalName(this.name.replace('.', '/'))
	}

val Class<*>.boxedClassDesc: ClassDesc
	get() = when (this) {
		Void.TYPE -> ConstantDescs.CD_Void
		Boolean::class.java -> ConstantDescs.CD_Boolean
		Byte::class.java -> ConstantDescs.CD_Byte
		Char::class.java -> ConstantDescs.CD_Character
		Short::class.java -> ConstantDescs.CD_Short
		Int::class.java -> ConstantDescs.CD_Integer
		Long::class.java -> ConstantDescs.CD_Long
		Float::class.java -> ConstantDescs.CD_Float
		Double::class.java -> ConstantDescs.CD_Double
		else -> ClassDesc.ofInternalName(this.name.replace('.', '/'))
	}

val Class<*>.typeKind: TypeKind
	get() = when (this) {
		Void.TYPE -> TypeKind.VOID
		Boolean::class.java -> TypeKind.BOOLEAN
		Byte::class.java -> TypeKind.BYTE
		Char::class.java -> TypeKind.CHAR
		Short::class.java -> TypeKind.SHORT
		Int::class.java -> TypeKind.INT
		Long::class.java -> TypeKind.LONG
		Float::class.java -> TypeKind.FLOAT
		Double::class.java -> TypeKind.DOUBLE
		else -> TypeKind.REFERENCE
	}

private val imagingCache = mutableMapOf<Class<*>, Constructor<*>>()
@Suppress("UNCHECKED_CAST")
fun <T> Class<T>.image(linker: Linker, segment: MemorySegment): T {
	val cached = imagingCache[this]
	if (cached != null) return cached.newInstance(segment, linker) as T

	val implementingMethods = sortedMapOf<Long, Method>()
	val implementingProperties = sortedMapOf<Long, KProperty<*>>()
	val toImplement = mutableSetOf<String>()

	var methodOffset = 0L
	var propertyOffset = 0L
	val classes = mutableListOf<Class<*>>()
	var clazz: Class<*>? = this
	while (clazz != null) {
		classes.addFirst(clazz)
		clazz = clazz.superclass
	}
	for (clazz in classes) {
		for (method in clazz.declaredMethods) {
			val vf = method.getDeclaredAnnotation(VirtualFunction::class.java)
			if (method.accessFlags().contains(AccessFlag.ABSTRACT)) {
				if (vf == null) {
					toImplement.add(method.toGenericString())
				} else {
					val old = implementingMethods.put(vf.index + methodOffset, method)
					if (old != null) throw IllegalArgumentException(
						"$old already taking ${vf.index} + $methodOffset (${vf.index + methodOffset}), $method replacing"
					)
				}
			} else {
				if (vf != null) throw IllegalArgumentException(
					"$clazz should not implement ${method.toGenericString()} (image)"
				)
				toImplement.remove(method.toGenericString())
			}
		}
		if (implementingMethods.isNotEmpty()) methodOffset = implementingMethods.lastKey() + 1
		for (member in clazz.kotlin.declaredMemberProperties) {
			val prop = member.annotations.firstNotNullOfOrNull { it as? DefinedProperty }
			if (prop == null) continue
			val jG = member.javaGetter
			val jS = if (member is KMutableProperty<*>) member.javaSetter else null
			if (jG == null) throw IllegalArgumentException("$prop missing required Java getter")
			if (!jG.accessFlags().contains(AccessFlag.ABSTRACT)) throw IllegalArgumentException(
				"$member / $jG, Java getter must not be implemented"
			)
			if (jS != null && !jS.accessFlags().contains(AccessFlag.ABSTRACT)) throw IllegalArgumentException(
				"$member / $jS, Java setter must not be implemented"
			)
			val old = implementingProperties.put(prop.index + propertyOffset, member)
			if (old != null) throw IllegalArgumentException(
				"$old already taking ${prop.index} + $propertyOffset (${prop.index + propertyOffset}), $member replacing"
			)
		}
		if (implementingProperties.isNotEmpty()) propertyOffset = implementingProperties.lastKey() + 1
	}

	val cf = ClassFile.of()
	val className = "hidden$${this.name.substringAfterLast('.')}"
	val lThis = ClassDesc.of(this.packageName, className)
	val lSuper = ClassDesc.of(this.name)
	val data = cf.build(lThis) { builder ->
		builder.withSuperclass(lSuper)
		builder.withInterfaceSymbols(SegmentExposed::class.java.classDesc)
		builder.withFlags(
			AccessFlag.PUBLIC,
			AccessFlag.FINAL
		)
		builder.withField(
			"segment", MemorySegment::class.java.classDesc,
			AccessFlag.PRIVATE.mask() or AccessFlag.FINAL.mask()
		)
		builder.withMethodBody(
			"getSegment", MethodTypeDesc.of(MemorySegment::class.java.classDesc),
			AccessFlag.PUBLIC.mask()
		) { codeBuilder ->
			codeBuilder
				.aload(0)
				.getfield(
					lThis, "segment",
					MemorySegment::class.java.classDesc
				)
				.areturn()
		}
		builder.withField(
			"linker", Linker::class.java.classDesc,
			AccessFlag.PRIVATE.mask() or AccessFlag.FINAL.mask()
		)
		builder.withMethodBody(
			"<init>",
			MethodTypeDesc.of(
				ConstantDescs.CD_void,
				MemorySegment::class.java.classDesc,
				Linker::class.java.classDesc
			),
			AccessFlag.PUBLIC.mask()
		) { codeBuilder ->
			codeBuilder
				.aload(0)
				.invokespecial(
					lSuper, "<init>",
					MethodTypeDesc.of(ConstantDescs.CD_void)
				)
				.aload(0)
				.aload(1)
				.putfield(
					lThis, "segment",
					MemorySegment::class.java.classDesc
				)
				.aload(0)
				.aload(2)
				.putfield(
					lThis, "linker",
					Linker::class.java.classDesc
				)
			if (implementingMethods.isNotEmpty()) {
				val vfptrSlot = codeBuilder.allocateLocal(TypeKind.REFERENCE)
				codeBuilder
					.aload(1)
					.loadConstant(`void*`.byteSize())
					.invokeinterface(
						MemorySegment::class.java.classDesc, "reinterpret",
						MethodTypeDesc.of(
							MemorySegment::class.java.classDesc,
							ConstantDescs.CD_long
						)
					)
					.getstatic(
						ValueLayout::class.java.classDesc, "ADDRESS",
						AddressLayout::class.java.classDesc
					)
					.loadConstant(0L)
					.invokeinterface(
						MemorySegment::class.java.classDesc, "get",
						MethodTypeDesc.of(
							MemorySegment::class.java.classDesc,
							AddressLayout::class.java.classDesc,
							ConstantDescs.CD_long
						)
					)
					.dup()
					.loadConstant(methodOffset * `void*`.byteSize())
					.invokeinterface(
						MemorySegment::class.java.classDesc, "reinterpret",
						MethodTypeDesc.of(
							MemorySegment::class.java.classDesc,
							ConstantDescs.CD_long
						)
					)
					.astore(vfptrSlot)
				for ((index, implement) in implementingMethods) {
					builder.withField(
						"h$index",
						ConstantDescs.CD_MethodHandle,
						AccessFlag.PRIVATE.mask() or AccessFlag.FINAL.mask()
					)

					codeBuilder
						.aload(0)
						.aload(2)
					codeBuilder
						.aload(vfptrSlot)
						.getstatic(
							ValueLayout::class.java.classDesc, "ADDRESS",
							AddressLayout::class.java.classDesc
						)
						.loadConstant(index * `void*`.byteSize())
						.invokeinterface(
							MemorySegment::class.java.classDesc, "get",
							MethodTypeDesc.of(
								MemorySegment::class.java.classDesc,
								AddressLayout::class.java.classDesc,
								ConstantDescs.CD_long
							)
						)

					if (implement.returnType != Void.TYPE) {
						if (implement.returnType.nativePrimitive) codeBuilder
							.ldc(codeBuilder.constantPool().classEntry(implement.returnType.boxedClassDesc))
							.invokestatic(
								AllocatorAnchor::class.java.classDesc, "layout",
								MethodTypeDesc.of(
									MemoryLayout::class.java.classDesc,
									ConstantDescs.CD_Class
								)
							) else codeBuilder
								.getstatic(
								ValueLayout::class.java.classDesc, "ADDRESS",
								AddressLayout::class.java.classDesc
							)
					}

					val parameters = implement.parameterTypes.toMutableList()
					parameters.addFirst(MemorySegment::class.java)
					codeBuilder
						.loadConstant(parameters.size)
						.anewarray(MemoryLayout::class.java.classDesc)
					var i = 0
					for (parameter in parameters) {
						codeBuilder
							.dup()
							.loadConstant(i++)
						if (parameter.nativePrimitive) codeBuilder
							.ldc(codeBuilder.constantPool().classEntry(parameter.boxedClassDesc))
							.invokestatic(
								AllocatorAnchor::class.java.classDesc, "layout",
								MethodTypeDesc.of(
									MemoryLayout::class.java.classDesc,
									ConstantDescs.CD_Class
								)
							) else codeBuilder
							.getstatic(
								ValueLayout::class.java.classDesc, "ADDRESS",
								AddressLayout::class.java.classDesc
							)
						codeBuilder.aastore()
					}

					if (implement.returnType == Void.TYPE) {
						codeBuilder.invokestatic(
							FunctionDescriptor::class.java.classDesc, "ofVoid",
							MethodTypeDesc.of(
								FunctionDescriptor::class.java.classDesc,
								MemoryLayout::class.java.classDesc.arrayType()
							),
							true
						)
					} else codeBuilder.invokestatic(
						FunctionDescriptor::class.java.classDesc, "of",
						MethodTypeDesc.of(
							FunctionDescriptor::class.java.classDesc,
							MemoryLayout::class.java.classDesc,
							MemoryLayout::class.java.classDesc.arrayType()
						),
						true
					)

					codeBuilder
						.bipush(0)
						.anewarray(Linker.Option::class.java.classDesc)
						.invokeinterface(
							Linker::class.java.classDesc, "downcallHandle",
							MethodTypeDesc.of(
								ConstantDescs.CD_MethodHandle,
								MemorySegment::class.java.classDesc,
								FunctionDescriptor::class.java.classDesc,
								Linker.Option::class.java.classDesc.arrayType()
							)
						)
						.putfield(
							lThis,
							"h$index",
							ConstantDescs.CD_MethodHandle
						)
				}
			}
			codeBuilder.return_()
		}
		for ((index, implement) in implementingMethods) {
			val imageSegment = !implement.returnType.nativePrimitive
			val desc = MethodTypeDesc.of(
				implement.returnType.classDesc,
				*implement.parameterTypes.map { it.classDesc }.toTypedArray()
			)
			builder.withMethodBody(
				implement.name,
				desc,
				(implement.accessFlags()
					.fold(0) { mask, flag -> mask or flag.mask() })
						and AccessFlag.ABSTRACT.mask().inv()
			) { codeBuilder ->
				if (imageSegment) {
					codeBuilder
						.ldc(codeBuilder.constantPool().classEntry(implement.returnType.classDesc))
						.aload(0)
						.getfield(
							lThis, "linker",
							Linker::class.java.classDesc
						)
				}
				codeBuilder
					.aload(0)
					.getfield(
						lThis, "h$index",
						ConstantDescs.CD_MethodHandle
					)
					.aload(0)
					.getfield(
						lThis, "segment",
						MemorySegment::class.java.classDesc
					)
				var i = 0
				for (parameter in implement.parameterTypes) {
					codeBuilder.loadLocal(parameter.typeKind, ++i)
					if (!parameter.nativePrimitive) {
						codeBuilder
							.dup()
							.instanceOf(SegmentExposed::class.java.classDesc)
							.block { blockFalse ->
								blockFalse
									.block { blockTrue ->
										blockTrue
											.branch(Opcode.IFEQ, blockTrue.endLabel())
											.checkcast(SegmentExposed::class.java.classDesc)
											.invokeinterface(
												SegmentExposed::class.java.classDesc, "getSegment",
												MethodTypeDesc.of(MemorySegment::class.java.classDesc)
											)
											.goto_(blockFalse.endLabel())
									}
									.invokestatic(
										AllocatorAnchor::class.java.classDesc, "cppAnalyze",
										MethodTypeDesc.of(
											ObjectAnalysis::class.java.classDesc,
											ConstantDescs.CD_Object
										)
									)
									.invokestatic(
										Arena::class.java.classDesc, "global",
										MethodTypeDesc.of(
											Arena::class.java.classDesc
										), // TODO!!!! DONT USE GLOBAL USE TEMP CONFINED
										true
									)
									.aload(0)
									.getfield(
										lThis, "linker",
										Linker::class.java.classDesc
									)
									.invokestatic(
										AllocatorAnchor::class.java.classDesc, "allocate",
										MethodTypeDesc.of(
											MemorySegment::class.java.classDesc,
											ObjectAnalysis::class.java.classDesc,
											Arena::class.java.classDesc,
											Linker::class.java.classDesc
										)
									)
							}
					}
				}
				codeBuilder.invokevirtual(
					ConstantDescs.CD_MethodHandle, "invokeExact",
					MethodTypeDesc.of(
						if (imageSegment) MemorySegment::class.java.classDesc else desc.returnType(),
						MemorySegment::class.java.classDesc,
						*desc.parameterList()
							.map {
								if (
									it.isClassOrInterface && it != MemorySegment::class.java.classDesc
								) MemorySegment::class.java.classDesc else it
							}.toTypedArray()
					)
				)
				if (imageSegment) {
					codeBuilder
						.invokestatic(
							AllocatorAnchor::class.java.classDesc, "image",
							MethodTypeDesc.of(
								ConstantDescs.CD_Object,
								ConstantDescs.CD_Class,
								Linker::class.java.classDesc,
								MemorySegment::class.java.classDesc
							)
						)
						.checkcast(implement.returnType.classDesc)
				}
				codeBuilder.return_(implement.returnType.typeKind)
			}
		}
		var offset = if (implementingMethods.isNotEmpty()) `void*`.byteSize() else 0L
		for ((_, implement) in implementingProperties) {
			val jG = implement.javaGetter!!
			builder.withMethodBody(
				jG.name,
				MethodTypeDesc.of(
					jG.returnType.classDesc,
					*jG.parameterTypes.map { it.classDesc }.toTypedArray()
				),
				(jG.accessFlags()
					.fold(0) { mask, flag -> mask or flag.mask() })
						and AccessFlag.ABSTRACT.mask().inv()
			) { codeBuilder ->
				val size: Long
				val layout: Class<*>
				val layoutType: Class<*>
				val layoutName: String
				when (val type = jG.returnType) {
					MemorySegment::class.java -> {
						size = AddressLayout.ADDRESS.byteSize()
						layout = AddressLayout::class.java
						layoutType = MemorySegment::class.java
						layoutName = "ADDRESS"
					}

					Float::class.java -> {
						size = ValueLayout.JAVA_FLOAT.byteSize()
						layout = ValueLayout.OfFloat::class.java
						layoutType = Float::class.java
						layoutName = "JAVA_FLOAT"
					}

					Int::class.java -> {
						size = ValueLayout.JAVA_INT.byteSize()
						layout = ValueLayout.OfInt::class.java
						layoutType = Int::class.java
						layoutName = "JAVA_INT"
					}

					Short::class.java -> {
						size = ValueLayout.JAVA_SHORT.byteSize()
						layout = ValueLayout.OfShort::class.java
						layoutType = Short::class.java
						layoutName = "JAVA_SHORT"
					}

					else -> throw IllegalArgumentException("Unsupported type $type")
				}
				codeBuilder
					.aload(0)
					.getfield(
						lThis, "segment",
						MemorySegment::class.java.classDesc
					)
					.loadConstant(offset + size)
					.invokeinterface(
						MemorySegment::class.java.classDesc, "reinterpret",
						MethodTypeDesc.of(
							MemorySegment::class.java.classDesc,
							ConstantDescs.CD_long
						)
					)
					.getstatic(
						ValueLayout::class.java.classDesc, layoutName,
						layout.classDesc
					)
					.loadConstant(offset)
					.invokeinterface(
						MemorySegment::class.java.classDesc, "get",
						MethodTypeDesc.of(
							layoutType.classDesc,
							layout.classDesc,
							ConstantDescs.CD_long
						)
					)
					.return_(jG.returnType.typeKind)
				offset += size
			}
		}
	}
	File("./$className.class").writeBytes(data)
	val hidden = MethodHandles.lookup().defineClass(data)
	@Suppress("UNCHECKED_CAST")
	imagingCache[this] = hidden.constructors.first()
	return hidden.constructors.first().newInstance(segment, linker) as T
}