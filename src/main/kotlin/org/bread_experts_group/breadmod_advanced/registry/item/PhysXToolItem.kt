package org.bread_experts_group.breadmod_advanced.registry.item

import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.context.UseOnContext
import org.apache.logging.log4j.LogManager
import org.bread_experts_group.api.system.device.SystemDeviceFeatures
import org.bread_experts_group.breadmod.registry.item.IMouseItem
import org.bread_experts_group.breadmod_advanced.system_native.*
import org.bread_experts_group.breadmod_advanced.system_native.PhysXQuatT.ReadWrite.Companion.PxIdentityF
import org.bread_experts_group.breadmod_advanced.system_native.PxPhysicsVersion.PX_PHYSICS_VERSION
import org.bread_experts_group.ffi.autoArena
import org.bread_experts_group.ffi.getLookup
import org.bread_experts_group.ffi.globalArena
import org.bread_experts_group.ffi.nativeLinker
import org.bread_experts_group.generic.FlagSet
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType

class PhysXToolItem : Item(Properties().stacksTo(1).rarity(Rarity.UNCOMMON)), IMouseItem {
	var ok = false
	override fun onItemUseFirst(stack: ItemStack, context: UseOnContext): InteractionResult {
		if (ok) return InteractionResult.PASS
		ok = true
		val logger = LogManager.getLogger("PhysXToolItem")
		globalArena.getLookup(
			Directories.physX.get(SystemDeviceFeatures.PATH_APPEND).append("PhysXGpu_64.dll")
				.get(SystemDeviceFeatures.PATH).element
		)!!
		globalArena.getLookup(
			Directories.physX.get(SystemDeviceFeatures.PATH_APPEND).append("PVDRuntime_64.dll")
				.get(SystemDeviceFeatures.PATH).element
		)!!
		globalArena.getLookup(
			Directories.physX.get(SystemDeviceFeatures.PATH_APPEND).append("PhysXCommon_64.dll")
				.get(SystemDeviceFeatures.PATH).element
		)!!
		globalArena.getLookup(
			Directories.physX.get(SystemDeviceFeatures.PATH_APPEND).append("PhysXCooking_64.dll")
				.get(SystemDeviceFeatures.PATH).element
		)!!

		val foundationLibrary = PhysXFoundationLibrary(
			globalArena.getLookup(
				Directories.physX.get(SystemDeviceFeatures.PATH_APPEND).append("PhysXFoundation_64.dll")
					.get(SystemDeviceFeatures.PATH).element
			)!!,
			nativeLinker
		)
		val physxLibrary = PhysXLibrary(
			globalArena.getLookup(
				Directories.physX.get(SystemDeviceFeatures.PATH_APPEND).append("PhysX_64.dll")
					.get(SystemDeviceFeatures.PATH).element
			)!!,
			nativeLinker
		)
		val foundation = foundationLibrary.pxCreateFoundation(
			globalArena,
			PX_PHYSICS_VERSION, PhysXAllocatorCallback.Standard,
			PhysXErrorCallback.Standard(LogManager.getLogger("PhysX"))
		)!!
		val pvd = physxLibrary.pxCreatePvd(foundation)
//		val transport = physxLibrary.pxDefaultPvdFileTransportCreate(globalArena, "TEST.pvd")
		val transport = physxLibrary.pxDefaultPvdSocketTransportCreate(globalArena, "127.0.0.1", 5425, 10u)
		if (pvd.connect(globalArena, transport, FlagSet.of(*PxPvdInstrumentationFlag.entries.toTypedArray()))) {
			logger.info("PVD Connected")
		}
		val physics = physxLibrary.pxCreatePhysics(
			globalArena,
			PX_PHYSICS_VERSION, foundation, PhysXTolerancesScale.ReadWrite(), true, pvd
		)!!
		val dispatcher = physxLibrary.pxDefaultCpuDispatcherCreate(globalArena, 4u)
		val cuda = physxLibrary.pxCreateCudaContextManager(globalArena, foundation, PhysXCudaContextManagerDesc())
		val sceneDesc = PhysXSceneDesc(physics.getTolerancesScale())
		sceneDesc.filterShader = MethodHandles.publicLookup().bind(
			physxLibrary,
			"pxDefaultSimulationFilterShader",
			MethodType.methodType(
				MemorySegment::class.java,
				MemorySegment::class.java,
				Int::class.java, MemorySegment::class.java,
				Int::class.java, MemorySegment::class.java,
				MemorySegment::class.java,
				MemorySegment::class.java, Int::class.java
			)
		)
		sceneDesc.cpuDispatcher = Pointer(dispatcher)
		sceneDesc.cudaContextManager = Pointer(cuda)
		sceneDesc.gravity = PhysXVec3T.ReadWrite(0f, -9.81f, 0f)
		sceneDesc.flags = FlagSet.of(
			PxSceneFlag.eENABLE_GPU_DYNAMICS,
			PxSceneFlag.eENABLE_PCM,
			PxSceneFlag.eENABLE_STABILIZATION
		).maskI.toUInt()
		sceneDesc.broadPhaseType = PxBroadPhaseType.eGPU
		val scene = physics.createScene(sceneDesc)
		val materialPtr = physics.createMaterial(0.5f, 0.5f, 0.6f)
		val groundPlane = PxCreatePlane(
			physics,
			PhysXPlane.ReadWrite(
				PhysXVec3T.ReadWrite(0f, 1f, 0f)
			),
			materialPtr
		)
		scene.addActor(groundPlane)
		var stackZ = 10f
		repeat(1) {
			println("${it + 1} / 40")
			val halfExtent = 1f
			val size = 20
			val materials = autoArena.allocate(ValueLayout.ADDRESS)
			materials.set(ValueLayout.ADDRESS, 0, materialPtr)
			val shape = physics.createShape(
				PhysXBoxGeometry.ReadWrite(
					PhysXVec3T.ReadWrite(halfExtent, halfExtent, halfExtent)
				),
				materials,
				1u
			)
			val t = PhysXTransformT.ReadWrite(
				PxIdentityF,
				PhysXVec3T.ReadWrite(0f, 0f, stackZ)
			)
			stackZ -= 10f
			repeat(size) { i ->
				repeat(size - i) { j ->
					val localTm = PhysXTransformT.ReadWrite(
						PhysXVec3T.ReadWrite(
							(j * 2f) - (size-i),
							(i * 2f + 1f),
							0f
						) * halfExtent
					)
					val body = physics.createRigidDynamic(t.transform(localTm))
					body.attachShape(shape)
//					updateMassAndInertia(body, 10)
					scene.addActor(body)
				}
			}
			shape.release()
		}

		fun createDynamic(
			t: PxTransform_t,
			geometry: PhysXGeometry,
			velocity: PxVec3_t = PhysXVec3T.ReadWrite(0f, 0f, 0f)
		): PhysXRigidDynamic {
			val dynamic = PxCreateDynamic(physics, t, geometry, materialPtr, 10f)
			dynamic.setAngularDamping(0.5f)
			dynamic.setLinearVelocity(velocity)
			scene.addActor(dynamic)
			return dynamic
		}
		val ball = createDynamic(
			PhysXTransformT.ReadWrite(
				PhysXVec3T.ReadWrite(0f, 20f, 100f)
			),
			PhysXSphereGeometry.ReadWrite(5f),
			PhysXVec3T.ReadWrite(0f, -25f, -100f)
		)
		ball.setMass(5000f)
		// updateMassAndInertia(ball, 1000f)

		repeat(600) {
			val a = scene.simulate(1f / 60)
			println("$it: a $a")
			val b = scene.fetchResults(true)
			println("$it: b $b")
			Thread.sleep(1000 / 60)
		}
		logger.info("Tearing down")
		scene.release()
		physics.release()
		foundation.release()
		return InteractionResult.SUCCESS
	}
}