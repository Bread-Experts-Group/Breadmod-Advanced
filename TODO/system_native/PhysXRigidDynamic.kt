package org.bread_experts_group.breadmod_advanced.system_native

abstract class PhysXRigidDynamic : PhysXRigidBody() {
	@VirtualFunction(0) abstract fun setKinematicTarget()
	@VirtualFunction(1) abstract fun getKinematicTarget()
	@VirtualFunction(2) abstract fun isSleeping()
	@VirtualFunction(3) abstract fun setSleepThreshold()
	@VirtualFunction(4) abstract fun getSleepThreshold()
	@VirtualFunction(5) abstract fun setStabilizationThreshold()
	@VirtualFunction(6) abstract fun getStabilizationThreshold()
	@VirtualFunction(7) abstract fun setWakeCounter()
	@VirtualFunction(8) abstract fun getWakeCounter()
	@VirtualFunction(9) abstract fun wakeUp()
	@VirtualFunction(10) abstract fun putToSleep()
	@VirtualFunction(11) abstract fun getRigidDynamicLockFlags()
	@VirtualFunction(12) abstract fun setRigidDynamicLockFlag()
	@VirtualFunction(13) abstract fun setRigidDynamicLockFlags()

	/**
	 * Sets the actor's center-of-mass linear velocity.
	 *
	 * Note that if you continuously set the velocity of an actor yourself,
	 * forces such as gravity or friction will not be able to manifest themselves, because forces directly
	 * influence only the velocity/momentum of an actor.
	 *
	 * **Default:** (0.0, 0.0, 0.0)
	 *
	 * **Sleeping:** This call wakes the actor if it is sleeping, and the autowake parameter is true (default) or the
	 * new velocity is non-zero.
	 *
	 * *It is invalid to use this method if [PxActorFlag.eDISABLE_SIMULATION] is set.*
	 *
	 * *This method should not be used after the direct GPU API has been enabled and initialized. See [PxDirectGPUAPI] for the details.*
	 *
	 * *The linear velocity is applied with respect to the actor's center of mass and not the actor frame origin.*
	 *
	 * @param linVel New center-of-mass linear velocity of the actor. **Range:** velocity vector
	 * @param autowake Whether to wake the object up if it is asleep. If true and the current wake counter value is
	 * smaller than [PhysXSceneDesc.wakeCounterResetValue] it will get increased to the reset value.
	 *
	 * @see getLinearVelocity
	 * @see setAngularVelocity
	 */
	@VirtualFunction(14) abstract fun setLinearVelocity(linVel: PxVec3_t, autowake: Boolean = true)
	@VirtualFunction(15) abstract fun setAngularVelocity()
	@VirtualFunction(16) abstract fun setSolverIterationCounts()
	@VirtualFunction(17) abstract fun getSolverIterationCounts()
	@VirtualFunction(18) abstract fun getContactReportThreshold()
	@VirtualFunction(19) abstract fun setContactReportThreshold()
	@VirtualFunction(20) abstract fun getGPUIndex()
}