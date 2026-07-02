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

/**
 * Filter callback to specify handling of collision pairs.
 *
 * This class is provided to implement more complex and flexible collision pair filtering logic, for instance, taking
 * the state of the user application into account. Filter callbacks also give the user the opportunity to track collision
 * pairs and update their filter state.
 *
 * You might want to check the documentation on [PxSimulationFilterShader] as well since it includes more general information
 * on filtering.
 *
 * *SDK state should not be modified from within the callbacks. In particular objects should not
 * be created or destroyed. If state modification is needed then the changes should be stored to a buffer
 * and performed after the simulation step.*
 *
 * The callbacks may execute in user threads or simulation threads, possibly simultaneously. The corresponding objects
 * may have been deleted by the application earlier in the frame. It is the application's responsibility to prevent race conditions
 * arising from using the SDK API in the callback while an application thread is making write calls to the scene, and to ensure that
 * the callbacks are thread-safe. Return values which depend on when the callback is called during the frame will introduce nondeterminism
 * into the simulation.
 *
 * @see PhysXSceneDesc.filterCallback
 * @see PxSimulationFilterShader
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
interface PhysXSimulationFilterCallback {
	/**
	 * Filter method to specify how a pair of potentially colliding objects should be processed.
	 *
	 * This method gets called when the filter flags returned by the filter shader (see [PxSimulationFilterShader])
	 * indicate that the filter callback should be invoked ([PxFilterFlag.eCALLBACK] or [PxFilterFlag.eNOTIFY] set).
	 * Return the [PxFilterFlag] flags and the [PxPairFlag] flags to define what the simulation should do with the given
	 * collision pair.
	 *
	 * @param pairID			Unique ID of the collision pair used to issue filter status changes for the pair (see #statusChange())
	 * @param attributes0		The filter attribute of the first object
	 * @param filterData0		The custom filter data of the first object
	 * @param a0				Actor pointer of the first object
	 * @param s0				Shape pointer of the first object (NULL if the object has no shapes)
	 * @param attributes1		The filter attribute of the second object
	 * @param filterData1		The custom filter data of the second object
	 * @param a1				Actor pointer of the second object
	 * @param s1				Shape pointer of the second object (NULL if the object has no shapes)
	 * @param pairFlags	In: Pair flags returned by the filter shader. Out: Additional information on how an accepted pair should get processed
	 * @return Filter flags defining whether the pair should be discarded, temporarily ignored or processed and whether the pair
	 * should be tracked and send a report on pair deletion through the filter callback
	 *
	 * @see PxSimulationFilterShader
	 * @see PhysXFilterData
	 * @see PhysXFilterObjectAttributes
	 * @see PxFilterFlag
	 * @see PxPairFlag
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun pairFound(
		pairID: PxU64_t,
		attributes0: PhysXFilterObjectAttributes, filterData0: PhysXFilterData, a0: PhysXActor, s0: PhysXShape,
		attributes1: PhysXFilterObjectAttributes, filterData1: PhysXFilterData, a1: PhysXActor, s1: PhysXShape,
		pairFlags: MutableGettable<FlagSet<PxPairFlag>>
	): FlagSet<PxFilterFlag>

	/**
	 * Callback to inform that a tracked collision pair is gone.
	 *
	 * This method gets called when a collision pair disappears or gets re-filtered. Only applies to
	 * collision pairs which have been marked as filter callback pairs ([PxFilterFlag.eNOTIFY] set in [pairFound]).
	 *
	 * @param pairID			Unique ID of the collision pair that disappeared
	 * @param attributes0		The filter attribute of the first object
	 * @param filterData0		The custom filter data of the first object
	 * @param attributes1		The filter attribute of the second object
	 * @param filterData1		The custom filter data of the second object
	 * @param objectRemoved	True if the pair was lost because one of the objects got removed from the scene
	 *
	 * @see pairFound
	 * @see PxSimulationFilterShader
	 * @see PhysXFilterData
	 * @see PhysXFilterObjectAttributes
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun pairLost(
		pairID: PxU64_t,
		attributes0: PhysXFilterObjectAttributes, filterData0: PhysXFilterData,
		attributes1: PhysXFilterObjectAttributes, filterData1: PhysXFilterData,
		objectRemoved: Boolean
	)

	/**
	 * Callback to give the opportunity to change the filter state of a tracked collision pair.
	 *
	 * This method gets called once per simulation step to let the application change the filter and pair
	 * flags of a collision pair that has been reported in [pairFound] and requested callbacks by
	 * setting [PxFilterFlag.eNOTIFY]. To request a change of filter status, the target pair has to be
	 * specified by its ID, the new filter and pair flags have to be provided and the method should return true.
	 *
	 * *If this method changes the filter status of a collision pair and the pair should keep being tracked
	 * by the filter callbacks then [PxFilterFlag.eNOTIFY] has to be set.*
	 *
	 * *The application is responsible to ensure that this method does not get called for pairs that have been
	 * reported as lost, see [pairLost].*
	 *
	 * @param pairID			ID of the collision pair for which the filter status should be changed
	 * @param pairFlags		The new pairFlags to apply to the collision pair
	 * @param filterFlags		The new filterFlags to apply to the collision pair
	 * @return True if the changes should be applied. In this case the method will get called again. False if
	 * no more status changes should be done in the current simulation step. In that case the provided flags will be discarded.
	 *
	 * @see pairFound
	 * @see pairLost
	 * @see PxFilterFlag
	 * @see PxPairFlag
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun statusChange(
		pairID: Mutable<PxU64_t>,
		pairFlags: Mutable<FlagSet<PxPairFlag>>,
		filterFlags: Mutable<FlagSet<PxFilterFlag>>
	): Boolean
}