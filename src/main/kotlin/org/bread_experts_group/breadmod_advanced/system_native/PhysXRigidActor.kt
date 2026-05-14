package org.bread_experts_group.breadmod_advanced.system_native

abstract class PhysXRigidActor : PhysXActor() {
	@VirtualFunction(0) abstract fun getInternalActorIndex()
	@VirtualFunction(1) abstract fun getGlobalPose()
	@VirtualFunction(2) abstract fun setGlobalPose()

	/**
	 * Attach a shape to an actor
	 *
	 * This call will increment the reference count of the shape.
	 *
	 * *Mass properties of dynamic rigid actors will not automatically be recomputed
	 * to reflect the new mass distribution implied by the shape. Follow this call with a call to
	 * the PhysX extensions method [PxRigidBodyExt.updateMassAndInertia] to do that.*
	 *
	 * Attaching a triangle mesh, heightfield or plane geometry shape configured as [eSIMULATION_SHAPE] is not supported for
	 * non-kinematic [PhysXRigidDynamic] instances.
	 *
	 * **Sleeping:** Does **NOT** wake the actor up automatically.
	 *
	 * @param shape	the shape to attach.
	 *
	 * @return True if success.
	 */
	@VirtualFunction(3) abstract fun attachShape(shape: PhysXShape): Boolean
	@VirtualFunction(4) abstract fun detachShape()
	@VirtualFunction(5) abstract fun getNbShapes()
	@VirtualFunction(6) abstract fun getShapes()
	@VirtualFunction(7) abstract fun getNbConstraints()
	@VirtualFunction(8) abstract fun getConstraints()
}