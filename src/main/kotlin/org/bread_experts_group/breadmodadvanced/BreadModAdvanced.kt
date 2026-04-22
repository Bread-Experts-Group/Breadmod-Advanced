package org.bread_experts_group.breadmodadvanced

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.loading.FMLLoader
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.log4j.core.config.ConfigurationFactory
import org.apache.logging.log4j.core.config.Configurator
import org.bread_experts_group.breadmodadvanced.registry.ModContent

@Mod(BreadModAdvanced.ID)
class BreadModAdvanced(eventBus: IEventBus, container: ModContainer) {
    companion object {
        const val ID: String = "breadmodadvanced"
        private val logger: Logger = LogManager.getLogger("BreadModAdvanced Main")
    }

    init {
        if (!FMLLoader.isProduction() || System.getProperty("breadmod.logging") == "true") {
            val context = LogManager.getContext(false) as LoggerContext
            val fileLocator = this::class.java.getResource("/log4j2.xml")?.toURI()
                ?: throw IllegalStateException("Failed to load log4j2.xml")
            val configuration = ConfigurationFactory
                .getInstance()
                .getConfiguration(
                    context,
                    context.name,
                    fileLocator,
                    null
                )
            val colorAppender = ConsoleColorAppender.createAppender("ConsoleColorAppender", null)
            configuration.addAppender(colorAppender)
            Configurator.reconfigure(configuration)
        }
        Companion.logger.info("Hello from BreadModAdvanced!")

        Companion.logger.info("Registering Blocks & Items")
        for (registry in ModContent) registry.register(eventBus)
    }
}
