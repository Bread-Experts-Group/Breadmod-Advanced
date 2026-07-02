package org.bread_experts_group.breadmod_advanced.system_native

import java.lang.foreign.MemorySegment

abstract class PhysXActor : PhysXBase() {
	@VirtualFunction(0) abstract fun getType()
	@VirtualFunction(1) abstract fun getScene()
	@VirtualFunction(2) abstract fun setName()
	@VirtualFunction(3) abstract fun getName()
	@VirtualFunction(4) abstract fun getWorldBounds()
	@VirtualFunction(5) abstract fun setActorFlag()
	@VirtualFunction(6) abstract fun setActorFlags()
	@VirtualFunction(7) abstract fun getActorFlags()
	@VirtualFunction(8) abstract fun setDominanceGroup()
	@VirtualFunction(9) abstract fun getDominanceGroup()
	@VirtualFunction(10) abstract fun setOwnerClient()
	@VirtualFunction(11) abstract fun getOwnerClient()
	@VirtualFunction(12) abstract fun getAggregate()
	@VirtualFunction(13) abstract fun setEnvironmentID()
	@VirtualFunction(14) abstract fun getEnvironmentID()

	/**
	 * user can assign this to whatever, usually to create a 1:1 relationship with a user object.
	 */
	@DefinedProperty(0) abstract val userData: MemorySegment
}