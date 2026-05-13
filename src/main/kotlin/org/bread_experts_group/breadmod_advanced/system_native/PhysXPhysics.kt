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

import java.lang.foreign.MemorySegment

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
	@VirtualFunction(2) abstract fun fTODO2() // getFoundation
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
	 */
	@VirtualFunction(24) abstract fun createScene(sceneDesc: MemorySegment): MemorySegment // createScene
	@VirtualFunction(25) abstract fun fTODO25() // getNbScenes
	@VirtualFunction(26) abstract fun fTODO26() // getScenes
}