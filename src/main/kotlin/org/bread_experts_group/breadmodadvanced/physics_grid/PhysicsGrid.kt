package org.bread_experts_group.breadmodadvanced.physics_grid

import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import org.bread_experts_group.breadmodadvanced.physics_grid.backend.ClientMicroLevel
import org.bread_experts_group.breadmodadvanced.physics_grid.backend.ServerMicroLevel

class PhysicsGrid(
	val id: Long,
	var pos: Vec3,
	var bounding: AABB,
	val parentLevel: Level
) {
	private val microLevel: Level = if (this.parentLevel.isClientSide)
		ClientMicroLevel(this.parentLevel, this) else ServerMicroLevel(this.parentLevel, this)

	fun getServerLevel(): ServerMicroLevel = this.microLevel as ServerMicroLevel
	fun getClientLevel(): ClientMicroLevel = this.microLevel as ClientMicroLevel
}