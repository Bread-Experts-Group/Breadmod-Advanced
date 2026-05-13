package org.bread_experts_group.breadmod_advanced.system_native

import java.lang.foreign.MemorySegment

@Suppress("FunctionName")
abstract class PhysXScene : PhysXSceneSQSystem() {
	@VirtualFunction(0) abstract fun fTODO1() // release
	@VirtualFunction(1) abstract fun fTODO2() // setFlag
	@VirtualFunction(2) abstract fun fTODO3() // getFlags
	@VirtualFunction(3) abstract fun fTODO4() // setLimits
	@VirtualFunction(4) abstract fun fTODO5() // getLimits
	@VirtualFunction(5) abstract fun fTODO6() // getPhysics
	@VirtualFunction(6) abstract fun fTODO7() // getTimestamp
	@VirtualFunction(7) abstract fun fTODO8() // setName
	@VirtualFunction(8) abstract fun fTODO9() // getName
	@VirtualFunction(9) abstract fun fTODO10() // addArticulation
	@VirtualFunction(10) abstract fun fTODO11() // removeArticulation
	@VirtualFunction(11) abstract fun fTODO12() // addActor
	@VirtualFunction(12) abstract fun fTODO13() // addActors
	@VirtualFunction(13) abstract fun fTODO14() // addActors
	@VirtualFunction(14) abstract fun fTODO15() // removeActor
	@VirtualFunction(15) abstract fun fTODO16() // removeActors
	@VirtualFunction(16) abstract fun fTODO17() // addAggregate
	@VirtualFunction(17) abstract fun fTODO18() // removeAggregate
	@VirtualFunction(18) abstract fun fTODO19() // addCollection
	@VirtualFunction(19) abstract fun fTODO20() // getNbActors
	@VirtualFunction(20) abstract fun fTODO21() // getActors
	@VirtualFunction(21) abstract fun fTODO22() // getActiveActors
	@VirtualFunction(22) abstract fun fTODO23() // getNbDeformableSurfaces
	@VirtualFunction(23) abstract fun fTODO24() // getDeformableSurfaces
	@VirtualFunction(24) abstract fun fTODO25() // getNbDeformableVolumes
	@VirtualFunction(25) abstract fun fTODO26() // getDeformableVolumes
	@VirtualFunction(26) abstract fun fTODO27() // getNbParticleSystems
	@VirtualFunction(27) abstract fun fTODO28() // getParticleSystems
	@VirtualFunction(28) abstract fun fTODO29() // getNbPBDParticleSystems
	@VirtualFunction(29) abstract fun fTODO30() // getPBDParticleSystems
	@VirtualFunction(30) abstract fun fTODO31() // getNbArticulations
	@VirtualFunction(31) abstract fun fTODO32() // getArticulations
	@VirtualFunction(32) abstract fun fTODO33() // getNbConstraints
	@VirtualFunction(33) abstract fun fTODO34() // getConstraints
	@VirtualFunction(34) abstract fun fTODO35() // getNbAggregates
	@VirtualFunction(35) abstract fun fTODO36() // getAggregates
	@VirtualFunction(36) abstract fun fTODO37() // setDominanceGroupPair
	@VirtualFunction(37) abstract fun fTODO38() // getDominanceGroupPair
	@VirtualFunction(38) abstract fun fTODO39() // getCpuDispatcher
	@VirtualFunction(39) abstract fun fTODO40() // getCudaContextManager
	@VirtualFunction(40) abstract fun fTODO41() // createClient
	@VirtualFunction(41) abstract fun fTODO42() // setSimulationEventCallback
	@VirtualFunction(42) abstract fun fTODO43() // getSimulationEventCallback
	@VirtualFunction(43) abstract fun fTODO44() // setContactModifyCallback
	@VirtualFunction(44) abstract fun fTODO45() // setCCDContactModifyCallback
	@VirtualFunction(45) abstract fun fTODO46() // getContactModifyCallback
	@VirtualFunction(46) abstract fun fTODO47() // getCCDContactModifyCallback
	@VirtualFunction(47) abstract fun fTODO48() // setBroadPhaseCallback
	@VirtualFunction(48) abstract fun fTODO49() // getBroadPhaseCallback
	@VirtualFunction(49) abstract fun fTODO50() // setFilterShaderData
	@VirtualFunction(50) abstract fun fTODO51() // getFilterShaderData
	@VirtualFunction(51) abstract fun fTODO52() // getFilterShaderDataSize
	@VirtualFunction(52) abstract fun fTODO53() // getFilterShader
	@VirtualFunction(53) abstract fun fTODO54() // getFilterCallback
	@VirtualFunction(54) abstract fun fTODO55() // resetFiltering
	@VirtualFunction(55) abstract fun fTODO56() // resetFiltering
	@VirtualFunction(56) abstract fun fTODO57() // getKinematicKinematicFilteringMode
	@VirtualFunction(57) abstract fun fTODO58() // getStaticKinematicFilteringMode

