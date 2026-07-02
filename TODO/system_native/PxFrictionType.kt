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
 * Enum for selecting the friction algorithm used for simulation.
 *
 * [PxFrictionType.ePATCH] is the default friction logic (Couloumb type friction model). Friction gets computed per contact patch.
 * Up to two contact points lying in the contact patch area are selected as friction anchors to which friction impulses are applied. If there
 * are more than two contact points, to select anchors from, the anchors are selected using a heuristic that tries to maximize the distance
 * between the anchors within the contact patch area. For each contact patch, two perpendicular axes of the contact patch plane are selected.
 * A 1D-constraint along each of the two axes is used to implement friction at a friction anchor point. Note that the two axes are processed
 * separately when the PGS solver type is selected. This can lead to asymmetries when transitioning from dynamic to static friction and vice
 * versa in certain edge cases. The TGS solver type, on the other hand, works with the combined impulse along the two axes and as such avoids
 * this potential problem, but this is slightly more computationally expensive. Another difference between TGS and PGS is that TGS applies
 * friction throughout all position and all velocity iterations, while PGS by default applies friction throughout the last 3 position iterations
 * and all velocity iterations (unless [PxSceneFlag.eENABLE_FRICTION_EVERY_ITERATION] is used).
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
@Suppress("EnumEntryName")
@Deprecated("Since only the patch friction model is supported now, the friction type option is obsolete.")
enum class PxFrictionType {
	/**
	 * Select default patch-friction model
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	ePATCH
}