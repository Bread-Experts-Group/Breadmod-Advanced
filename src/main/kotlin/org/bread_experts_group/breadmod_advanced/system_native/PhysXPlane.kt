package org.bread_experts_group.breadmod_advanced.system_native

/**
 * Representation of a plane.
 *
 *  Plane equation used: n.dot(v) + d = 0
 */
sealed class PhysXPlane {
	/**
	 * The normal to the plane
	 */
	abstract val n: PxVec3_t

	/**
	 * The distance from the origin
	 */
	abstract val d: Float

	abstract class ReadOnly : PhysXPlane() {
		@DefinedProperty(0) abstract override val n: PxVec3_t
		@DefinedProperty(1) abstract override val d: Float
	}

	open class ReadWrite(
		@DefinedProperty(0) override var n: PxVec3_t = PxVec3_t(0f, 0f, 0f),
		@DefinedProperty(1) override var d: Float = 0f
	) : PhysXPlane()
}