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
 * An interface class that the user can implement in order to receive simulation events.
 *
 * With the exception of [onAdvance], the events get sent during the call to either [PhysXScene.fetchResults] or
 * [PhysXScene.flushSimulation] with sendPendingReports=true. [onAdvance] gets called while the simulation
 * is running (that is between [PhysXScene.simulate] or [PhysXScene.advance] and [PhysXScene.fetchResults]).
 *
 * *SDK state should not be modified from within the callbacks. In particular objects should not
 * be created or destroyed. If state modification is needed then the changes should be stored to a buffer
 * and performed after the simulation step.*
 *
 * **Threading:** With the exception of [onAdvance], it is not necessary to make these callbacks thread safe as
 * they will only be called in the context of the user thread.
 *
 * @see PhysXScene.setSimulationEventCallback
 * @see PhysXScene.getSimulationEventCallback
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
interface PhysXSimulationEventCallback {
	/**
	 * This is called when a breakable constraint breaks.
	 *
	 * *The user should not release the constraint shader inside this call!*
	 *
	 * *No event will get reported if the constraint breaks but gets deleted while the time step is still being simulated.*
	 *
	 * @param constraints The constraints which have been broken.
	 *
	 * @see PhysXConstraint
	 * @see PhysXConstraintDesc.linearBreakForce
	 * @see PhysXConstraintDesc.angularBreakForce
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun onConstraintBreak(constraints: Array<PhysXConstraintInfo>)

	/**
	 * This is called with the actors which have just been woken up.
	 *
	 * *Only supported by rigid bodies yet.*
	 * *Only called on actors for which the [PhysXActorFlag.eSEND_SLEEP_NOTIFIES] has been set.*
	 * *Only the latest sleep state transition happening between fetchResults() of the previous frame and fetchResults() of the current frame
	 * will get reported. For example, let us assume actor A is awake, then A->putToSleep() gets called, then later A->wakeUp() gets called.
	 * At the next simulate/fetchResults() step only an onWake() event will get triggered because that was the last transition.*
	 * *If an actor gets newly added to a scene with properties such that it is awake and the sleep state does not get changed by
	 * the user or simulation, then an onWake() event will get sent at the next simulate/fetchResults() step.*
	 *
	 * @param actors The actors which just woke up.
	 *
	 * @see PhysXScene.setSimulationEventCallback
	 * @see PhysXSceneDesc.simulationEventCallback
	 * @see PhysXActorFlag
	 * @see PhysXActor.setActorFlag
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun onWake(actors: Array<PhysXActor>)

	/**
	 * This is called with the actors which have just been put to sleep.
	 *
	 * *Only supported by rigid bodies yet.*
	 * *Only called on actors for which the [PxActorFlag.eSEND_SLEEP_NOTIFIES] has been set.*
	 * *Only the latest sleep state transition happening between fetchResults() of the previous frame and fetchResults() of the current frame
	 * will get reported. For example, let us assume actor A is asleep, then A->wakeUp() gets called, then later A->putToSleep() gets called.
	 * At the next simulate/fetchResults() step only an onSleep() event will get triggered because that was the last transition (assuming the simulation
	 * does not wake the actor up).*
	 * *If an actor gets newly added to a scene with properties such that it is asleep and the sleep state does not get changed by
	 * the user or simulation, then an onSleep() event will get sent at the next simulate/fetchResults() step.*
	 *
	 * @param actors The actors which have just been put to sleep.
	 *
	 * @see PhysXScene.setSimulationEventCallback
	 * @see PhysXSceneDesc.simulationEventCallback
	 * @see PhysXActorFlag
	 * @see PhysXActor.setActorFlag
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun onSleep(actors: Array<PhysXActor>)

	/**
	 * This is called when certain contact events occur.
	 *
	 * The method will be called for a pair of actors if one of the colliding shape pairs requested contact notification.
	 * You request which events are reported using the filter shader/callback mechanism (see [PhysXSimulationFilterShader],
	 * [PhysXSimulationFilterCallback], [PhysXPairFlag]).
	 *
	 * Do not keep references to the passed objects, as they will be
	 * invalid after this function returns.
	 *
	 * @param pairHeader Information on the two actors whose shapes triggered a contact report.
	 * @param pairs The contact pairs of two actors for which contact reports have been requested. See [PhysXContactPair].
	 *
	 * @see PhysXScene.setSimulationEventCallback
	 * @see PhysXSceneDesc.simulationEventCallback
	 * @see PhysXContactPair
	 * @see PhysXPairFlag
	 * @see PhysXSimulationFilterShader
	 * @see PhysXSimulationFilterCallback
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun onContact(pairHeader: PhysXContactPairHeader, pairs: Array<PhysXContactPair>)

	/**
	 * This is called with the current trigger pair events.
	 *
	 * Shapes which have been marked as triggers using [PxShapeFlag.eTRIGGER_SHAPE] will send events
	 * according to the pair flag specification in the filter shader (see [PhysXPairFlag], [PhysXSimulationFilterShader]).
	 *
	 * *Trigger shapes will no longer send notification events for interactions with other trigger shapes.*
	 *
	 * @param pairs - The trigger pair events.
	 *
	 * @see PhysXScene.setSimulationEventCallback
	 * @see PhysXSceneDesc.simulationEventCallback
	 * @see PhysXPairFlag
	 * @see PhysXSimulationFilterShader
	 * @see PhysXShapeFlag
	 * @see PhysXShape.setFlag
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun onTrigger(pairs: Array<PhysXTriggerPair>)

	/**
	 * Provides early access to the new pose of moving rigid bodies.
	 *
	 * When this call occurs, rigid bodies having the [PxRigidBodyFlag::eENABLE_POSE_INTEGRATION_PREVIEW ]
	 * flag set, were moved by the simulation and their new poses can be accessed through the provided buffers.
	 *
	 * *The provided buffers are valid and can be read until the next call to [PhysXScene.simulate] or [PhysXScene.collide].*
	 *
	 * *This callback gets triggered while the simulation is running. If the provided rigid body references are used to
	 * read properties of the object, then the callback has to guarantee no other thread is writing to the same body at the same
	 * time.*
	 *
	 * *The code in this callback should be lightweight as it can block the simulation, that is, the
	 * [PhysXScene.fetchResults] call.*
	 *
	 * @param bodyBuffer The rigid bodies that moved and requested early pose reporting.
	 * @param poseBuffer The integrated rigid body poses of the bodies listed in bodyBuffer.
	 *
	 * @see PhysXScene.setSimulationEventCallback
	 * @see PhysXSceneDesc.simulationEventCallback
	 * @see PhysXRigidBodyFlag.eENABLE_POSE_INTEGRATION_PREVIEW
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun onAdvance(bodyBuffer: Array<PhysXRigidBody>, poseBuffer: Array<PxTransform_t>)
}