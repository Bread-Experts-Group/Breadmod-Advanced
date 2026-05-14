// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
//  * Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
//  * Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in the
//    documentation and/or other materials provided with the distribution.
//  * Neither the name of NVIDIA CORPORATION nor the names of its
//    contributors may be used to endorse or promote products derived
//    from this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS ''AS IS'' AND ANY
// EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
// PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
// CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
// EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
// PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
// OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//
// Copyright (c) 2008-2025 NVIDIA Corporation. All rights reserved.
// Copyright (c) 2004-2008 AGEIA Technologies, Inc. All rights reserved.
// Copyright (c) 2001-2004 NovodeX AG. All rights reserved.

package org.bread_experts_group.breadmod_advanced.system_native

import org.bread_experts_group.generic.FlagSet
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

/**
 * Abstract singleton factory class used for instancing objects in the Physics SDK.
 *
 * In addition you can use PxPhysics to set global parameters which will effect all scenes and create
 * objects that can be shared across multiple scenes.
 *
 * You can get an instance of this class by calling [PhysXLibrary.pxCreatePhysics].
 *
 * @since In accordance with PhysX 5.6.1
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 *
 * @see PhysXLibrary.pxCreatePhysics
 * @see PhysXScene
 */
abstract class PhysXPhysics {
	@VirtualFunction(0) abstract fun destructorPhysXPhysics()

	/**
	 * Destroys the instance it is called on.
	 *
	 * Use this release method to destroy an instance of this class. Be sure
	 * to not keep a reference to this object after calling release.
	 * Avoid release calls while a scene is simulating (in between simulate() and fetchResults() calls).
	 *
	 * Note that this must be called once for each prior call to PxCreatePhysics, as
	 * there is a reference counter. Also note that you mustn't destroy the PxFoundation instance (holding the allocator, error callback etc.)
	 * until after the reference count reaches 0 and the SDK is actually removed.
	 *
	 * Releasing an SDK will also release any objects created through it (scenes, triangle meshes, convex meshes, heightfields, shapes etc.),
	 * provided the user hasn't already done so.
	 *
	 * *Releasing the PxPhysics instance is a prerequisite to releasing the PxFoundation instance.*
	 *
	 * @since In accordance with PhysX 5.6.1
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 *
	 * @see PhysXFoundation
	 * @see PhysXLibrary.pxCreatePhysics
	 */
	@VirtualFunction(1) abstract fun release()
	@VirtualFunction(2) abstract fun getFoundation(): PhysXFoundation
	@VirtualFunction(3) abstract fun fTODO3() // getPhysicsInsertionCallback
	@VirtualFunction(4) abstract fun fTODO4() // getOmniPvd

	/**
	 * Returns the simulation tolerance parameters.
	 * @return The current simulation tolerance parameters.
	 *
	 * @since In accordance with PhysX 5.6.1
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 */
	@VirtualFunction(5) abstract fun getTolerancesScale(): PhysXTolerancesScale.ReadOnly
	@VirtualFunction(6) abstract fun fTODO6() // createAggregate
	@VirtualFunction(7) abstract fun fTODO7() // getNbAggregates
	@VirtualFunction(8) abstract fun fTODO8() // createTriangleMesh
	@VirtualFunction(9) abstract fun fTODO9() // getNbTriangleMeshes
	@VirtualFunction(10) abstract fun fTODO10() // getTriangleMeshes
	@VirtualFunction(11) abstract fun fTODO11() // createTetrahedronMesh
	@VirtualFunction(12) abstract fun fTODO12() // getNbTetrahedronMeshes
	@VirtualFunction(13) abstract fun fTODO13() // getTetrahedronMeshes
	@VirtualFunction(14) abstract fun fTODO14() // createHeightField
	@VirtualFunction(15) abstract fun fTODO15() // getNbHeightFields
	@VirtualFunction(16) abstract fun fTODO16() // getHeightFields
	@VirtualFunction(17) abstract fun fTODO17() // createConvexMesh
	@VirtualFunction(18) abstract fun fTODO18() // getNbConvexMeshes
	@VirtualFunction(19) abstract fun fTODO19() // getConvexMeshes
	@VirtualFunction(20) abstract fun fTODO20() // createDeformableVolumeMesh
	@VirtualFunction(21) abstract fun fTODO21() // createBVH
	@VirtualFunction(22) abstract fun fTODO22() // getNbBVHs
	@VirtualFunction(23) abstract fun fTODO23() // getBVHs

	/**
	 * Creates a scene.
	 *
	 * *Every scene uses a Thread Local Storage slot. This imposes a platform specific limit on the
	 * number of scenes that can be created.*
	 *
	 * @param sceneDesc	Scene descriptor. See [PhysXSceneDesc]
	 * @return The new scene object.
	 *
	 * @see PhysXScene
	 * @see PhysXScene.release
	 * @see PhysXSceneDesc
	 *
	 * @since In accordance with PhysX 5.6.1
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 */
	@VirtualFunction(24) abstract fun createScene(sceneDesc: PhysXSceneDesc): PhysXScene
	@VirtualFunction(25) abstract fun fTODO25() // getNbScenes
	@VirtualFunction(26) abstract fun fTODO26() // getScenes

	/**
	 * Creates a static rigid actor with the specified pose and all other fields initialized
	 * to their default values.
	 *
	 * @param pose	The initial pose of the actor. Must be a valid transform.
	 *
	 * @see PhysXRigidStatic
	 *
	 * @since In accordance with PhysX 5.6.1
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 */
	@VirtualFunction(27) abstract fun createRigidStatic(pose: PxTransform_t): PhysXRigidStatic

