package org.bread_experts_group.breadmod_advanced.system_native

abstract class PhysXRigidBody : PhysXRigidActor() {
	@VirtualFunction(0) abstract fun setCMassLocalPose()
	@VirtualFunction(1) abstract fun getCMassLocalPose()

	/**
	 * Sets the mass of a dynamic actor.
	 *
	 * The mass must be non-negative.
	 *
	 * [setMass] does not update the inertial properties of the body, to change the inertia tensor
	 * use [setMassSpaceInertiaTensor] or the PhysX extensions method [PxRigidBodyExt.updateMassAndInertia].
	 *
	 * *A value of 0 is interpreted as infinite mass.*
	 *
	 * *Values of 0 are not permitted for instances of [PhysXArticulationLink] but are permitted for instances of [PhysXRigidDynamic].*
	 *
	 * **Default:** 1.0
	 *
	 * **Sleeping:** Does **NOT** wake the actor up automatically.
	 *
	 * @param mass New mass value for the actor. **Range:** [0, [PX_MAX_F32])
	 *
	 * @see getMass
	 * @see setMassSpaceInertiaTensor
	 */
	@VirtualFunction(2) abstract fun setMass(mass: PxReal_t)
	@VirtualFunction(3) abstract fun getMass()
	@VirtualFunction(4) abstract fun getInvMass()
	@VirtualFunction(5) abstract fun setMassSpaceInertiaTensor()
	@VirtualFunction(6) abstract fun getMassSpaceInertiaTensor()
	@VirtualFunction(7) abstract fun getMassSpaceInvInertiaTensor()
	@VirtualFunction(8) abstract fun setLinearDamping()
	@VirtualFunction(9) abstract fun getLinearDamping()

	/**
	 * Sets the angular damping coefficient.
	 *
	 * Zero represents no damping.
	 *
	 * The angular damping coefficient must be nonnegative.
	 *
	 * **Default:** 0.05
	 *
	 * @param angDamp Angular damping coefficient. **Range:** [0, [PX_MAX_F32])
	 *
	 * @see getAngularDamping
	 * @see setLinearDamping
	 */
	@VirtualFunction(10) abstract fun setAngularDamping(angDamp: PxReal_t)
	@VirtualFunction(11) abstract fun getAngularDamping()
	@VirtualFunction(12) abstract fun getLinearVelocity()
	@VirtualFunction(13) abstract fun getAngularVelocity()
	@VirtualFunction(14) abstract fun setMaxLinearVelocity()
	@VirtualFunction(15) abstract fun getMaxLinearVelocity()
	@VirtualFunction(16) abstract fun setMaxAngularVelocity()
	@VirtualFunction(17) abstract fun getMaxAngularVelocity()
	@VirtualFunction(18) abstract fun getLinearAcceleration()
	@VirtualFunction(19) abstract fun getAngularAcceleration()
	@VirtualFunction(20) abstract fun addForce()
	@VirtualFunction(21) abstract fun addTorque()
	@VirtualFunction(22) abstract fun clearForce()
	@VirtualFunction(23) abstract fun clearTorque()
	@VirtualFunction(24) abstract fun setForceAndTorque()
	@VirtualFunction(25) abstract fun setRigidBodyFlag()
	@VirtualFunction(26) abstract fun setRigidBodyFlags()
	@VirtualFunction(27) abstract fun getRigidBodyFlags()
	@VirtualFunction(28) abstract fun setMinCCDAdvanceCoefficient()
	@VirtualFunction(29) abstract fun getMinCCDAdvanceCoefficient()
	@VirtualFunction(30) abstract fun setMaxDepenetrationVelocity()
	@VirtualFunction(31) abstract fun getMaxDepenetrationVelocity()
	@VirtualFunction(32) abstract fun setMaxContactImpulse()
	@VirtualFunction(33) abstract fun getMaxContactImpulse()
	@VirtualFunction(34) abstract fun setContactSlopCoefficient()
	@VirtualFunction(35) abstract fun getContactSlopCoefficient()
	@VirtualFunction(36) abstract fun getInternalIslandNodeIndex()
}