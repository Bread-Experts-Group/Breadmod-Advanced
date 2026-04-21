package org.bread_experts_group.breadmod_advanced.block

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import org.bread_experts_group.breadmod_advanced.BreadModAdvanced

object ModBlocks {
    val REGISTRY: DeferredRegister.Blocks = DeferredRegister.createBlocks(BreadModAdvanced.ID)

    val EXAMPLE_BLOCK: DeferredBlock<Block> = REGISTRY.register("example_block") { ->
        Block(BlockBehaviour.Properties.of().lightLevel { 15 }.strength(3.0f))
    }
}
