package org.bread_experts_group.breadmod_advanced

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bread_experts_group.breadmod_advanced.registry.ModContent

@Mod(BreadModAdvanced.ID)
class BreadModAdvanced(eventBus: IEventBus, container: ModContainer) {
    companion object {
        const val ID: String = "breadmod_advanced"
        private val logger: Logger = LogManager.getLogger("BreadModAdvanced Main")
    }

    init {
//        if (!FMLLoader.isProduction() || System.getProperty("breadmod.logging") == "true") {
//            val context = LogManager.getContext(false) as LoggerContext
//            val fileLocator = this::class.java.getResource("/log4j2.xml")?.toURI()
//                ?: throw IllegalStateException("Failed to load log4j2.xml")
//            val configuration = ConfigurationFactory
//                .getInstance()
//                .getConfiguration(
//                    context,
//                    context.name,
//                    fileLocator,
//                    null
//                )
//            val colorAppender = ConsoleColorAppender.createAppender("ConsoleColorAppender", null)
//            configuration.addAppender(colorAppender)
//            Configurator.reconfigure(configuration)
//        }
        logger.info("Hello from BreadModAdvanced!")
        logger.info("Registering Blocks & Items")
        for (registry in ModContent) registry.register(eventBus)
    }
}
