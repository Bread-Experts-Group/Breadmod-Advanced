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
 * Scene query update mode
 *
 * This enum controls what work is done when the scene query system is updated. The updates traditionally happen when [PhysXScene.fetchResults]
 * is called. This function then calls [PxSceneQuerySystem.finalizeUpdates], where the update mode is used.
 *
 * fetchResults/finalizeUpdates will sync changed bounds during simulation and update the scene query bounds in pruners, this work is mandatory.
 *
 * [eBUILD_ENABLED_COMMIT_ENABLED] does allow to execute the new AABB tree build step during fetchResults/finalizeUpdates, additionally
 * the pruner commit is called where any changes are applied. During commit PhysX refits the dynamic scene query tree and if a new tree
 * was built and the build finished the tree is swapped with current AABB tree.
 *
 * [eBUILD_ENABLED_COMMIT_DISABLED] does allow to execute the new AABB tree build step during fetchResults/finalizeUpdates. Pruner commit
 * is not called, this means that refit will then occur during the first scene query following fetchResults/finalizeUpdates, or may be forced
 * by the method [PhysXScene.flushQueryUpdates] / [PxSceneQuerySystemBase.flushUpdates].
 *
 * [eBUILD_DISABLED_COMMIT_DISABLED] no further scene query work is executed. The scene queries update needs to be called manually, see
 * [PxScene.sceneQueriesUpdate] (see that function's doc for the equivalent [PxSceneQuerySystem] sequence). It is recommended to call
 * [PxScene.sceneQueriesUpdate] right after fetchResults/finalizeUpdates as the pruning structures are not updated.
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
enum class PxSceneQueryUpdateMode {
	/**
	 * Both scene query build and commit are executed.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eBUILD_ENABLED_COMMIT_ENABLED,

	/**
	 * Scene query build only is executed.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eBUILD_ENABLED_COMMIT_DISABLED,

	/**
	 * No work is done, no update of scene queries
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eBUILD_DISABLED_COMMIT_DISABLED
}