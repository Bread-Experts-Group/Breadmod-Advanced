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
 * Collection of flags describing the filter actions to take for a collision pair.
 *
 * @see PxSimulationFilterShader
 * @see PhysXSimulationFilterCallback
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
@Suppress("EnumEntryName")
enum class PxFilterFlag {
	/**
	 * Ignore the collision pair as long as the bounding volumes of the pair objects overlap.
	 *
	 * Killed pairs will be ignored by the simulation and won't run through the filter again until one
	 * of the following occurs:
	 *
	 * - The bounding volumes of the two objects overlap again (after being separated)
	 * - The user enforces a re-filtering (see [PhysXScene.resetFiltering])
	 *
	 * @see PhysXScene.resetFiltering
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eKILL,

	/**
	 * Ignore the collision pair as long as the bounding volumes of the pair objects overlap or until
	 * filtering relevant data changes for one of the collision objects.
	 *
	 * Suppressed pairs will be ignored by the simulation and won't make another filter request until one
	 * of the following occurs:
	 *
	 * - Same conditions as for killed pairs (see #eKILL)
	 * - The filter data or the filter object attributes change for one of the collision objects
	 *
	 * @see PhysXFilterData
	 * @see PhysXFilterObjectAttributes
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eSUPPRESS,

	/**
	 * Invoke the filter callback ([PhysXSimulationFilterCallback.pairFound]) for this collision pair.
	 *
	 * @see PhysXSimulationFilterCallback
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eCALLBACK,

	INTERNAL_USE_0;

	companion object {
		/**
		 * Track this collision pair with the filter callback mechanism.
		 *
		 * When the bounding volumes of the collision pair lose contact, the filter callback [PhysXSimulationFilterCallback.pairLost]
		 * will be invoked. Furthermore, the filter status of the collision pair can be adjusted through [PhysXSimulationFilterCallback.statusChange]
		 * once per frame (until a pairLost() notification occurs).
		 *
		 * @see PhysXSimulationFilterCallback
		 *
		 * @author Miko Elbrecht (Kotlin)
		 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
		 * @since In accordance with PhysX 5.6.1
		 */
		val eNOTIFY: FlagSet<PxFilterFlag> = FlagSet.of(
			INTERNAL_USE_0,
			eCALLBACK
		)

		/**
		 * Provided default to get standard behavior:
		 *
		 * The application configure the pair's collision properties once when bounding volume overlap is found and
		 * doesn't get asked again about that pair until overlap status or filter properties changes, or re-filtering is requested.
		 *
		 * No notification is provided when bounding volume overlap is lost
		 *
		 * The pair will not be killed or suppressed, so collision detection will be processed
		 *
		 * @author Miko Elbrecht (Kotlin)
		 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
		 * @since In accordance with PhysX 5.6.1
		 */
		val eDEFAULT: FlagSet<PxFilterFlag> = FlagSet(PxFilterFlag::class.java, 0)
	}
}