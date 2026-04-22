NeoForge.EVENT_BUS.addListener { _: ClientTickEvent.Post ->
	PhysicsGrid.clientGrids.forEach { (_, grid) ->
		if (!grid.microLevel.tickRateManager().runsNormally()) return@forEach
		(grid.microLevel as ClientMicroLevel).tick { true }
		grid.movementTick()
	}
}

NeoForge.EVENT_BUS.addListener { event: ServerTickEvent.Post ->
	PhysicsGrid.serverGrids.forEach { (_, grid) ->
		if (!grid.microLevel.tickRateManager().runsNormally()) return@forEach
		grid.tick(event.server)
		grid.movementTick()
	}
}

GridPacket.register(registrar)
NewPhysicsGridPacket.register(registrar)
EncapsulateSoundEntityPhysicsGridPacket.register(registrar)
EncapsulateSoundPhysicsGridPacket.register(registrar)
EncapsulateBlockEventPhysicsGridPacket.register(registrar)
EncapsulateBlockDestructionPhysicsGridPacket.register(registrar)
EncapsulateBlockUpdatePhysicsGridPacket.register(registrar)
EncapsulateLevelEventPhysicsGridPacket.register(registrar)
EncapsulateUseItemOnPhysicsGridPacket.register(registrar)
EncapsulatePlayerActionPhysicsGridPacket.register(registrar)