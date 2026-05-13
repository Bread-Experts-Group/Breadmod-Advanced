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
		val physicsPtr = physxLibrary.pxCreatePhysics(
			globalArena,
			PX_PHYSICS_VERSION, foundation, PhysXTolerancesScale.ReadWrite(), true, pvd
		)!!
		val physics = PhysXPhysics::class.java.image(nativeLinker, physicsPtr)
		val dispatcher = physxLibrary.pxDefaultCpuDispatcherCreate(globalArena, 4u)
		val cuda = physxLibrary.pxCreateCudaContextManager(globalArena, foundation, PhysXCudaContextManagerDesc())
		val sceneDesc = PhysXSceneDesc(physics.getTolerancesScale())
		sceneDesc.filterShader = MethodHandles.publicLookup().bind(
			physxLibrary,
			"pxDefaultSimulationFilterShader",
			MethodType.methodType(
				Short::class.java,
				Int::class.java, MemorySegment::class.java,
				Int::class.java, MemorySegment::class.java,
				MemorySegment::class.java,
				MemorySegment::class.java, Int::class.java
			)
		)
		sceneDesc.cpuDispatcher = Pointer(dispatcher)
		sceneDesc.cudaContextManager = Pointer(cuda)
		sceneDesc.gravity = PxVec3_t(0f, -9.81f, 0f)
		sceneDesc.flags = FlagSet.of(
			PxSceneFlag.eENABLE_GPU_DYNAMICS,
			PxSceneFlag.eENABLE_PCM,
			PxSceneFlag.eENABLE_STABILIZATION
		).maskI.toUInt()
		sceneDesc.broadPhaseType = PxBroadPhaseType.eGPU
		val scene = PhysXScene::class.java.image(
			nativeLinker,
			physics.createScene(
				cppAnalyze(sceneDesc).allocate(globalArena, nativeLinker)
			)
		)
		val materialPtr = physics.createMaterial(0.5f, 0.5f, 0.6f)
		val groundPlane = physxLibrary.pxCreatePlane(
			physicsPtr,
			cppAnalyze(
				PhysXPlane.ReadWrite(
					PxVec3_t(0f, 1f, 0f),
					0f
				)
			).allocate(globalArena, nativeLinker),
			materialPtr
		)!!
		scene.addActor(groundPlane)
		repeat(40) {
			val halfExtent = 1f
			val size = 20
			val materials = autoArena.allocate(ValueLayout.ADDRESS)
			materials.set(ValueLayout.ADDRESS, 0, materialPtr)
			val flags = autoArena.allocate(1)
			flags.set(ValueLayout.JAVA_BYTE, 0, FlagSet.of(
				PxShapeFlag.eVISUALIZATION,
				PxShapeFlag.eSCENE_QUERY_SHAPE,
				PxShapeFlag.eSIMULATION_SHAPE
			).maskB)
			val shapePtr = physics.createShape(
				cppAnalyze(
					PhysXBoxGeometry.ReadWrite(
						PxVec3_t(halfExtent, halfExtent, halfExtent)
					)
				).allocate(globalArena, nativeLinker),
				materials,
				1u,
				shapeFlags = flags
			)
			println(shapePtr)
			repeat(size) { i ->
				repeat(size - i) { j ->
					// t PxTransform(PxVec3(0,0,stackZ-=10.0f)), size 20, 1.0f
					// PxTransform localTm(PxVec3(PxReal(j*2) - PxReal(size-i), PxReal(i*2+1), 0) * halfExtent);
//					val bodyPtr = physics.createRigidDynamic(t.transform(localTm))
//					body->attachShape(*shape);
//					PxRigidBodyExt::updateMassAndInertia(*body, 10.0f);
//					gScene->addActor(*body);
				}
			}
//			shape.release()
		}
		repeat(60) {
			scene.simulate(1f / 60)
			scene.fetchResults(true)
			Thread.sleep(1000 / 60)
		}
		logger.info("Tearing down")
		scene.release()
		physics.release()
		foundation.release()
		return InteractionResult.SUCCESS
	}
}