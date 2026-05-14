package org.bread_experts_group.breadmod_advanced.registry

import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem
import org.bread_experts_group.breadmod.registry.RegistryProvider
import org.bread_experts_group.breadmod_advanced.BreadModAdvanced
import org.bread_experts_group.breadmod_advanced.registry.item.GridCreatorItem
import java.util.function.Supplier

object ModContent : RegistryProvider(
	BreadModAdvanced.ID,
	Registries.ITEM,
	Registries.BLOCK,
	Registries.CREATIVE_MODE_TAB
) {
	val itemRegistry = this.getRegistry(Registries.ITEM)
	val blockRegistry = this.getRegistry(Registries.BLOCK)
	val tabRegistry = this.getRegistry(Registries.CREATIVE_MODE_TAB)

	fun <T : Item> registerItem(id: String, item: () -> T): DeferredItem<T> {
		val holder = this.itemRegistry.register(id, item)
		return DeferredItem.createItem(holder.id)
	}

	fun registerItem(id: String, properties: Item.Properties = Item.Properties()): DeferredItem<Item> {
		return this.registerItem(id) { Item(properties) }
	}

	val GRID_CREATOR: DeferredItem<Item> = this.registerItem("grid_creator", ::GridCreatorItem)

	val CREATIVE_TAB: Supplier<CreativeModeTab> = this.tabRegistry.register("breadmod_advanced") { ->
		CreativeModeTab.builder()
			.title(Component.literal("Breadmod: Advanced"))
			.displayItems { parameters, output ->
				output.accept(this.GRID_CREATOR.get())
			}
			.build()
	}
}