	/**
	 * Advances the simulation by an [elapsedTime] time.
	 *
	 * Large [elapsedTime] values can lead to instabilities. In such cases [elapsedTime]
	 * should be subdivided into smaller time intervals and [simulate] should be called
	 * multiple times for each interval.
	 *
	 * Calls to [simulate] should pair with calls to [fetchResults]:
	 * 	Each [fetchResults] invocation corresponds to exactly one [simulate]
	 * 	invocation; calling [simulate] twice without an intervening [fetchResults]
	 * 	or [fetchResults] twice without an intervening [simulate] causes an error
	 * 	condition.
	 *
	 *  ```
	 * 	scene.simulate()
	 * 	// ...do some processing until physics is computed...
	 * 	scene.fetchResults()
	 * 	// ...now results of run may be retrieved.
	 * ```
	 *
	 * @param elapsedTime Amount of time to advance simulation by. The parameter has to be larger than 0, else the resulting behavior will be undefined. <b>Range:</b> (0, PX_MAX_F32)
	 * @param completionTask if non-NULL, this task will have its refcount incremented in [simulate], then
	 * decremented when the scene is ready to have [fetchResults] called. So the task will not run until the
	 * application also calls [removeReference].
	 * @param scratchMemBlock a memory region for physx to use for temporary data during simulation. This block may be reused by the application
	 * after [fetchResults] returns. Must be aligned on a 16-byte boundary
	 * @param scratchMemBlockSize the size of the scratch memory block. Must be a multiple of 16K.
	 * @param controlSimulation if true, the scene controls its PxTaskManager simulation state. Leave
	 * true unless the application is calling the [PxTaskManager] [startSimulation]/[stopSimulation] methods itself.
	 * @return True if success
	 *
	 * @see fetchResults
	 * @see checkResults
	 */
	@VirtualFunction(58) abstract fun simulate(
		elapsedTime: PxReal_t,
		completionTask: MemorySegment = MemorySegment.NULL, // TODO: PxBaseTask
		scratchMemBlock: MemorySegment = MemorySegment.NULL,
		scratchMemBlockSize: PxU32_t = 0u,
		controlSimulation: Boolean = true
	): Boolean

	@VirtualFunction(59) abstract fun fTODO60() // advance
	@VirtualFunction(60) abstract fun fTODO61() // collide
	@VirtualFunction(61) abstract fun fTODO62() // checkResults
	@VirtualFunction(62) abstract fun fTODO63() // fetchCollision
	/**
	 * This is the big brother to [checkResults] it basically does the following:
	 *
	 * ```
	 * if (checkResults(block)) {
	 * 	// fire appropriate callbacks
	 * 	// swap buffers
	 * 	return true
	 * } else return false
	 * ```
	 *
	 * @param block When set to true will block until results are available.
	 * @param errorState Used to retrieve hardware error codes. A non zero value indicates an error.
	 * @return True if the results have been fetched.
	 *
	 * @see simulate
	 * @see checkResults
	 */
	@VirtualFunction(63) abstract fun fetchResults(
		block: Boolean = false,
		errorState: MemorySegment = MemorySegment.NULL
	): Boolean

