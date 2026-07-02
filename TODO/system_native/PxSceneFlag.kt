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
import org.bread_experts_group.generic.Flaggable

/**
 * flags for configuring properties of the scene
 *
 * @see PhysXScene
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
enum class PxSceneFlag : Flaggable {
	/**
	 * Enable Active Actors Notification.
	 *
	 * This flag enables the Active Actor Notification feature for a scene.  This
	 * feature defaults to disabled.  When disabled, the function
	 * [PhysXScene.getActiveActors] will always return a NULL list.
	 *
	 * *There may be a performance penalty for enabling the Active Actor Notification, hence this flag should
	 * only be enabled if the application intends to use the feature.*
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eENABLE_ACTIVE_ACTORS,

	/**
	 * Enables a second broad phase check after integration that makes it possible to prevent objects from tunneling
	 * through eachother.
	 *
	 * [PxPairFlag.eDETECT_CCD_CONTACT] requires this flag to be specified.
	 *
	 * *For this feature to be effective for bodies that can move at a significant velocity, the user should raise the
	 * flag [PxRigidBodyFlag.eENABLE_CCD] for them.*
	 *
	 * @see PxRigidBodyFlag.eENABLE_CCD
	 * @see PxPairFlag.eDETECT_CCD_CONTACT
	 * @see eDISABLE_CCD_RESWEEP
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eENABLE_CCD,

	/**
	 * Enables a simplified swept integration strategy, which sacrifices some accuracy for improved performance.
	 *
	 * This simplified swept integration approach makes certain assumptions about the motion of objects that are not made
	 * when using a full swept integration. These assumptions usually hold but there are cases where they could result
	 * in incorrect behavior between a set of fast-moving rigid bodies. A key issue is that fast-moving dynamic objects
	 * may tunnel through each-other after a rebound. This will not happen if this mode is disabled. However, this
	 * approach will be potentially faster than a full swept integration because it will perform significantly fewer
	 * sweeps in non-trivial scenes involving many fast-moving objects. This approach should successfully resist objects
	 * passing through the static environment.
	 *
	 * [PxPairFlag.eDETECT_CCD_CONTACT] requires this flag to be specified.
	 *
	 * *This scene flag requires [eENABLE_CCD] to be enabled as well. If it is not, this scene flag will do nothing.*
	 * *For this feature to be effective for bodies that can move at a significant velocity, the user should raise the
	 * flag [PxRigidBodyFlag.eENABLE_CCD] for them.*
	 *
	 * @see PxRigidBodyFlag.eENABLE_CCD
	 * @see PxPairFlag.eDETECT_CCD_CONTACT
	 * @see eENABLE_CCD
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eDISABLE_CCD_RESWEEP,

	RESERVED_0,
	RESERVED_1,
	RESERVED_2,

	/**
	 * Enable GJK-based distance collision detection system.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eENABLE_PCM,

	/**
	 * Disable contact report buffer resize. Once the contact buffer is full, the rest of the contact reports will
	 * not be buffered and sent.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eDISABLE_CONTACT_REPORT_BUFFER_RESIZE,

	/**
	 * Disable contact cache.
	 *
	 * Contact caches are used internally to provide faster contact generation. You can disable all contact caches
	 * if memory usage for this feature becomes too high.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eDISABLE_CONTACT_CACHE,

	/**
	 * Require scene-level locking
	 *
	 * When set to true this requires that threads accessing the [PhysXScene] use the
	 * multi-threaded lock methods.
	 *
	 * @see PhysXScene.lockRead
	 * @see PhysXScene.unlockRead
	 * @see PhysXScene.lockWrite
	 * @see PhysXScene.unlockWrite
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eREQUIRE_RW_LOCK,

	/**
	 * Enables additional stabilization pass in solver
	 *
	 * When set to true, this enables additional stabilization processing to improve that stability of complex
	 * interactions between large numbers of bodies.
	 *
	 * This is an experimental feature which does result in some loss of momentum.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	*/
	eENABLE_STABILIZATION,

	/**
	 * Enables average points in contact manifolds
	 *
	 * When set to true, this enables additional contacts to be generated per manifold to represent the
	 * average point in a manifold. This can stabilize stacking when only a small
	 * number of solver iterations is used.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eENABLE_AVERAGE_POINT,

	/**
	 * Do not report kinematics in list of active actors.
	 *
	 * Since the target pose for kinematics is set by the user, an application can track the activity state directly and use
	 * this flag to avoid that kinematics get added to the list of active actors.
	 *
	 * *This flag has only an effect in combination with [eENABLE_ACTIVE_ACTORS].*
	 *
	 * @see eENABLE_ACTIVE_ACTORS
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eEXCLUDE_KINEMATICS_FROM_ACTIVE_ACTORS,

	/**
	 * Enables the GPU dynamics pipeline
	 *
	 * When set to true, a CUDA ARCH 3.0 or above-enabled NVIDIA GPU is present and the CUDA context manager has been
	 * configured, this will run the GPU dynamics pipelin instead of the CPU dynamics pipeline.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	*/
	eENABLE_GPU_DYNAMICS,

	/**
	 * Provides improved determinism at the expense of performance.
	 *
	 * By default, PhysX provides limited determinism guarantees. Specifically, PhysX guarantees that the exact
	 * scene (same actors created in the same order) and simulated using the same
	 * time-stepping scheme should provide the exact same behaviour.
	 *
	 * However, if additional actors are added to the simulation, this can affect the behaviour of the existing actors
	 * in the simulation, even if the set of new actors do not interact with the existing actors.
	 *
	 * This flag provides an additional level of determinism that guarantees that the simulation will not change if
	 * additional actors are added to the simulation, provided those actors do not interfere with the existing actors in
	 * the scene. Determinism is only guaranteed if the actors are inserted in a consistent order each run in a
	 * newly-created scene and simulated using a consistent time-stepping scheme.
	 *
	 * Note that enabling this flag can have a negative impact on performance.
	 *
	 * Note that this feature is not currently supported on GPU.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eENABLE_ENHANCED_DETERMINISM,

	/**
	 * Controls processing friction in all solver iterations
	 *
	 * By default, PhysX processes friction only in the final 3 position iterations, and all velocity
	 * iterations. This flag enables friction processing in all position and velocity iterations.
	 *
	 * The default behaviour provides a good trade-off between performance and stability and is aimed
	 * primarily at game development.
	 *
	 * When simulating more complex frictional behaviour, such as grasping of complex geometries with
	 * a robotic manipulator, better results can be achieved by enabling friction in all solver iterations.
	 *
	 * *This flag only has effect with the default solver. The TGS solver always performs friction per-iteration.*
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eENABLE_FRICTION_EVERY_ITERATION,

	/**
	 * Controls application of gravity and other external forces per TGS solver position iterations
	 *
	 * By default, external forces such as gravity are applied just once at the beginning of each simulate() call. With this
	 * flag enabled the same forces are applied in each sub time step (position iteration) of the TGS solver, leading to greater stability and better solver convergence.
	 * One consequence is that a body in freefall will move a shorter distance over the entire simulation step if the flag is raised.
	 *
	 * Note that raising this flag makes the distance traveled under freefall dependent on the number of solver iterations.
	 * Since solver iterations are determined per-island, bodies assigned to an island with fewer solver iterations will travel a larger distance than bodies assigned to an island with more iterations.
	 *
	 * *This feature is only supported for the TGS solver.*
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eENABLE_EXTERNAL_FORCES_EVERY_ITERATION_TGS,

	/**
	 * Enables the direct-GPU API. Raising this flag is only allowed if eENABLE_GPU_DYNAMICS is raised and
	 * [PxBroadPhaseType.eGPU] is used.
	 *
	 * This is useful if your application only needs to communicate to the GPU via GPU buffers. Can be significantly
	 * faster.
	 *
	 * *Enabling the direct-GPU API will disable the readback of simulation state from GPU to CPU. Simulation outputs
	 *  can only be accessed using the direct-GPU API functions in PxDirectGPUAPI ([PxDirectGPUAPI.getRigidDynamicData],
	 *  [PxDirectGPUAPI.getArticulationData], [PxDirectGPUAPI.copyContactData]), and reading state directly from the actor
	 *  is not allowed.*
	 *
	 * @see PhysXScene.getDirectGPUAPI
	 * @see PxDirectGPUAPI
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eENABLE_DIRECT_GPU_API,

	/**
	 * Enables the computation of body accelerations for PxRigidDynamic actors.
	 *
	 * By default PhysX does not compute per-body accelerations for PxRigidDynamic actors (only for articulation links).
	 * This flag tells the system to compute them.
	 *
	 * Retrieve the accelerations using [PxRigidBody.getLinearAcceleration] and [PxRigidBody.getAngularAcceleration].
	 *
	 * If the flag is not enabled these functions will return valid accelerations for PxArticulationLink objects, but
	 * it will return zero for PxRigidDynamic actors.
	 *
	 * If the flag is enabled, these functions will return valid accelerations for both PxArticulationLink and
	 * PxRigidDynamic objects.
	 *
	 * This flag also enables [PxRigidDynamicGPUAPIReadType.eLINEAR_ACCELERATION] and [PxRigidDynamicGPUAPIReadType.eANGULAR_ACCELERATION]
	 * in the direct GPU API.
	 *
	 * @see PxRigidBody.getLinearAcceleration
	 * @see PxRigidBody.getAngularAcceleration
	 * @see PxRigidDynamicGPUAPIReadType
	 * @see PxDirectGPUAPI
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eENABLE_BODY_ACCELERATIONS,

	/**
	 * Enables the solver residual reporting.
	 *
	 * *Enabling this flag can have a negative impact on the performance but the impact should be small.*
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eENABLE_SOLVER_RESIDUAL_REPORTING;

	override val position: Long = 1L shl ordinal
}

val eMUTABLE_FLAGS: FlagSet<PxSceneFlag> = FlagSet.of(
	PxSceneFlag.eENABLE_ACTIVE_ACTORS,
	PxSceneFlag.eEXCLUDE_KINEMATICS_FROM_ACTIVE_ACTORS
)