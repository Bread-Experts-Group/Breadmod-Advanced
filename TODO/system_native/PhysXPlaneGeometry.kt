package org.bread_experts_group.breadmod_advanced.system_native

/**
 * Class describing a plane geometry.
 *
 * The plane geometry specifies the half-space volume x<=0. As with other geometry types,
 * when used in a [PhysXShape] the collision volume is obtained by transforming the halfspace
 * by the shape local pose and the actor global pose.
 *
 * To generate a [PhysXPlane] from a [PxTransform_t], transform [PhysXPlane] (1,0,0,0).
 *
 * To generate a [PxTransform_t] from a [PhysXPlane], use [PxTransformFromPlaneEquation].
 *
 * @see PhysXShape.setGeometry
 * @see PhysXShape.getPlaneGeometry
 * @see PxTransformFromPlaneEquation
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
class PhysXPlaneGeometry : PhysXGeometry() {
	@DefinedProperty(0) override val mType: PxGeometryType = PxGeometryType.ePLANE
	@DefinedProperty(1) override val mTypePadding: Float = 0f
}