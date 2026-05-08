package org.bread_experts_group.breadmod_advanced.preload_agent

import java.lang.classfile.ClassFile
import java.lang.classfile.CodeModel
import java.lang.classfile.MethodModel
import java.lang.constant.ClassDesc
import java.lang.constant.ConstantDesc
import java.lang.constant.ConstantDescs
import java.lang.constant.MethodTypeDesc
import java.lang.instrument.ClassFileTransformer
import java.lang.instrument.Instrumentation
import java.nio.file.Files
import java.security.ProtectionDomain
import kotlin.io.path.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.name

object Agent {
	@JvmStatic
	private var executed = false

	@JvmStatic
	fun premain(agentArgs: String?, instrumentation: Instrumentation) {
		if (this.executed) return
		this.executed = true
		println("=== Quick-play parameter configuration")
		println("1. Singleplayer")
		println("2. Multiplayer")
		println("3. No quick-play")
		var prompt = ""
		var line = ""
		while (true) {
			val read = System.`in`.read()
			if (read == -1) break
			val character = Char(read)
			if (character.isWhitespace()) {
				when (line) {
					"1" -> prompt = "singleplayer"
					"2" -> prompt = "multiplayer"
					"3" -> break
					else -> {}
				}
				line = ""
			} else line += Char(read)
			if (prompt != "") break
		}
		val quickPlay = when (prompt) {
			"" -> null
			"singleplayer" -> {
				println("=== World")
				val worlds = Files.list(Path("./saves")).toList()
					.filter { it.isDirectory() }
					.map { it.name }
					.onEachIndexed {
					index, path ->
					println("$index. $path")
				}
				println("${worlds.size}. No quick-play")
				prompt = ""
				line = ""
				var computed: QuickPlayKind? = null
				while (true) {
					val read = System.`in`.read()
					if (read == -1) break
					val character = Char(read)
					if (character.isWhitespace()) {
						val n = line.toIntOrNull()
						if (n != null) when (n) {
							in worlds.indices -> {
								computed = SingleplayerWorld(worlds[n])
								break
							}

							worlds.size -> break
							else -> {}
						}
						line = ""
					} else line += Char(read)
				}
				computed
			}

			"multiplayer" -> {
				println("=== Server")
				TODO("@")
			}

			else -> throw IllegalStateException(prompt)
		}

		val cf = ClassFile.of()
		instrumentation.addTransformer(
			object : ClassFileTransformer {
				val quickPlay = quickPlay

				override fun transform(
					module: Module?,
					loader: ClassLoader?,
					className: String,
					classBeingRedefined: Class<*>?,
					protectionDomain: ProtectionDomain?,
					classfileBuffer: ByteArray
				): ByteArray? {
					if (className == "net/minecraft/client/main/Main" && this.quickPlay != null) {
						return cf.transformClass(
							cf.parse(classfileBuffer)
						) { classBuilder, classElement ->
							if (
								classElement is MethodModel &&
								classElement.methodName().equalsString("main")
							) {
								classBuilder.transformMethod(classElement) { methodBuilder, methodElement ->
									if (methodElement is CodeModel) {
										var adjustedEntry = false
										methodBuilder.transformCode(
											methodElement
										) { codeBuilder, codeElement ->
											if (!adjustedEntry) {
												codeBuilder
													.aload(0)
													.dup()
													.arraylength()
													.bipush(2)
													.iadd()
													.invokestatic(
														ClassDesc.of("java.util.Arrays"),
														"copyOf",
														MethodTypeDesc.of(
															ConstantDescs.CD_Object.arrayType(),
															ConstantDescs.CD_Object.arrayType(),
															ConstantDescs.CD_int
														)
													)
													.checkcast(
														ConstantDescs.CD_String.arrayType()
													)
												@Suppress("CAST_NEVER_SUCCEEDS")
												when (quickPlay) {
													is SingleplayerWorld -> {
														codeBuilder
															.dup()
															.dup()
															.arraylength()
															.bipush(2)
															.isub()
															.loadConstant("--quickPlaySingleplayer" as ConstantDesc)
															.aastore()
															.dup()
															.dup()
															.arraylength()
															.bipush(1)
															.isub()
															.loadConstant(quickPlay.name as ConstantDesc)
															.aastore()
													}

													else -> throw IllegalStateException()
												}
												codeBuilder.astore(0)
												adjustedEntry = true
											} else codeBuilder.with(codeElement)
										}
									} else methodBuilder.with(methodElement)
								}
							} else classBuilder.with(classElement)
						}
					}
					return null
				}
			}
		)
	}
}