	/**
	 * Creates a dynamic rigid actor with the specified pose and all other fields initialized
	 * to their default values.
	 *
	 * @param pose	The initial pose of the actor. Must be a valid transform.
	 *
	 * @see PhysXRigidDynamic
	 *
	 * @since In accordance with PhysX 5.6.1
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 */
	@VirtualFunction(28) abstract fun createRigidDynamic(pose: PxTransform_t): PhysXRigidDynamic
	@VirtualFunction(29) abstract fun fTODO29() // createPruningStructure

	/**
	 * Creates a shape which may be attached to multiple actors
	 *
	 * The shape will be created with a reference count of 1.
	 *
	 * @param geometry		The geometry for the shape
	 * @param material		The material for the shape
	 * @param isExclusive	Whether this shape is exclusive to a single actor or maybe be shared
	 * @param shapeFlags	The PxShapeFlags to be set
	 * @return The shape
	 *
	 * *Shared shapes are not mutable when they are attached to an actor*
	 *
	 * @see PhysXShape
	 *
	 * @since In accordance with PhysX 5.6.1
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 */
	fun createShape(
		geometry: PhysXGeometry,
		material: MemorySegment,
		isExclusive: Boolean = false,
		shapeFlags: PxShapeFlags = PxShapeFlags.ReadWrite(
			FlagSet.of(
				PxShapeFlag.eVISUALIZATION,
				PxShapeFlag.eSCENE_QUERY_SHAPE,
				PxShapeFlag.eSIMULATION_SHAPE
			).maskB.toUByte()
		)
	): PhysXShape = Arena.ofConfined().use { tempArena ->
		val materialPtr = tempArena.allocate(ValueLayout.ADDRESS, 1)
		materialPtr.set(ValueLayout.ADDRESS, 0, material)
		this.createShape(geometry, materialPtr, 1u, isExclusive, shapeFlags)
	}

	/**
	 * Creates a shape which may be attached to multiple actors
	 *
	 * The shape will be created with a reference count of 1.
	 *
	 * @param geometry		The geometry for the shape
	 * @param materials		The materials for the shape
	 * @param materialCount	The number of materials
	 * @param isExclusive	Whether this shape is exclusive to a single actor or may be shared
	 * @param shapeFlags		The PxShapeFlags to be set
	 * @return The shape
	 *
	 * *Shared shapes are not mutable when they are attached to an actor*
	 * *Shapes created from *SDF* triangle-mesh geometries do not support more than one material.*
	 *
	 * @see PhysXShape
	 *
	 * @since In accordance with PhysX 5.6.1
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 */
	@VirtualFunction(32) abstract fun createShape(
		geometry: PhysXGeometry,
		materials: MemorySegment,
		materialCount: PxU16_t,
		isExclusive: Boolean = false,
		shapeFlags: PxShapeFlags = PxShapeFlags.ReadWrite(
			FlagSet.of(
				PxShapeFlag.eVISUALIZATION,
				PxShapeFlag.eSCENE_QUERY_SHAPE,
				PxShapeFlag.eSIMULATION_SHAPE
			).maskB.toUByte()
		)
	): PhysXShape

	@VirtualFunction(31) abstract fun fTODO31() // createShape (PxTriangleMeshGeometry)
	@VirtualFunction(30) abstract fun fTODO32() // createShape (PxTetrahedronMeshGeometry)
	@VirtualFunction(33) abstract fun fTODO33() // getNbShapes
	@VirtualFunction(34) abstract fun fTODO34() // getShapes
	@VirtualFunction(35) abstract fun fTODO35() // createConstraint
	@VirtualFunction(36) abstract fun fTODO36() // getNbConstraints
	@VirtualFunction(37) abstract fun fTODO37() // createArticulationReducedCoordinate
	@VirtualFunction(38) abstract fun fTODO38() // getNbArticulations
	@VirtualFunction(39) abstract fun fTODO39() // createDeformableAttachment
	@VirtualFunction(40) abstract fun fTODO40() // createDeformableElementFilter
	@VirtualFunction(41) abstract fun fTODO41() // createDeformableSurface
	@VirtualFunction(42) abstract fun fTODO42() // createDeformableVolume
	@VirtualFunction(43) abstract fun fTODO43() // createPBDParticleSystem
	@VirtualFunction(44) abstract fun fTODO44() // createParticleBuffer
	@VirtualFunction(45) abstract fun fTODO45() // createParticleAndDiffuseBuffer
	@VirtualFunction(46) abstract fun fTODO46() // createParticleClothBuffer
	@VirtualFunction(47) abstract fun fTODO47() // createParticleRigidBuffer

	/**
	 * Creates a new rigid body material with certain default properties.
	 *
	 * @return The new rigid body material.
	 *
	 * @param staticFriction		The coefficient of static friction
	 * @param dynamicFriction	The coefficient of dynamic friction
	 * @param restitution		The coefficient of restitution (if in range [0,1]) or the spring stiffness for compliant contact (if in range (-PX_MAX_REAL, 0))
	 *
	 * @see PhysXMaterial
	 *
	 * @since In accordance with PhysX 5.6.1
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 */
	@VirtualFunction(48) abstract fun createMaterial(
		staticFriction: PxReal_t,
		dynamicFriction: PxReal_t,
		restitution: PxReal_t,
	): MemorySegment
	@VirtualFunction(49) abstract fun fTODO49() // getNbMaterials
	@VirtualFunction(50) abstract fun fTODO50() // getMaterials
}