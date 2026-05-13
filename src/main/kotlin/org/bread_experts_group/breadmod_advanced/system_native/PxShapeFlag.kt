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

import org.bread_experts_group.generic.Flaggable

/**
 * Flags which affect the behavior of [PhysXShapes].
 *
 * @see PhysXShape
 * @see PhysXShape.setFlag
 */
@Suppress("EnumEntryName")
enum class PxShapeFlag : Flaggable {
	/**
	 * The shape will partake in collision in the physical simulation.
	 *
	 * *It is illegal to raise the [eSIMULATION_SHAPE] and [eTRIGGER_SHAPE] flags.
	 * In the event that one of these flags is already raised the sdk will reject any
	 * attempt to raise the other.  To raise the [eSIMULATION_SHAPE] first ensure that
	 * [eTRIGGER_SHAPE] is already lowered.*
	 *
	 * *This flag has no effect if simulation is disabled for the corresponding actor (see [PxActorFlag.eDISABLE_SIMULATION]).*
	 *
	 * @see PhysXSimulationEventCallback.onContact
	 * @see PhysXScene.setSimulationEventCallback
	 * @see PhysXShape.setFlag
	 * @see PhysXShape.setFlags
	 */
	eSIMULATION_SHAPE,

	/**
	 * The shape will partake in scene queries (ray casts, overlap tests, sweeps, ...).
	 */
	eSCENE_QUERY_SHAPE,

	/**
	 * The shape is a trigger which can send reports whenever other shapes enter/leave its volume.
	 *
	 * *Triangle meshes and heightfields can not be triggers. Shape creation will fail in these cases.*
	 *
	 * *Shapes marked as triggers do not collide with other objects. If an object should act both
	 * as a trigger shape and a collision shape then create a rigid body with two shapes, one being a
	 * trigger shape and the other a collision shape. 	It is illegal to raise the [eTRIGGER_SHAPE] and
	 * [eSIMULATION_SHAPE] flags on a single [PhysXShape] instance.  In the event that one of these flags is already
	 * raised the sdk will reject any attempt to raise the other.  To raise the [eTRIGGER_SHAPE] flag first
	 * ensure that [eSIMULATION_SHAPE] flag is already lowered.*
	 *
	 * *Trigger shapes will no longer send notification events for interactions with other trigger shapes.*
	 *
	 * *Shapes marked as triggers are allowed to participate in scene queries, provided the [eSCENE_QUERY_SHAPE] flag is set. *
	 *
	 * *This flag has no effect if simulation is disabled for the corresponding actor (see [PxActorFlag.eDISABLE_SIMULATION]).*
	 *
	 * @see PhysXSimulationEventCallback.onTrigger
	 * @see PhysXScene.setSimulationEventCallback
	 * @see PhysXShape.setFlag
	 * @see PhysXShape.setFlags
	 */
	eTRIGGER_SHAPE,

	/**
	 * Enable debug renderer for this shape
	 *
	 * @see PhysXScene.getRenderBuffer
	 * @see PhysXRenderBuffer
	 * @see PhysXVisualizationParameter
	 */
	eVISUALIZATION;

	override val position: Long = 1L shl ordinal
}