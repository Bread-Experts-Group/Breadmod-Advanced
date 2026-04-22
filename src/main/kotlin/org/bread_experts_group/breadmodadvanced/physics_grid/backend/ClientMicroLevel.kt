package org.bread_experts_group.breadmodadvanced.physics_grid.backend

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.world.level.Level
import org.bread_experts_group.breadmodadvanced.physics_grid.PhysicsGrid

class ClientMicroLevel(
	private val sourceLevel: Level,
	val grid: PhysicsGrid
) : ClientLevel(
	null, null, null, null,
	0, 0, null, null, false,
	0
) {
}