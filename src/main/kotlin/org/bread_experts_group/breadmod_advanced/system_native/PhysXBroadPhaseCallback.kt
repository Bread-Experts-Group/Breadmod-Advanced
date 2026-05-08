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
 * Broad-phase callback to receive broad-phase related events.
 *
 * Each broadphase callback object is associated with a PxClientID. It is possible to register different
 * callbacks for different clients. The callback functions are called this way:
 * - for shapes/actors, the callback assigned to the actors' clients are used
 * - for aggregates, the callbacks assigned to clients from aggregated actors  are used
 *
 * *SDK state should not be modified from within the callbacks. In particular objects should not
 * be created or destroyed. If state modification is needed then the changes should be stored to a buffer
 * and performed after the simulation step.*
 *
 * **Threading:** It is not necessary to make this class thread safe as it will only be called in the context of the
 * user thread.
 *
 * @see PhysXSceneDesc
 * @see PhysXScene.setBroadPhaseCallback
 * @see PhysXScene.getBroadPhaseCallback
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
interface PhysXBroadPhaseCallback {
	/**
	 * Out-of-bounds notification.
	 *
	 * This function is called when an object leaves the broad-phase.
	 *
	 * @param shape	Shape that left the broad-phase bounds
	 * @param actor	Owner actor
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun onObjectOutOfBounds(shape: PhysXShape, actor: PhysXActor)

	/**
	 * Out-of-bounds notification.
	 *
	 * This function is called when an aggregate leaves the broad-phase.
	 *
	 * @param aggregate	Aggregate that left the broad-phase bounds
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun onObjectOutOfBounds(aggregate: PhysXAggregate)
}