package org.bread_experts_group.breadmod_advanced.physics_grid.backend

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import org.bread_experts_group.breadmod_advanced.physics_grid.PhysicsGrid

class ServerMicroLevel(
	private val sourceLevel: Level,
	val grid: PhysicsGrid
) : ServerLevel(
	null, null, null, null,
	null, null, null, false,
	0, null, true, null
) {
}