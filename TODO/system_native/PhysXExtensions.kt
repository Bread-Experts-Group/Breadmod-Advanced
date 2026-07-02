@file:Suppress("FunctionName")

package org.bread_experts_group.breadmod_advanced.system_native

import org.bread_experts_group.breadmod_advanced.system_native.PhysXQuatT.ReadWrite.Companion.PxIdentityF
import org.bread_experts_group.breadmod_advanced.system_native.PhysXVec3T.ReadWrite.Companion.PxZeroF
import java.lang.foreign.MemorySegment
import kotlin.math.abs

fun PxCreateStatic(
	sdk: PhysXPhysics,
	transform: PxTransform_t,
	shape: PhysXShape
): PhysXRigidStatic {
	// TODO: PX_CHECK_AND_RETURN_NULL(transform.isValid(), "PxCreateStatic: transform is not valid.");
	val s = sdk.createRigidStatic(transform)
	s.attachShape(shape)
	return s
}

fun PxCreateStatic(
	sdk: PhysXPhysics,
	transform: PxTransform_t,
	geometry: PhysXGeometry,
	material: MemorySegment,
	shapeOffset: PxTransform_t = PhysXTransformT.ReadWrite(PxIdentityF, PxZeroF)
): PhysXRigidStatic {
	/*
	TODO: PX_CHECK_AND_RETURN_NULL(transform.isValid(), "PxCreateStatic: transform is not valid.");
	TODO: PX_CHECK_AND_RETURN_NULL(shapeOffset.isValid(), "PxCreateStatic: shapeOffset is not valid.");
	 */

	val shape = sdk.createShape(geometry, material, true)
	// TODO: shape->setLocalPose(shapeOffset);
	val s = PxCreateStatic(sdk, transform, shape)
	shape.release()
	return s
}

fun PxCreateDynamic(
	sdk: PhysXPhysics,
	transform: PxTransform_t,
	shape: PhysXShape,
	density: Float
): PhysXRigidDynamic {
	//TODO:  PX_CHECK_AND_RETURN_NULL(transform.isValid(), "PxCreateDynamic: transform is not valid.");
	//
	val actor = sdk.createRigidDynamic(transform)
	//TODO: if(actor)
	//TODO: {
	//TODO: 	if(!
	actor.attachShape(shape)
	//TODO: )
	//TODO: 	{
	//TODO: 		actor->release();
	//TODO: 		return NULL;
	//TODO: 	}
	//TODO: 	if(!PxRigidBodyExt::updateMassAndInertia(*actor, density))
	//TODO: 	{
	//TODO: 		actor->release();
	//TODO: 		return NULL;
	//TODO: 	}
	//TODO: }
	return actor
}

fun PxCreateDynamic(
	sdk: PhysXPhysics,
	transform: PxTransform_t,
	geometry: PhysXGeometry,
	material: MemorySegment,
	density: PxReal_t,
	shapeOffset: PxTransform_t = PhysXTransformT.ReadWrite(PxIdentityF, PxZeroF)
): PhysXRigidDynamic {
	// 	TODO: PX_CHECK_AND_RETURN_NULL(transform.isValid(), "PxCreateDynamic: transform is not valid.");
	//	TODO: PX_CHECK_AND_RETURN_NULL(shapeOffset.isValid(), "PxCreateDynamic: shapeOffset is not valid.");
	//
	//	TODO: if(!isDynamicGeometry(geometry.getType()) || density <= 0.0f)
	//	TODO:     return NULL;
	//
	val shape = sdk.createShape(geometry, material, true)
	//	TODO: if(!shape)
	//	TODO: 	return NULL;
	//
	//	TODO: shape->setLocalPose(shapeOffset);
	//
	val body = PxCreateDynamic(sdk, transform, shape, density)
	shape.release()
	return body
}

/**
 * finds the shortest rotation between two vectors.
 *
 * @param from the vector to start from
 * @param target the vector to rotate to
 * @return a rotation about an axis normal to the two vectors which takes one to the other via the shortest path
 */
fun PxShortestRotation(
	v0: PxVec3_t,
	v1: PxVec3_t
): PxQuat_t {
	val d = v0.dot(v1)
	val cross = v0.cross(v1)
	val q = if (d > -1) PhysXQuatT.ReadWrite(cross.x, cross.y, cross.z, 1 + d)
	else if (abs(v0.x) < 0.1f) PhysXQuatT.ReadWrite(0f, v0.z, -v0.y, 0f)
	else PhysXQuatT.ReadWrite(v0.y, -v0.x, 0f, 0f)
	return q.getNormalized()
}

/**
 * creates a transform from a plane equation, suitable for an actor transform for a [PhysXPlaneGeometry]
 *
 * @param plane the desired plane equation
 * @return a [PhysXTransform] which will transform the plane [PhysXPlane] (1,0,0,0) to the specified plane
 */
fun PxTransformFromPlaneEquation(
	plane: PhysXPlane
): PxTransform_t {
	val p = plane.normalize()
	val halfsqrt2 = 0.707106781f
	var r = 0
	if (p.n.x == 0.0f) r++
	if (p.n.y == 0.0f) r++
	if (p.n.z == 0.0f) r++
	val q = if (r == 2) {
		if (p.n.x > 0) PxIdentityF
		else if (p.n.x < 0) PhysXQuatT.ReadWrite(0f, 0f, 1f, 0f)
		else PhysXQuatT.ReadWrite(0f, -p.n.z, p.n.y, 1f) * halfsqrt2
	} else PxShortestRotation(PhysXVec3T.ReadWrite(1f, 0f, 0f), p.n)
	return PhysXTransformT.ReadWrite(-p.n * p.d, q)
}

fun PxCreatePlane(
	sdk: PhysXPhysics,
	plane: PhysXPlane,
	material: MemorySegment
): PhysXRigidStatic {
	/*
	TODO: PX_CHECK_AND_RETURN_NULL(plane.n.isFinite(), "PxCreatePlane: plane normal is not valid.");

	TODO: if (!plane.n.isNormalized())
	TODO: 	return NULL;
	 */
	return PxCreateStatic(sdk, PxTransformFromPlaneEquation(plane), PhysXPlaneGeometry(), material)
}