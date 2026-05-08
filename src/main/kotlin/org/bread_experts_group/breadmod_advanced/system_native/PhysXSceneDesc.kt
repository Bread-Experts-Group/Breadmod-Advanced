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
import java.lang.foreign.MemorySegment

open class PhysXSceneDesc(
	/**
	 * Gravity vector.
	 *
	 * **Range:** force vector
	 *
	 * When setting gravity, you should probably also set bounce threshold.
	 *
	 * @see PhysXScene.setGravity
	 * @see PhysXScene.getGravity
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val gravity: PxVec3_t = PxVec3_t(0f, 0f, 0f),
	/**
	 * Possible notification callback.
	 *
	 * @see PhysXSimulationEventCallback
	 * @see PhysXScene.setSimulationEventCallback
	 * @see PhysXScene.getSimulationEventCallback
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val simulationEventCallback: PhysXSimulationEventCallback? = null,
	/**
	 * Possible asynchronous callback for contact modification.
	 *
	 * @see PhysXContactModifyCallback
	 * @see PhysXScene.setContactModifyCallback
	 * @see PhysXScene.getContactModifyCallback
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val contactModifyCallback: PhysXContactModifyCallback? = null,
	/**
	 * Possible asynchronous callback for contact modification.
	 *
	 * @see PhysXContactModifyCallback
	 * @see PhysXScene.setContactModifyCallback
	 * @see PhysXScene.getContactModifyCallback
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val ccdContactModifyCallback: PhysXCCDContactModifyCallback? = null,
	/**
	 * Possible asynchronous callback for post-solve operations on deformable surfaces.
	 *
	 * @see PhysXPostSolveCallback
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val deformableSurfacePostSolveCallback: PhysXPostSolveCallback? = null,
	/**
	 * Possible asynchronous callback for post-solve operations on deformable volumes.
	 *
	 * @see PhysXPostSolveCallback
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val deformableVolumePostSolveCallback: PhysXPostSolveCallback? = null,
	/**
	 * Shared global filter data which will get passed into the filter shader.
	 *
	 * *The provided data will get copied to internal buffers and this copy will be used for filtering calls.*
	 *
	 * @see PxSimulationFilterShader
	 * @see PhysXScene.setFilterShaderData
	 * @see PhysXScene.getFilterShaderData
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val filterShaderData: MemorySegment = MemorySegment.NULL,
	/**
	 * The custom filter shader to use for collision filtering.
	 *
	 * *This parameter is compulsory. If you don't want to define your own filter shader you can
	 * use the default shader [PxDefaultSimulationFilterShader] which can be found in the PhysX extensions
	 * library.*
	 *
	 * @see PxSimulationFilterShader
	 * @see PhysXScene.getFilterShader
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val filterShader: PxSimulationFilterShader,
	/**
	 * A custom collision filter callback which can be used to implement more complex filtering operations which need
	 * access to the simulation state, for example.
	 *
	 * @see PhysXSimulationFilterCallback
	 * @see PhysXScene.getFilterCallback
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val filterCallback: PhysXSimulationFilterCallback? = null,
	/**
	 * Filtering mode for kinematic-kinematic pairs in the broadphase.
	 *
	 * @see PxPairFilteringMode
	 * @see PhysXScene.getKinematicKinematicFilteringMode
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val kineKineFilteringMode: PxPairFilteringMode = PxPairFilteringMode.eDEFAULT,
	/**
	 * Filtering mode for static-kinematic pairs in the broadphase.
	 *
	 * @see PxPairFilteringMode
	 * @see PhysXScene.getStaticKinematicFilteringMode
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val staticKineFilteringMode: PxPairFilteringMode = PxPairFilteringMode.eDEFAULT,
	/**
	 * Selects the broad-phase algorithm to use.
	 *
	 * @see PxBroadPhaseType
	 * @see PhysXScene.getBroadPhaseType
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val broadPhaseType: PxBroadPhaseType = PxBroadPhaseType.ePABP,
	/**
	 * Broad-phase callback
	 *
	 * @see PhysXBroadPhaseCallback
	 * @see PhysXScene.getBroadPhaseCallback
	 * @see PhysXScene.setBroadPhaseCallback
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val broadPhaseCallback: PhysXBroadPhaseCallback? = null,
	/**
	 * Optional GPU broad-phase descriptor.
	 *
	 * This is only used for the GPU broadphase ([PxBroadPhaseType.eGPU]).
	 *
	 * @see PxBroadPhaseType
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val gpuBroadPhaseDesc: PhysXGpuBroadPhaseDesc? = null,
	/**
	 * Expected scene limits.
	 *
	 * @see PxSceneLimits
	 * @see PhysXScene.getLimits
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val limits: PxSceneLimits,
	/**
	 * Selects the solver algorithm to use.
	 *
	 * @see PxSolverType
	 * @see PhysXScene.getSolverType
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val solverType: PxSolverType = PxSolverType.ePGS,
	/**
	 * A contact with a relative velocity below this will not bounce. A typical value for simulation.
	 * stability is about `0.2 * gravity`.
	 *
	 * **Range:** (0, PX_MAX_F32)
	 *
	 * @see PxMaterial
	 * @see PhysXScene.setBounceThresholdVelocity
	 * @see PhysXScene.getBounceThresholdVelocity
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val bounceThresholdVelocity: PxReal_t, // =  0.2 * PxTolerancesScale::speed
	/**
	 * A threshold of contact separation distance used to decide if a contact point will experience friction forces.
	 *
	 * *If the separation distance of a contact point is greater than the threshold then the contact point will not experience friction forces.*
	 *
	 * *If the aggregated contact offset of a pair of shapes is large it might be desirable to neglect friction
	 * for contact points whose separation distance is sufficiently large that the shape surfaces are clearly separated.*
	 *
	 * *This parameter can be used to tune the separation distance of contact points at which friction starts to have an effect.*
	 *
	 * **Range:** [0, PX_MAX_F32)
	 *
	 * @see PhysXScene.setFrictionOffsetThreshold
	 * @see PhysXScene.getFrictionOffsetThreshold
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val frictionOffsetThreshold: PxReal_t, // 0.04 * PxTolerancesScale::length
	/**
	 * Friction correlation distance used to decide whether contacts are close enough to be merged into a single friction anchor point or not.
	 *
	 * *If the correlation distance is larger than the distance between contact points generated between a pair of shapes, some of the contacts may not experience frictional forces.*
	 *
	 * *This parameter can be used to tune the correlation distance used in the solver. Contact points can be merged into a single friction anchor if the distance between the contacts is smaller than correlation distance.*
	 *
	 * **Range:** [0, PX_MAX_F32)
	 *
	 * @see PhysXScene.setFrictionCorrelationDistance
	 * @see PhysXScene.getFrictionCorrelationDistance
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val frictionCorrelationDistance: PxReal_t, // 0.025f * PxTolerancesScale::length
	/**
	 * Flags used to select scene options.
	 *
	 * @see PxSceneFlag
	 * @see PxSceneFlags
	 * @see PhysXScene.getFlags
	 * @see PhysXScene.setFlag
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val flags: FlagSet<PxSceneFlag> = FlagSet.of(PxSceneFlag.eENABLE_PCM),
	/**
	 * The CPU task dispatcher for the scene.
	 *
	 * @see PhysXCpuDispatcher
	 * @see PhysXScene.getCpuDispatcher
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val cpuDispatcher: PhysXCpuDispatcher,
	/**
	 * The CUDA context manager for the scene.
	 *
	 * **Platform specific:** Applies to PC GPU only.
	 *
	 * @see PhysXCudaContextManager
	 * @see PhysXScene.getCudaContextManager
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val cudaContextManager: PhysXCudaContextManager,
	/**
	 * Will be copied to [PhysXScene.userData].
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val userData: MemorySegment = MemorySegment.NULL,
	/**
	 * Defines the number of actors required to spawn a separate rigid body solver island task chain.
	 *
	 * This parameter defines the minimum number of actors required to spawn a separate rigid body solver task chain. Setting a low value
	 * will potentially cause more task chains to be generated. This may result in the overhead of spawning tasks can become a limiting performance factor.
	 * Setting a high value will potentially cause fewer islands to be generated. This may reduce thread scaling (fewer task chains spawned) and may
	 * detrimentally affect performance if some bodies in the scene have large solver iteration counts because all constraints in a given island are solved by the
	 * maximum number of solver iterations requested by any body in the island.
	 *
	 * Note that a rigid body solver task chain is spawned as soon as either a sufficient number of rigid bodies or articulations are batched together.
	 *
	 * @see PhysXScene.setSolverBatchSize
	 * @see PhysXScene.getSolverBatchSize
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val solverBatchSize: PxU32_t = 128u,
	/**
	 * Defines the number of articulations required to spawn a separate rigid body solver island task chain.
	 *
	 * This parameter defines the minimum number of articulations required to spawn a separate rigid body solver task chain. Setting a low value
	 * will potentially cause more task chains to be generated. This may result in the overhead of spawning tasks can become a limiting performance factor.
	 * Setting a high value will potentially cause fewer islands to be generated. This may reduce thread scaling (fewer task chains spawned) and may
	 * detrimentally affect performance if some bodies in the scene have large solver iteration counts because all constraints in a given island are solved by the
	 * maximum number of solver iterations requested by any body in the island.
	 *
	 * Note that a rigid body solver task chain is spawned as soon as either a sufficient number of rigid bodies or articulations are batched together.
	 *
	 * @see PhysXScene.setSolverArticulationBatchSize
	 * @see PhysXScene.getSolverArticulationBatchSize
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val solverArticulationBatchSize: PxU32_t = 16u,
	/**
	 * Setting to define the number of 16K blocks that will be initially reserved to store contact, friction, and contact cache data.
	 * This is the number of 16K memory blocks that will be automatically allocated from the user allocator when the scene is instantiated. Further 16k
	 * memory blocks may be allocated during the simulation up to maxNbContactDataBlocks.
	 *
	 * *This value cannot be larger than maxNbContactDataBlocks because that defines the maximum number of 16k blocks that can be allocated by the SDK.*
	 *
	 * **Range:** [0, PX_MAX_U32]
	 *
	 * @see PxPhysics.createScene
	 * @see PhysXScene.setNbContactDataBlocks
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val nbContactDataBlocks: PxU32_t = 0u,
	/**
	 * Setting to define the maximum number of 16K blocks that can be allocated to store contact, friction, and contact cache data.
	 * As the complexity of a scene increases, the SDK may require to allocate new 16k blocks in addition to the blocks it has already
	 * allocated. This variable controls the maximum number of blocks that the SDK can allocate.
	 *
	 * In the case that the scene is sufficiently complex that all the permitted 16K blocks are used, contacts will be dropped and
	 * a warning passed to the error stream.
	 *
	 * If a warning is reported to the error stream to indicate the number of 16K blocks is insufficient for the scene complexity
	 * then the choices are either (i) re-tune the number of 16K data blocks until a number is found that is sufficient for the scene complexity,
	 * (ii) to simplify the scene or (iii) to opt to not increase the memory requirements of physx and accept some dropped contacts.
	 *
	 *
	 * **Range:** [0, PX_MAX_U32]
	 *
	 * @see nbContactDataBlocks
	 * @see PhysXScene.setNbContactDataBlocks
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val maxNbContactDataBlocks: PxU32_t = 65536u,
	/**
	 * The maximum bias coefficient used in the constraint solver
	 *
	 * When geometric errors are found in the constraint solver, either as a result of shapes penetrating
	 * or joints becoming separated or violating limits, a bias is introduced in the solver position iterations
	 * to correct these errors. This bias is proportional to 1/dt, meaning that the bias becomes increasingly
	 * strong as the time-step passed to [PhysXScene.simulate] becomes smaller. This coefficient allows the
	 * application to restrict how large the bias coefficient is, to reduce how violent error corrections are.
	 * This can improve simulation quality in cases where either variable time-steps or extremely small time-steps
	 * are used.
	 *
	 * **Range:** [0, PX_MAX_F32]
	 *
	 * @see PhysXScene.setMaxBiasCoefficient
	 * @see PhysXScene.getMaxBiasCoefficient
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val maxBiasCoefficient: PxReal_t = PX_MAX_F32,
	/**
	 * Size of the contact report stream (in bytes).
	 *
	 * The contact report stream buffer is used during the simulation to store all the contact reports.
	 * If the size is not sufficient, the buffer will grow by a factor of two.
	 * It is possible to disable the buffer growth by setting the flag [PxSceneFlag.eDISABLE_CONTACT_REPORT_BUFFER_RESIZE].
	 * In that case the buffer will not grow but contact reports not stored in the buffer will not get sent in the contact report callbacks.
	 *
	 * **Range:** (0, PX_MAX_U32]
	 *
	 * @see PhysXScene.getContactReportStreamBufferSize
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val contactReportStreamBufferSize: PxU32_t = 8192u,
	/**
	 * Maximum number of CCD passes
	 *
	 * The CCD performs multiple passes, where each pass every object advances to its time of first impact. This value defines how many passes the CCD system should perform.
	 *
	 * *The CCD system is a multi-pass best-effort conservative advancement approach. After the defined number of passes has been completed, any remaining time is dropped.*
	 * *This defines the maximum number of passes the CCD can perform. It may perform fewer if additional passes are not necessary.*
	 *
	 * **Range:** [1, PX_MAX_U32]
	 *
	 * @see PhysXScene.setCCDMaxPasses
	 * @see PhysXScene.getCCDMaxPasses
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val ccdMaxPasses: PxU32_t = 1u,
	/**
	 * CCD threshold
	 *
	 * CCD performs sweeps against shapes if and only if the relative motion of the shapes is fast-enough that a collision would be missed
	 * by the discrete contact generation. However, in some circumstances, e.g. when the environment is constructed from large convex shapes, this
	 * approach may produce undesired simulation artefacts. This parameter defines the minimum relative motion that would be required to force CCD between shapes.
	 * The smaller of this value and the sum of the thresholds calculated for the shapes involved will be used.
	 *
	 * *It is not advisable to set this to a very small value as this may lead to CCD "jamming" and detrimentally effect performance. This value should be at least larger than the translation caused by a single frame's gravitational effect*
	 *
	 * **Range:** [Eps, PX_MAX_F32]
	 *
	 * @see PhysXScene.setCCDThreshold
	 * @see PhysXScene.getCCDThreshold
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val ccdThreshold: PxReal_t = PX_MAX_F32,
	/**
	 * A threshold for speculative CCD. Used to control whether bias, restitution or a combination of the two are used to resolve the contacts.
	 *
	 * *This only has any effect on contacting pairs where one of the bodies has [PxRigidBodyFlag.eENABLE_SPECULATIVE_CCD] raised.*
	 *
	 * **Range:** [0, PX_MAX_F32)
	 *
	 * @see PhysXScene.setCCDMaxSeparation
	 * @see PhysXScene.getCCDMaxSeparation
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val ccdMaxSeparation: PxReal_t, // 0.04 * PxTolerancesScale::length
	/**
	 * The wake counter reset value
	 *
	 * Calling wakeUp() on objects which support sleeping will set their wake counter value to the specified reset value.
	 *
	 * **Range:** (0, PX_MAX_F32)
	 *
	 * @see PxRigidDynamic.wakeUp
	 * @see PxArticulationReducedCoordinate.wakeUp
	 * @see PhysXScene.getWakeCounterResetValue
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val wakeCounterResetValue: PxReal_t, // 0.4 (which corresponds to 20 frames for a time step of 0.02)
	/**
	 * The bounds used to sanity check user-set positions of actors and articulation links
	 *
	 * These bounds are used to check the position values of rigid actors inserted into the scene, and positions set for rigid actors
	 * already within the scene.
	 *
	 * **Range:** any valid PxBounds3
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val sanityBounds: PhysXBounds3, // (-PX_MAX_BOUNDS_EXTENTS, PX_MAX_BOUNDS_EXTENTS) on each axis
	/**
	 * The pre-allocations performed in the GPU dynamics pipeline.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val gpuDynamicsConfig: PhysXGpuDynamicsMemoryConfig,
	/**
	 * Limitation for the partitions in the GPU dynamics pipeline.
	 * This variable must be power of 2.
	 * A value greater than 32 is currently not supported.
	 *
	 * **Range:** (1, 32)
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val gpuMaxNumPartitions: PxU32_t,
	/**
	 * Limitation for the number of static rigid body partitions in the GPU dynamics pipeline.
	 *
	 * **Range:** (1, 255)
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val gpuMaxNumStaticPartitions: PxU32_t = 16u,
	/**
	 * Defines which compute version the GPU dynamics should target. DO NOT MODIFY
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val gpuComputeVersion: PxU32_t,
	/**
	 * Defines the size of a contact pool slab.
	 * Contact pairs and associated data are allocated using a pool allocator. Increasing the slab size can trade
	 * off some performance spikes when a large number of new contacts are found for an increase in overall memory
	 * usage.
	 *
	 * **Range:**(1, PX_MAX_U32)
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val contactPairSlabSize: PxU32_t = 256u,
	/**
	 * The scene query sub-system for the scene.
	 * 
	 * If left to NULL, PxScene will use its usual internal sub-system. If non-NULL, all SQ-related calls
	 * will be re-routed to the user-provided implementation. An external SQ implementation is available
	 * in the Extensions library (see [PxCreateExternalSceneQuerySystem]). This can also be fully re-implemented by users if needed.
	 * 
	 * @see PxSceneQuerySystem
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val sceneQuerySystem: PxSceneQuerySystem? = null
)