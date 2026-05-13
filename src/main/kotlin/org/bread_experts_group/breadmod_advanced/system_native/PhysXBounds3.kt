package org.bread_experts_group.breadmod_advanced.system_native

data class PhysXBounds3(
	@DefinedProperty(0) val minimum: PxVec3_t,
	@DefinedProperty(1) val maximum: PxVec3_t,
)