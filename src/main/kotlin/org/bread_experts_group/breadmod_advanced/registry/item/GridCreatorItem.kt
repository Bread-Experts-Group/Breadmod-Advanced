package org.bread_experts_group.breadmod_advanced.registry.item

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.player.LocalPlayer
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.neoforged.neoforge.client.event.InputEvent.MouseScrollingEvent
import org.bread_experts_group.breadmod.registry.item.IMouseItem

class GridCreatorItem : Item(Properties().stacksTo(1).rarity(Rarity.UNCOMMON)), IMouseItem {
	override fun onMouseScroll(
		scrollingEvent: MouseScrollingEvent,
		heldStack: ItemStack,
		level: ClientLevel,
		player: LocalPlayer
	) {
		println("test")
		super.onMouseScroll(scrollingEvent, heldStack, level, player)
	}
}