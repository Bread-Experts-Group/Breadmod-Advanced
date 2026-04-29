package org.bread_experts_group.breadmod_advanced.registry.item

import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.context.UseOnContext
import org.bread_experts_group.api.system.SystemFeatures
import org.bread_experts_group.api.system.SystemProvider
import org.bread_experts_group.api.system.device.SystemDeviceFeatures
import org.bread_experts_group.api.system.io.IODevice
import org.bread_experts_group.api.system.io.IODeviceFeatures
import org.bread_experts_group.api.system.io.open.FileIOOpenFeatures
import org.bread_experts_group.api.system.io.open.FileIOReOpenFeatures
import org.bread_experts_group.api.system.io.open.StandardIOOpenFeatures
import org.bread_experts_group.breadmod.BreadMod
import org.bread_experts_group.breadmod.registry.item.IMouseItem
import org.bread_experts_group.breadmod_advanced.BreadModAdvanced
import org.bread_experts_group.breadmod_advanced.println
import org.bread_experts_group.ffi.getLookup
import org.bread_experts_group.generic.io.reader.BSLWriter
import java.lang.foreign.Arena
import kotlin.io.path.Path

class PhysXToolItem : Item(Properties().stacksTo(1).rarity(Rarity.UNCOMMON)), IMouseItem {
	override fun onItemUseFirst(stack: ItemStack, context: UseOnContext): InteractionResult {
		val file = "libs/ovphysx.dll"
		val dll = PhysXToolItem::class.java.classLoader.getResourceAsStream(file)!!
		val dllDisk = Path(BreadMod.ID).resolve(BreadModAdvanced.ID).resolve(file)
		val pathDevice = SystemProvider.get(SystemFeatures.GET_CURRENT_WORKING_PATH_DEVICE).device
			.get(SystemDeviceFeatures.PATH_APPEND).append(BreadMod.ID)
			.get(SystemDeviceFeatures.PATH_APPEND).append(BreadModAdvanced.ID)
			.get(SystemDeviceFeatures.PATH_APPEND).append(file)
		val ioDeviceStatus = pathDevice.get(SystemDeviceFeatures.IO_DEVICE).open(
			StandardIOOpenFeatures.CREATE, FileIOOpenFeatures.TRUNCATE,
			FileIOReOpenFeatures.WRITE
		)
		val ioDevice = ioDeviceStatus.firstNotNullOfOrNull { it as? IODevice }
		if (ioDevice == null) {
			println("Couldn't download the DLL. Status: $ioDeviceStatus")
			return InteractionResult.FAIL
		}
		val writer = BSLWriter(ioDevice.get(IODeviceFeatures.WRITE))
		while (true) {
			val byte = dll.read()
			if (byte == -1) break
			writer.write8i(byte)
		}
		writer.flush()
		println(Arena.ofAuto().getLookup(dllDisk.toString()))
		return super.onItemUseFirst(stack, context)
	}
}