fun <T : Block> registerBlock(
	id: String,
	block: () -> T
): DeferredBlock<T> {
	val holder = this.registry.register(id, Supplier {
		val actual = block()
		if (actual is BreadModBlock) {
			if (actual.shouldCreateEntity()) {
				@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
				actual.blockEntityType = this.blockEntityRegistry.register(id, Supplier {
					BlockEntityType.Builder.of(
						{ p, s -> actual.newBlockEntity(p, s) },
						actual
					).build(null)
				})
			}
			val menu = actual.ofMenu()
			if (menu != null) {
				actual.menuType = this.menuRegistry.register(id) { _: ResourceLocation ->
					IMenuTypeExtension.create { id, inventory, byteBuf ->
						val pos = byteBuf.readBlockPos()
						val level: Level = if (byteBuf.capacity() > 8) {
							val gridID = byteBuf.readLong()
							val isClient = inventory.player.level().isClientSide
							val grid = if (isClient) PhysicsGrid.clientGrids[gridID]
							else PhysicsGrid.serverGrids[gridID]
							grid?.microLevel ?: inventory.player.level()
						} else inventory.player.level()
						@Suppress("UNCHECKED_CAST")
						menu(
							actual.menuType!!.get(), id, inventory,
							level
								.getBlockEntity(
									pos,
									actual.blockEntityType!!.get() as BlockEntityType<BreadModBlockEntity>
								)
								.get()
						)
					}
				}
			}
		}
		actual
	})
	return DeferredBlock.createBlock(holder.id)
}