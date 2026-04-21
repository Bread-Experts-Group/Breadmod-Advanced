package org.bread_experts_group.breadmod_advanced

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bread_experts_group.breadmod_advanced.block.ModBlocks

class BreadModAdvanced(eventBus: IEventBus, container: ModContainer) {
    companion object {
        const val ID: String = "breadmodadvanced"
        private val logger: Logger = LogManager.getLogger("BreadModAdvanced Main")
    }

    init {
        ModBlocks.REGISTRY.register(eventBus)
    }
}
