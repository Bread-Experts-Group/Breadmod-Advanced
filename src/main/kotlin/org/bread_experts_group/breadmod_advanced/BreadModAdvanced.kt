package org.bread_experts_group.breadmod_advanced

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bread_experts_group.api.system.SystemFeatures
import org.bread_experts_group.api.system.SystemProvider
import org.bread_experts_group.api.system.device.SystemDevice
import org.bread_experts_group.api.system.device.SystemDeviceFeatures
import org.bread_experts_group.api.system.device.copy.WindowsCopySystemDeviceFeatures
import org.bread_experts_group.breadmod.BreadMod
import org.bread_experts_group.breadmod_advanced.registry.ModContent
import org.bread_experts_group.breadmod_advanced.system_native.Directories
import org.bread_experts_group.breadmod_advanced.system_native.Directories.createDirectories
import org.bread_experts_group.upwards.UpwardsMod
import kotlin.io.path.absolutePathString
import kotlin.io.path.isDirectory
import kotlin.io.path.name

@UpwardsMod(modID = BreadModAdvanced.ID, dependencyLocation = "libs", piggybackModID = BreadMod.ID)
class BreadModAdvanced(container: ModContainer, eventBus: IEventBus) {
    companion object {
        const val ID: String = "breadmod_advanced"
        private val logger: Logger = LogManager.getLogger("BreadModAdvanced Main")
    }

    init {
        val modPath = container.modInfo.owningFile.file.filePath
        if (modPath.isDirectory()) {
            createDirectories(Directories.modLocalData)
            fun recursiveCopy(device: SystemDevice, into: SystemDevice) {
                val destination = into.get(SystemDeviceFeatures.PATH_APPEND).append(
                    device.get(SystemDeviceFeatures.PATH_ELEMENT_LAST).element
                )
                val status = device.get(SystemDeviceFeatures.PATH_COPY).copy(destination).start(
                    WindowsCopySystemDeviceFeatures.DIRECTORY,
                    WindowsCopySystemDeviceFeatures.ALLOW_UNENCRYPTED_DESTINATION,
                    WindowsCopySystemDeviceFeatures.NO_BLOCK_DESTINATION_ENCRYPT,
                    WindowsCopySystemDeviceFeatures.SPARSENESS
                )
                println("${device.get(SystemDeviceFeatures.PATH_ELEMENT_LAST).element} $status")
                for (child in device.get(SystemDeviceFeatures.PATH_CHILDREN)) {
                    recursiveCopy(child, destination)
                }
            }

            SystemProvider.get(SystemFeatures.GET_PATH_DEVICE_DIRECT)
                .get(modPath.absolutePathString())
                .get(SystemDeviceFeatures.PATH_PARENT).parent!!
                .get(SystemDeviceFeatures.PATH_PARENT).parent!!
                .get(SystemDeviceFeatures.PATH_PARENT).parent!!
                .get(SystemDeviceFeatures.PATH_APPEND).append("resources")
                .get(SystemDeviceFeatures.PATH_APPEND).append(modPath.name)
                .get(SystemDeviceFeatures.PATH_APPEND).append("runtimeExtracted")
                .get(SystemDeviceFeatures.PATH_CHILDREN).forEach { child ->
                    recursiveCopy(child, Directories.modLocalData)
                }
        } else TODO("COPY")
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
