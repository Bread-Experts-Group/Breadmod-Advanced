fun openContainerMenu(level: Level, pos: BlockPos, player: Player): InteractionResult {
	val entity = level.getBlockEntity(pos) as? BreadModBlockEntity
	if (entity != null && this.menuType != null) {
		player.openMenu(entity) { buf ->
			buf.writeBlockPos(pos)
			if (level is ClientMicroLevel) buf.writeLong(level.grid.id)
			else if (level is ServerMicroLevel) buf.writeLong(level.grid.id)
		}
		return InteractionResult.sidedSuccess(level.isClientSide)
	}
	return InteractionResult.PASS
}