	@VirtualFunction(64) abstract fun fTODO65() // fetchResultsStart
	@VirtualFunction(65) abstract fun fTODO66() // processCallbacks
	@VirtualFunction(66) abstract fun fTODO67() // fetchResultsFinish
	@VirtualFunction(67) abstract fun fTODO68() // fetchResultsParticleSystem
	@VirtualFunction(68) abstract fun fTODO69() // flushSimulation
	@VirtualFunction(69) abstract fun fTODO70() // setGravity
	@VirtualFunction(70) abstract fun fTODO71() // getGravity
	@VirtualFunction(71) abstract fun fTODO72() // setBounceThresholdVelocity
	@VirtualFunction(72) abstract fun fTODO73() // getBounceThresholdVelocity
	@VirtualFunction(73) abstract fun fTODO74() // setCCDMaxPasses
	@VirtualFunction(74) abstract fun fTODO75() // getCCDMaxPasses
	@VirtualFunction(75) abstract fun fTODO76() // setCCDMaxSeparation
	@VirtualFunction(76) abstract fun fTODO77() // getCCDMaxSeparation
	@VirtualFunction(77) abstract fun fTODO78() // setCCDThreshold
	@VirtualFunction(78) abstract fun fTODO79() // getCCDThreshold
	@VirtualFunction(79) abstract fun fTODO80() // setMaxBiasCoefficient
	@VirtualFunction(80) abstract fun fTODO81() // getMaxBiasCoefficient
	@VirtualFunction(81) abstract fun fTODO82() // setFrictionOffsetThreshold
	@VirtualFunction(82) abstract fun fTODO83() // getFrictionOffsetThreshold
	@VirtualFunction(83) abstract fun fTODO84() // setFrictionCorrelationDistance
	@VirtualFunction(84) abstract fun fTODO85() // getFrictionCorrelationDistance
	@VirtualFunction(85) abstract fun fTODO86() // getFrictionType
	@VirtualFunction(86) abstract fun fTODO87() // getSolverType
	@VirtualFunction(87) abstract fun fTODO88() // setVisualizationParameter
	@VirtualFunction(88) abstract fun fTODO89() // getVisualizationParameter
	@VirtualFunction(89) abstract fun fTODO90() // setVisualizationCullingBox
	@VirtualFunction(90) abstract fun fTODO91() // getVisualizationCullingBox
	@VirtualFunction(91) abstract fun fTODO92() // getRenderBuffer
	@VirtualFunction(92) abstract fun fTODO93() // getSimulationStatistics
	@VirtualFunction(93) abstract fun fTODO94() // getBroadPhaseType
	@VirtualFunction(94) abstract fun fTODO95() // getBroadPhaseCaps
	@VirtualFunction(95) abstract fun fTODO96() // getNbBroadPhaseRegions
	@VirtualFunction(96) abstract fun fTODO97() // getBroadPhaseRegions
	@VirtualFunction(97) abstract fun fTODO98() // addBroadPhaseRegion
	@VirtualFunction(98) abstract fun fTODO99() // removeBroadPhaseRegion
	@VirtualFunction(99) abstract fun fTODO100() // getTaskManager
	@VirtualFunction(100) abstract fun fTODO101() // lockRead
	@VirtualFunction(101) abstract fun fTODO102() // unlockRead
	@VirtualFunction(102) abstract fun fTODO103() // lockWrite
	@VirtualFunction(103) abstract fun fTODO104() // unlockWrite
	@VirtualFunction(104) abstract fun fTODO105() // setNbContactDataBlocks
	@VirtualFunction(105) abstract fun fTODO106() // getNbContactDataBlocksUsed
	@VirtualFunction(106) abstract fun fTODO107() // getMaxNbContactDataBlocksUsed
	@VirtualFunction(107) abstract fun fTODO108() // getContactReportStreamBufferSize
	@VirtualFunction(108) abstract fun fTODO109() // setSolverBatchSize
	@VirtualFunction(109) abstract fun fTODO110() // getSolverBatchSize
	@VirtualFunction(110) abstract fun fTODO111() // setSolverArticulationBatchSize
	@VirtualFunction(111) abstract fun fTODO112() // getSolverArticulationBatchSize
	@VirtualFunction(112) abstract fun fTODO113() // getWakeCounterResetValue
	@VirtualFunction(113) abstract fun fTODO114() // shiftOrigin
	@VirtualFunction(114) abstract fun fTODO115() // getScenePvdClient
	@VirtualFunction(115) abstract fun fTODO116() // getGpuDynamicsConfig
	@VirtualFunction(116) abstract fun fTODO117() // getDirectGPUAPI
	@VirtualFunction(117) abstract fun fTODO118() // getSolverResidual
	@VirtualFunction(118) abstract fun fTODO119() // setDeformableSurfaceGpuPostSolveCallback
	@VirtualFunction(119) abstract fun fTODO120() // setDeformableVolumeGpuPostSolveCallback
	@VirtualFunction(120) abstract fun fTODO121() // copySoftBodyData
	@VirtualFunction(121) abstract fun fTODO122() // applySoftBodyData
	@VirtualFunction(122) abstract fun fTODO123() // applyParticleBufferData
}