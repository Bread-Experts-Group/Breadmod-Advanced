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
 * Base class for the scene-query system.
 *
 * Methods defined here are common to both the traditional [PhysXScene] API and the [PhysXSceneQuerySystem] API.
 *
 * @see PhysXScene
 * @see PhysXSceneQuerySystem
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
@Suppress("FunctionName")
abstract class PhysXSceneQuerySystemBase {
	@VirtualFunction(0) abstract fun `~PhysXSceneQuerySystemBase`(selfPtr: MemorySegment)

	/**
	 * Sets the rebuild rate of the dynamic tree pruning structures.
	 *
	 * @param dynamicTreeRebuildRateHint Rebuild rate of the dynamic tree pruning structures.
	 *
	 * @see PhysXSceneQueryDesc.dynamicTreeRebuildRateHint
	 * @see getDynamicTreeRebuildRateHint
	 * @see forceRebuildDynamicTree
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(1) abstract fun setDynamicTreeRebuildRateHint(
		selfPtr: MemorySegment,
		dynamicTreeRebuildRateHint: PxU32_t
	)

	/**
	 * Retrieves the rebuild rate of the dynamic tree pruning structures.
	 *
	 * @return The rebuild rate of the dynamic tree pruning structures.
	 *
	 * @see PhysXSceneQueryDesc.dynamicTreeRebuildRateHint
	 * @see setDynamicTreeRebuildRateHint
	 * @see forceRebuildDynamicTree
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(2) abstract fun getDynamicTreeRebuildRateHint(
		selfPtr: MemorySegment
	): PxU32_t

	/**
	 * Forces dynamic trees to be immediately rebuilt.
	 *
	 * @param prunerIndex	Index of pruner containing the dynamic tree to rebuild
	 *
	 * *PxScene will call this function with the [PX_SCENE_PRUNER_STATIC] or [PX_SCENE_PRUNER_DYNAMIC] value.*
	 *
	 * @see PhysXSceneQueryDesc.dynamicTreeRebuildRateHint
	 * @see setDynamicTreeRebuildRateHint
	 * @see getDynamicTreeRebuildRateHint
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(3) abstract fun forceRebuildDynamicTree(
		selfPtr: MemorySegment,
		prunerIndex: PxU32_t
	)

	/**
	 * Sets scene query update mode
	 *
	 * @param updateMode	Scene query update mode.
	 *
	 * @see PxSceneQueryUpdateMode
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(4) abstract fun setUpdateMode(
		selfPtr: MemorySegment,
		updateMode: PxSceneQueryUpdateMode
	)

	/**
	 * Gets scene query update mode
	 *
	 * @return Current scene query update mode.
	 *
	 * @see PxSceneQueryUpdateMode
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(5) abstract fun getUpdateMode(
		selfPtr: MemorySegment
	): PxSceneQueryUpdateMode

	/**
	 * Retrieves the system's internal scene query timestamp, increased each time a change to the
	 * static scene query structure is performed.
	 *
	 * @return scene query static timestamp
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(6) abstract fun getStaticTimestamp(
		selfPtr: MemorySegment
	): PxU32_t

	/**
	 * Flushes any changes to the scene query representation.
	 *
	 * This method updates the state of the scene query representation to match changes in the scene state.
	 *
	 * By default, these changes are buffered until the next query is submitted. Calling this function will not change
	 * the results from scene queries, but can be used to ensure that a query will not perform update work in the course of
	 * its execution.
	 *
	 * A thread performing updates will hold a write lock on the query structure, and thus stall other querying threads. In multithread
	 * scenarios it can be useful to explicitly schedule the period where this lock may be held for a significant period, so that
	 * subsequent queries issued from multiple threads will not block.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(7) abstract fun flushUpdates(
		selfPtr: MemorySegment
	)

	/**
	 * Performs a raycast against objects in the scene, returns results in a [PxRaycastBuffer] object
	 * or via a custom user callback implementation inheriting from [PxRaycastCallback].
	 *
	 * *Touching hits are not ordered.*
	 *
	 * *Shooting a ray from within an object leads to different results depending on the shape type. Please check the
	 * details in user guide article SceneQuery. User can ignore such objects by employing one of the provided filter
	 * mechanisms.*
	 *
	 * @param origin		Origin of the ray.
	 * @param unitDir		Normalized direction of the ray.
	 * @param distance		Length of the ray. Has to be in the [0, inf) range.
	 * @param hitCall		Raycast hit buffer or callback object used to report raycast hits.
	 * @param hitFlags		Specifies which properties per hit should be computed and returned via the hit callback.
	 * @param filterData	Filtering data passed to the filter shader.
	 * @param filterCall	Custom filtering logic (optional). Only used if the corresponding #PxQueryFlag flags are set. If NULL, all hits are assumed to be blocking.
	 * @param cache		Cached hit shape (optional). Ray is tested against cached shape first. If no hit is found the ray gets queried against the scene.
	 * 						Note: Filtering is not executed for a cached shape if supplied; instead, if a hit is found, it is assumed to be a blocking hit.
	 * 						Note: Using past touching hits as cache will produce incorrect behavior since the cached hit will always be treated as blocking.
	 * @param queryFlags	Optional flags controlling the query.
	 *
	 * @return True if any touching or blocking hits were found or any hit was found in case PxQueryFlag::eANY_HIT was specified.
	 *
	 * @see PxRaycastCallback
	 * @see PxRaycastBuffer
	 * @see PxQueryFilterData
	 * @see PxQueryFilterCallback
	 * @see PxQueryCache
	 * @see PxRaycastHit
	 * @see PxQueryFlag
	 * @see PxQueryFlag.eANY_HIT
	 * @see PxGeometryQueryFlag
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(8) abstract fun raycast(
		selfPtr: MemorySegment,
		origin: MemorySegment, // TODO: PxVec3
		unitDir: MemorySegment, // TODO: PxVec3
		distance: PxReal_t, // TODO: PxReal
		hitCall: MemorySegment, // TODO: PxRaycastCallback
		hitFlags: PxU16_t, // TODO: PxHitFlags = eDEFAULT
		filterData: MemorySegment, // TODO: PxQueryFilterData = PxQueryFilterData()
		filterCall: MemorySegment = MemorySegment.NULL, // TODO: PxQueryFilterCallback
		cache: MemorySegment = MemorySegment.NULL, // TODO: PxQueryCache
		queryFlags: PxU32_t // TODO: PxGeometryQueryFlags = eDEFAULT
	): Boolean

	/**
	 * Performs a sweep test against objects in the scene, returns results in a [PxSweepBuffer] object
	 * or via a custom user callback implementation inheriting from [PxSweepCallback].
	 *
	 * *Touching hits are not ordered.*
	 *
	 * *If a shape from the scene is already overlapping with the query shape in its starting position,
	 * the hit is returned unless [eASSUME_NO_INITIAL_OVERLAP] was specified.*
	 *
	 * @param geometry		Geometry of object to sweep (supported types are: box, sphere, capsule, convex core, convex mesh).
	 * @param pose			Pose of the sweep object.
	 * @param unitDir		Normalized direction of the sweep.
	 * @param distance		Sweep distance. Needs to be in [0, inf) range and >0 if [eASSUME_NO_INITIAL_OVERLAP] was specified. Will be clamped to PX_MAX_SWEEP_DISTANCE.
	 * @param hitCall		Sweep hit buffer or callback object used to report sweep hits.
	 * @param hitFlags		Specifies which properties per hit should be computed and returned via the hit callback.
	 * @param filterData	Filtering data and simple logic.
	 * @param filterCall	Custom filtering logic (optional). Only used if the corresponding #PxQueryFlag flags are set. If NULL, all hits are assumed to be blocking.
	 * @param cache		Cached hit shape (optional). Sweep is performed against cached shape first. If no hit is found the sweep gets queried against the scene.
	 * 						Note: Filtering is not executed for a cached shape if supplied; instead, if a hit is found, it is assumed to be a blocking hit.
	 * 						Note: Using past touching hits as cache will produce incorrect behavior since the cached hit will always be treated as blocking.
	 * @param inflation	This parameter creates a skin around the swept geometry which increases its extents for sweeping. The sweep will register a hit as soon as the skin touches a shape, and will return the corresponding distance and normal.
	 * 						Note: ePRECISE_SWEEP doesn't support inflation. Therefore the sweep will be performed with zero inflation.
	 * @param queryFlags	Optional flags controlling the query.
	 *
	 * @return True if any touching or blocking hits were found or any hit was found in case [PxQueryFlag.eANY_HIT] was specified.
	 *
	 * @see PxSweepCallback
	 * @see PxSweepBuffer
	 * @see PxQueryFilterData
	 * @see PxQueryFilterCallback
	 * @see PxSweepHit
	 * @see PxQueryCache
	 * @see PxGeometryQueryFlag
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(9) abstract fun sweep(
		selfPtr: MemorySegment,
		geometry: MemorySegment, // TODO: PxGeometry
		pose: MemorySegment, // TODO: PxTransform
		unitDir: MemorySegment, // TODO: PxVec3
		distance: PxReal_t,
		hitCall: MemorySegment, // TODO: PxSweepCallback
		hitFlags: PxU16_t, // TODO: PxHitFlags eDEFAULT
		filterData: MemorySegment, // TODO: PxQueryFilterData = PxQueryFilterData()
		filterCall: MemorySegment = MemorySegment.NULL, // TODO: PxQueryFilterCallback
		cache: MemorySegment = MemorySegment.NULL, // TODO: PxQueryCache
		inflation: PxReal_t = 0f,
		queryFlags: PxU32_t // TODO: PxGeometryQueryFlags eDEFAULT
	): Boolean

	/**
	 * Performs an overlap test of a given geometry against objects in the scene, returns results in a [PxOverlapBuffer] object
	 * or via a custom user callback implementation inheriting from [PxOverlapCallback].
	 *
	 * *Filtering: returning [eBLOCK] from user filter for overlap queries will cause a warning (see [PxQueryHitType]).*
	 *
	 * @param geometry		Geometry of object to check for overlap (supported types are: box, sphere, capsule, convex core, convex mesh).
	 * @param pose			Pose of the object.
	 * @param hitCall		Overlap hit buffer or callback object used to report overlap hits.
	 * @param filterData	Filtering data and simple logic. See #PxQueryFilterData #PxQueryFilterCallback
	 * @param filterCall	Custom filtering logic (optional). Only used if the corresponding #PxQueryFlag flags are set. If NULL, all hits are assumed to overlap.
	 * @param cache		Cached hit shape (optional). Overlap is performed against cached shape first. If no hit is found the overlap gets queried against the scene.
	 * @param queryFlags	Optional flags controlling the query.
	 * Note: Filtering is not executed for a cached shape if supplied; instead, if a hit is found, it is assumed to be a blocking hit.
	 *
	 * Note: Using past touching hits as cache will produce incorrect behavior since the cached hit will always be treated as blocking.
	 *
	 * @return True if any touching or blocking hits were found or any hit was found in case [PxQueryFlag.eANY_HIT] was specified.
	 *
	 * *[eBLOCK] should not be returned from user filters for overlap(). Doing so will result in undefined behavior, and a warning will be issued.*
	 *
	 * *If the [PxQueryFlag.eNO_BLOCK] flag is set, the [eBLOCK] will instead be automatically converted to an [eTOUCH] and the warning suppressed.*
	 *
	 * @see PxOverlapCallback
	 * @see PxOverlapBuffer
	 * @see PxHitFlags
	 * @see PxQueryFilterData
	 * @see PxQueryFilterCallback
	 * @see PxGeometryQueryFlag
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@VirtualFunction(10) abstract fun overlap(
		selfPtr: MemorySegment,
		geometry: MemorySegment, // TODO: PxGeometry
		pose: MemorySegment, // TODO: PxTransform
		hitCall: MemorySegment, // TODO: PxOverlapCallback
		filterData: MemorySegment, // TODO: PxQueryFilterData = PxQueryFilterData()
		filterCall: MemorySegment = MemorySegment.NULL, // TODO: PxQueryFilterCallback
		cache: MemorySegment = MemorySegment.NULL, // TODO: PxQueryCache
		queryFlags: PxU32_t // TODO: PxGeometryQueryFlags eDEFAULT
	): Boolean
}