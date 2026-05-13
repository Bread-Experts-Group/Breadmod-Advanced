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
 * Descriptor class for scene query system. See [PxSceneQuerySystem].
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
open class PhysXSceneQueryDesc(
	/**
	 * Defines the structure used to store static objects ([PhysXRigidStatic] actors).
	 *
	 * There are usually a lot more static actors than dynamic actors in a scene, so they are stored
	 * in a separate structure. The idea is that when dynamic actors move each frame, the static structure
	 * remains untouched and does not need updating.
	 *
	 * *Only [PxPruningStructureType.eSTATIC_AABB_TREE] and [PxPruningStructureType.eDYNAMIC_AABB_TREE] are allowed here.*
	 *
	 * @see PxPruningStructureType
	 * @see PhysXSceneSQSystem.getStaticStructure
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(0) var staticStructure: PxPruningStructureType = PxPruningStructureType.eDYNAMIC_AABB_TREE,
	/**
	 * Defines the structure used to store dynamic objects (non-[PhysXRigidStatic] actors).
	 *
	 * @see PxPruningStructureType
	 * @see PhysXSceneSQSystem.getDynamicStructure
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(1) var dynamicStructure: PxPruningStructureType = PxPruningStructureType.eDYNAMIC_AABB_TREE,
	/**
	 * Hint for how much work should be done per simulation frame to rebuild the pruning structures.
	 *
	 * This parameter gives a hint on the distribution of the workload for rebuilding the dynamic AABB tree
	 * pruning structure [PxPruningStructureType.eDYNAMIC_AABB_TREE]. It specifies the desired number of simulation frames
	 * the rebuild process should take. Higher values will decrease the workload per frame but the pruning
	 * structure will get more and more outdated the longer the rebuild takes (which can make
	 * scene queries less efficient).
	 *
	 * *Only used for [PxPruningStructureType.eDYNAMIC_AABB_TREE] pruning structures.*
	 *
	 * *Both staticStructure & dynamicStructure can use a [PxPruningStructureType.eDYNAMIC_AABB_TREE] in which case
	 * this parameter is used for both.*
	 *
	 * *This parameter gives only a hint. The rebuild process might still take more or less time depending on the
	 * number of objects involved.*
	 *
	 * **Range:** [4, PX_MAX_U32)
	 *
	 * @see PxSceneQuerySystemBase.setDynamicTreeRebuildRateHint
	 * @see PxSceneQuerySystemBase.getDynamicTreeRebuildRateHint
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(2) var dynamicTreeRebuildRateHint: PxU32_t = 100u,
	/**
	 * Secondary pruner for dynamic tree.
	 *
	 * This is used for [PxPruningStructureType.eDYNAMIC_AABB_TREE] structures, to control how objects added to the system
	 * at runtime are managed.
	 *
	 * *Both staticStructure & dynamicStructure can use a [PxPruningStructureType.eDYNAMIC_AABB_TREE], in which case
	 * this parameter is used for both.*
	 *
	 * @see PxDynamicTreeSecondaryPruner
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(3) var dynamicTreeSecondaryPruner: PxDynamicTreeSecondaryPruner = PxDynamicTreeSecondaryPruner.eINCREMENTAL,
	/**
	 * Build strategy for [PxSceneQueryDesc.staticStructure].
	 *
	 * This parameter is used to refine / control the build strategy of [PxSceneQueryDesc.staticStructure]. This is only
	 * used with [PxPruningStructureType.eDYNAMIC_AABB_TREE] and [PxPruningStructureType.eSTATIC_AABB_TREE].
	 *
	 * @see PxBVHBuildStrategy
	 * @see PxSceneQueryDesc.staticStructure
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(4) var staticBVHBuildStrategy: PxBVHBuildStrategy = PxBVHBuildStrategy.eFAST,
	/**
	 * Build strategy for [PxSceneQueryDesc.dynamicStructure].
	 *
	 * This parameter is used to refine / control the build strategy of [PxSceneQueryDesc.dynamicStructure]. This is only
	 * used with [PxPruningStructureType.eDYNAMIC_AABB_TREE] and [PxPruningStructureType.eSTATIC_AABB_TREE].
	 *
	 * @see PxBVHBuildStrategy
	 * @see PxSceneQueryDesc.dynamicStructure
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(5) var dynamicBVHBuildStrategy: PxBVHBuildStrategy = PxBVHBuildStrategy.eFAST,
	/**
	 * Number of objects per node for [PxSceneQueryDesc.staticStructure].
	 *
	 * This parameter is used to refine / control the number of objects per node for [PxSceneQueryDesc.staticStructure].
	 * This is only used with [PxPruningStructureType.eDYNAMIC_AABB_TREE] and [PxPruningStructureType.eSTATIC_AABB_TREE].
	 *
	 * This parameter has an impact on how quickly the structure gets built, and on the per-frame cost of maintaining
	 * the structure. Increasing this value gives smaller AABB-trees that use less memory, are faster to build and
	 * update, but it can lead to slower queries.
	 *
	 * @see PxSceneQueryDesc.staticStructure
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(6) var staticNbObjectsPerNode: PxU32_t = 4u,
	/**
	 * Number of objects per node for [PxSceneQueryDesc.dynamicStructure].
	 *
	 * This parameter is used to refine / control the number of objects per node for [PxSceneQueryDesc.dynamicStructure].
	 * This is only used with [PxPruningStructureType.eDYNAMIC_AABB_TREE] and [PxPruningStructureType.eSTATIC_AABB_TREE].
	 *
	 * This parameter has an impact on how quickly the structure gets built, and on the per-frame cost of maintaining
	 * the structure. Increasing this value gives smaller AABB-trees that use less memory, are faster to build and
	 * update, but it can lead to slower queries.
	 *
	 * @see PxSceneQueryDesc.dynamicStructure
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(7) var dynamicNbObjectsPerNode: PxU32_t = 4u,
	/**
	 * Defines the scene query update mode.
	 *
	 * @see PxSceneQuerySystemBase.setUpdateMode
	 * @see PxSceneQuerySystemBase.getUpdateMode
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(8) var sceneQueryUpdateMode: PxSceneQueryUpdateMode = PxSceneQueryUpdateMode.eBUILD_ENABLED_COMMIT_ENABLED
)