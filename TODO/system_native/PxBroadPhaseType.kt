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
 * Broad phase algorithm used in the simulation
 *
 * [eSAP] is a good generic choice with great performance when many objects are sleeping. Performance
 * can degrade significantly though, when all objects are moving, or when large numbers of objects
 * are added to or removed from the broad phase. This algorithm does not need world bounds to be
 * defined in order to work.
 *
 * [eMBP] is an alternative broad phase algorithm that does not suffer from the same performance
 * issues as [eSAP] when all objects are moving or when inserting large numbers of objects. However
 * its generic performance when many objects are sleeping might be inferior to [eSAP], and it requires
 * users to define world bounds in order to work.
 *
 * [eABP] is a revisited implementation of [eMBP], which automatically manages broad-phase regions.
 * It offers the convenience of [eSAP] (no need to define world bounds or regions) and the performance
 * of [eMBP] when a lot of objects are moving. While [eSAP] can remain faster when most objects are
 * sleeping and [eMBP] can remain faster when it uses a large number of properly-defined regions,
 * eABP often gives the best performance on average and the best memory usage.
 *
 * [ePABP] is a parallel implementation of [eABP]. It can often be the fastest (CPU) broadphase, but it
 * can use more memory than [eABP].
 *
 * [eGPU] is a GPU implementation of the incremental sweep and prune approach. Additionally, it uses a ABP-style
 * initial pair generation approach to avoid large spikes when inserting shapes. It not only has the advantage
 * of traditional [eSAP] approch which is good for when many objects are sleeping, but due to being fully parallel,
 * it also is great when lots of shapes are moving or for runtime pair insertion and removal. It can become a
 * performance bottleneck if there are a very large number of shapes roughly projecting to the same values
 * on a given axis. If the scene has a very large number of shapes in an actor, e.g. a humanoid, it is recommended
 * to use an aggregate to represent multi-shape or multi-body actors to minimize stress placed on the broad phase.
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
@Suppress("EnumEntryName")
enum class PxBroadPhaseType {
	/**
	 * 3-axes sweep-and-prune
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eSAP,

	/**
	 * Multi box pruning
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eMBP,

	/**
	 * Automatic box pruning
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eABP,

	/**
	 * Parallel automatic box pruning
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	ePABP,

	/**
	 * GPU broad phase
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eGPU
}