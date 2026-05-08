package org.bread_experts_group.breadmod_advanced.system_native

import org.bread_experts_group.api.system.SystemFeatures
import org.bread_experts_group.api.system.SystemProvider
import org.bread_experts_group.api.system.device.SystemDevice
import org.bread_experts_group.api.system.device.SystemDeviceFeatures
import org.bread_experts_group.api.system.io.IODevice
import org.bread_experts_group.api.system.io.IODeviceFeatures
import org.bread_experts_group.api.system.io.open.StandardIOOpenFeatures
import org.bread_experts_group.breadmod.BreadMod
import org.bread_experts_group.breadmod_advanced.BreadModAdvanced

object Directories {
	fun createDirectories(device: SystemDevice) {
		val workList = ArrayDeque<SystemDevice>(1)
		workList.add(device)
		while (workList.isNotEmpty()) {
			val creating = workList.removeFirst()
			val status = creating.get(SystemDeviceFeatures.IO_DEVICE).open(
				StandardIOOpenFeatures.CREATE, StandardIOOpenFeatures.DIRECTORY
			)
			val device = status.firstNotNullOfOrNull { it as? IODevice }
			if (device != null) device.getOrNull(IODeviceFeatures.RELEASE)?.close()
			else {
				workList.add(creating.get(SystemDeviceFeatures.PATH_PARENT).parent ?: return)
				workList.add(creating)
			}
		}
	}

	val minecraftRoot: SystemDevice = SystemProvider.get(SystemFeatures.GET_CURRENT_WORKING_PATH_DEVICE).device

	val modLocalData: SystemDevice = minecraftRoot
		.get(SystemDeviceFeatures.PATH_APPEND).append(BreadMod.ID)
		.get(SystemDeviceFeatures.PATH_APPEND).append(BreadModAdvanced.ID)
	val nativeLibraries: SystemDevice = modLocalData
		.get(SystemDeviceFeatures.PATH_APPEND).append("natives")
	val physX: SystemDevice = nativeLibraries
		.get(SystemDeviceFeatures.PATH_APPEND).append("physx")
}