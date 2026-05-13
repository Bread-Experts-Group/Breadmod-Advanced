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

abstract class PhysXSceneSQSystem : PhysXSceneQuerySystemBase() {
	/**
	 * Return the value of [PhysXSceneQueryDesc.staticStructure] that was set when creating the scene with [PhysXPhysics.createScene]
	 *
	 * @see PhysXSceneQueryDesc.staticStructure
	 * @see PhysXPhysics.createScene
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(0) abstract fun getStaticStructure(
		selfPtr: MemorySegment
	): PxPruningStructureType

	/**
	 * Return the value of [PhysXSceneQueryDesc.dynamicStructure] that was set when creating the scene with [PhysXPhysics.createScene]
	 *
	 * @see PhysXSceneQueryDesc.dynamicStructure
	 * @see PhysXPhysics.createScene
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(1) abstract fun getDynamicStructure(
		selfPtr: MemorySegment
	): PxPruningStructureType

	/**
	 * Executes scene queries update tasks.
	 *
	 * This function will refit dirty shapes within the pruner and will execute a task to build a new AABB tree, which is
	 * build on a different thread. The new AABB tree is built based on the dynamic tree rebuild hint rate. Once
	 * the new tree is ready it will be commited in next fetchQueries call, which must be called after.
	 *
	 * This function is equivalent to the following PxSceneQuerySystem calls:
	 * Synchronous calls:
	 * 	- PxSceneQuerySystemBase.flushUpdates()
	 * 	- handle0 = PxSceneQuerySystem::prepareSceneQueryBuildStep(PX_SCENE_PRUNER_STATIC)
	 * 	- handle1 = PxSceneQuerySystem::prepareSceneQueryBuildStep(PX_SCENE_PRUNER_DYNAMIC)
	 * Asynchronous calls:
	 * 	- PxSceneQuerySystem::sceneQueryBuildStep(handle0);
	 * 	- PxSceneQuerySystem::sceneQueryBuildStep(handle1);
	 *
	 * This function is part of the [PhysXSceneSQSystem] interface because it uses the [PhysXScene] task system under the hood. But
	 * it calls [PxSceneQuerySystem] functions, which are independent from this system and could be called in a similar
	 * fashion by a separate, possibly user-defined task manager.
	 *
	 * *If [PxSceneQueryUpdateMode.eBUILD_DISABLED_COMMIT_DISABLED] is used, it is required to update the scene queries
	 * using this function.*
	 *
	 * @param completionTask if non-NULL, this task will have its refcount incremented in [sceneQueryUpdate], then
	 * decremented when the scene is ready to have [fetchQueries] called. So the task will not run until the
	 * application also calls [removeReference].
	 * @param controlSimulation if true, the scene controls its [PxTaskManager] simulation state. Leave
	 * true unless the application is calling the [PxTaskManager] [startSimulation]/[stopSimulation] methods itself.
	 *
	 * @see [PxSceneQueryUpdateMode.eBUILD_DISABLED_COMMIT_DISABLED]
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(2) abstract fun sceneQueriesUpdate(
		selfPtr: MemorySegment,
		completionTask: MemorySegment = MemorySegment.NULL, // TODO: PxBaseTask
		controlSimulation: Boolean = true
	)

	/**
	 * This checks to see if the scene queries update has completed.
	 *
	 * This does not cause the data available for reading to be updated with the results of the scene queries update, it is simply a status check.
	 * The bool will allow it to either return immediately or block waiting for the condition to be met so that it can return true
	 *
	 * @param block When set to true will block until the condition is met.
	 * @return True if the results are available.
	 *
	 * @see sceneQueriesUpdate
	 * @see fetchResults
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(3) abstract fun checkQueries(
		selfPtr: MemorySegment,
		block: Boolean = false
	): Boolean

	/**
	 * This method must be called after [sceneQueriesUpdate]. It will wait for the scene queries update to finish. If the user makes an illegal scene queries update call,
	 * the SDK will issue an error message.
	 *
	 * If a new AABB tree build finished, then during fetchQueries the current tree within the pruning structure is swapped with the new tree.
	 *
	 * @param block When set to true will block until the condition is met, which is tree built task must finish running.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(4) abstract fun fetchQueries(
		selfPtr: MemorySegment,
		block: Boolean = false
	): Boolean
}