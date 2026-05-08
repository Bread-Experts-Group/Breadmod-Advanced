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
import org.bread_experts_group.ffi.getLookup
import org.bread_experts_group.ffi.globalArena
import org.bread_experts_group.ffi.nativeLinker
import org.bread_experts_group.generic.FlagSet

class PhysXToolItem : Item(Properties().stacksTo(1).rarity(Rarity.UNCOMMON)), IMouseItem {
	var ok = false
	override fun onItemUseFirst(stack: ItemStack, context: UseOnContext): InteractionResult {
		if (ok) return InteractionResult.PASS
		ok = true
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
			nativeLinker, globalArena,
			PX_PHYSICS_VERSION, PhysXAllocatorCallback.Standard,
			PhysXErrorCallback.Standard(LogManager.getLogger("PhysX"))
		)!!
		val pvd = physxLibrary.pxCreatePvd(foundation)
//		val transport = physxLibrary.pxDefaultPvdFileTransportCreate(globalArena, "TEST.pvd")
		val transport = physxLibrary.pxDefaultPvdSocketTransportCreate(globalArena, "127.0.0.1", 5425, 10u)
		println(pvd.connect(globalArena, transport, FlagSet.of(*PxPvdInstrumentationFlag.entries.toTypedArray())))
		val physics = physxLibrary.pxCreatePhysics(
			globalArena,
			PX_PHYSICS_VERSION, foundation, PhysXTolerancesScale(), true, pvd
		)!!
		println(physics)
		Thread.sleep(10000)
		physics.release()
		foundation.release()
		return InteractionResult.SUCCESS
	}
}