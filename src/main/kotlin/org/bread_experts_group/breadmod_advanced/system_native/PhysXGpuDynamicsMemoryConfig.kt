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

/**
 * Sizes of pre-allocated buffers use for GPU dynamics
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
data class PhysXGpuDynamicsMemoryConfig(
	/**
	 * Initial capacity of temp solver buffer allocated in pinned host memory. This buffer will grow if more memory
	 * is needed than specified here.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(0) val tempBufferCapacity: PxU64_t = 16u * 1024u * 1024uL,
	/**
	 * Size of contact stream buffer allocated in pinned host memory. This is double-buffered so total
	 * `allocation size = 2 * contactStreamCapacity * sizeof(PxContact)`.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(1) val maxRigidContactCount: PxU32_t = 1024u * 512u,
	/**
	 * Size of the contact patch stream buffer allocated in pinned host memory. This is double-buffered so total
	 * `allocation size = 2 * patchStreamCapacity * sizeof(PxContactPatch)`.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(2) val maxRigidPatchCount: PxU32_t = 1024u * 80u,
	/**
	 * Initial capacity of the GPU and pinned host memory heaps. Additional memory will be allocated if more
	 * memory is required.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(3) val heapCapacity: PxU32_t = 64u * 1024u * 1024u,
	/**
	 * Capacity of found and lost buffers allocated in GPU global memory. This is used for the found/lost
	 * pair reports in the BP.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(4) val foundLostPairsCapacity: PxU32_t = 256u * 1024u,
	/**
	 * Capacity of found and lost buffers in aggregate system allocated in GPU global memory. This is
	 * used for the found/lost pair reports in AABB manager.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(5) val foundLostAggregatePairsCapacity: PxU32_t = 1024u,
	/**
	 * Capacity of aggregate pair buffer allocated in GPU global memory.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(6) val totalAggregatePairsCapacity: PxU32_t = 1024u,
	/**
	 * Capacity of deformable surface contact buffer allocated in GPU global memory.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(7) val maxDeformableSurfaceContacts: PxU32_t = 1u * 1024u * 1024u,
	/**
	 * Capacity of deformable volume contact buffer allocated in GPU global memory.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(9) val maxDeformableVolumeContacts: PxU32_t = 1u * 1024u * 1024u,
	/**
	 * Capacity of particle contact buffer allocated in GPU global memory.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(11) val maxParticleContacts: PxU32_t = 1u * 1024u * 1024u,
	/**
	 * Capacity of the collision stack buffer, used as scratch space during narrowphase collision detection.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(12) val collisionStackSize: PxU32_t = 64u * 1024u * 1024u
) {
	@Deprecated("Deprecated in PhysX", ReplaceWith("maxDeformableSurfaceContacts"))
	@DefinedProperty(8) val maxFemClothContacts: PxU32_t = 0u

	@Deprecated("Deprecated in PhysX", ReplaceWith("maxDeformableVolumeContacts"))
	@DefinedProperty(10) val maxSoftBodyContacts: PxU32_t = 0u
}