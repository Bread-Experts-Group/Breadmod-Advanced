package org.bread_experts_group.breadmodadvanced.physics_grid

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.structure.BoundingBox
import net.minecraft.world.phys.AABB
import org.bread_experts_group.breadmod.util.minus
import org.bread_experts_group.breadmod.util.toVec3

object PhysGridManager {
	private var idCounter: Long = 0

	@JvmField
	val clientGrids: MutableMap<Long, PhysicsGrid> = mutableMapOf()

	@JvmField
	val serverGrids: MutableMap<Long, PhysicsGrid> = mutableMapOf()

	// todo Temp code until i figure out how i wanna fully create the grids and place them in the world
	fun createNewGrid(posA: BlockPos, posB: BlockPos, level: Level): PhysicsGrid {
		val blocks: MutableMap<BlockPos, BlockState> = mutableMapOf()
		val blockEntities: MutableMap<BlockPos, BlockEntity> = mutableMapOf()
		val bounding = AABB.of(BoundingBox.fromCorners(posA, posB))
		BlockPos.betweenClosedStream(posA, posB).forEach { pos ->
			val immutable = pos.immutable()
			val state = level.getBlockState(immutable)
			if (state.isAir) return@forEach
			val posOffset = immutable - posA
			blocks[posOffset] = state
			val blockEntity = level.getBlockEntity(immutable)
			if (state.block is EntityBlock && blockEntity != null) {
				val data = blockEntity.saveWithId(level.registryAccess())
				val newEntity = (state.block as EntityBlock).newBlockEntity(posOffset, state)
				if (newEntity != null) {
					newEntity.loadWithComponents(data, level.registryAccess())
					blockEntities[posOffset] = newEntity
				}
			}
		}

		return PhysicsGrid(idCounter++, posA.toVec3(), bounding, level)
	}
}