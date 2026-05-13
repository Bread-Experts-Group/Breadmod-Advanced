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

import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.bool
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.char
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.int
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.ptr
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.`void*`
import org.bread_experts_group.ffi.getDowncall
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.SymbolLookup
import java.lang.invoke.MethodHandle

class PhysXLibrary private constructor(
	private val pxCreateCudaContextManager: MethodHandle,
	private val pxCreatePhysics: MethodHandle,
	private val pxCreatePlane: MethodHandle,
	private val pxCreatePvd: MethodHandle,
	private val pxDefaultCpuDispatcherCreate: MethodHandle,
	private val pxDefaultPvdSocketTransportCreate: MethodHandle,
	private val pxDefaultPvdFileTransportCreate: MethodHandle,
	private val pxDefaultSimulationFilterShader: MethodHandle,
	private val linker: Linker
) {
	constructor(
		lookup: SymbolLookup,
		linker: Linker
	) : this(
		lookup.getDowncall(
			linker, "PxCreateCudaContextManager", PxCudaContextManager.ptr,
			PxFoundation.ptr.withName("foundation"),
			PxCudaContextManagerDesc.ptr.withName("desc"),
			PxProfilerCallback.ptr.withName("profilerCallback"),
			bool.withName("launchSynchronous")
		)!!,
		lookup.getDowncall(
			linker, "PxCreatePhysics", PxPhysics.ptr,
			PxU32.withName("version"),
			PxFoundation.ptr.withName("foundation"),
			PxTolerancesScale.ptr.withName("scale"),
			bool.withName("trackOutstandingAllocations"),
			PxPvd.ptr.withName("pvd"),
			PxOmniPvd.ptr.withName("omniPvd"),
		)!!,
		lookup.getDowncall(
			linker, "PxCreatePlaneBM", PxRigidStatic.ptr,
			PxPhysics.ptr.withName("sdk"),
			PxPlane.ptr.withName("plane"),
			PxMaterial.ptr.withName("material")
		)!!,
		lookup.getDowncall(
			linker, "PxCreatePvd", PxPvd.ptr,
			PxFoundation.ptr.withName("foundation")
		)!!,
		lookup.getDowncall(
			linker, "PxDefaultCpuDispatcherCreateBM", PxDefaultCpuDispatcher.ptr,
			PxU32.withName("numThreads"),
			PxU32.ptr.withName("affinityMasks"),
			int.withName("mode"),
			PxU32.withName("yieldProcessorCount")
		)!!,
		lookup.getDowncall(
			linker, "PxDefaultPvdSocketTransportCreateBM", PxPvdTransport.ptr,
			char.ptr.withName("host"),
			int.withName("port"),
			int.withName("timeoutInMilliseconds")
		)!!,
		lookup.getDowncall(
			linker, "PxDefaultPvdFileTransportCreateBM", PxPvdTransport.ptr,
			char.ptr.withName("name")
		)!!,
		lookup.getDowncall(
			linker, "PxDefaultSimulationFilterShaderBM", PxU16,
			PxFilterObjectAttributes.withName("attributes0"),
			PxFilterData.ptr.withName("filterData0"),
			PxFilterObjectAttributes.withName("attributes1"),
			PxFilterData.ptr.withName("filterData1"),
			PxPairFlags.ptr.withName("pairFlags"),
			`void*`.withName("constantBlock"),
			PxU32.withName("constantBlockSize")
		)!!,
		linker
	)

	/**
	 * Allocate a CUDA Context manager, complete with heaps.
	 * You only need one CUDA context manager per GPU device you intend to use for
	 * CUDA tasks.
	 * @param foundation PhysXFoundation instance.
	 * @param desc Cuda context manager desc.
	 * @param profilerCallback PhysX profiler callback instance.
	 * @param launchSynchronous Set launchSynchronous to true for CUDA to report the actual point of failure.
	 *
	 * @see PxGetProfilerCallback

	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun pxCreateCudaContextManager(
		arena: Arena,
		foundation: PhysXFoundation,
		desc: PhysXCudaContextManagerDesc,
		profilerCallback: PhysXProfilerCallback? = null,
		launchSynchronous: Boolean = false
	): PhysXCudaContextManager {
		val ptr = pxCreateCudaContextManager.invokeExact(
			foundation.segment,
			cppAnalyze(desc).allocate(arena, linker),
			if (profilerCallback != null) TODO("!") else MemorySegment.NULL,
			launchSynchronous
		) as MemorySegment
		return PhysXCudaContextManager(ptr)
	}

	/**
	 * Creates an instance of the physics SDK.

	 * Creates an instance of [PhysXPhysics]. May not be a class member to avoid name mangling.
	 * Pass the constant [PxPhysicsVersion.PX_PHYSICS_VERSION] as the argument.
	 * There may be only one instance of this class per process. Calling this method after an instance
	   has been created already will result in an error message and null will be returned.

	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1

	 * @param version Version number we are expecting (should be [PxPhysicsVersion.PX_PHYSICS_VERSION])
	 * @param foundation Foundation instance (see [PhysXFoundation])
	 * @param scale values used to determine default tolerances for objects at creation time
	 * @param trackOutstandingAllocations true if you want to track memory allocations
	   so a debugger connection partway through your physics simulation will get
	   an accurate map of everything that has been allocated so far. This could have a memory
	   and performance impact on your simulation hence it defaults to off.
	 * @param pvd When pvd is a valid [PhysXPvd] instance (PhysX Visual Debugger), a connection to the specified [PhysXPvd] instance is created.
	   If pvd is null no connection will be attempted.
	 * @param omniPvd When omniPvd is a valid [PhysXOmniPvd] instance PhysX will sample its internal structures to the defined OmniPvd output streams
	   set in the [PhysXOmniPvd] object.
	 * @return [PhysXPhysics] instance on success, null if operation failed

	 * @see PhysXPhysics
	 */
	fun pxCreatePhysics(
		arena: Arena,
		version: PxU32_t,
		foundation: PhysXFoundation,
		scale: PhysXTolerancesScale,
		trackOutstandingAllocations: Boolean = false,
		pvd: PhysXPvd? = null,
		omniPvd: PhysXOmniPvd? = null
	): MemorySegment? {
		val physics = pxCreatePhysics.invokeExact(
			version.toInt(),
			foundation.segment,
			cppAnalyze(scale).allocate(arena, linker),
			trackOutstandingAllocations,
			pvd?.segment ?: MemorySegment.NULL,
			omniPvd?.segment ?: MemorySegment.NULL
		) as MemorySegment
		if (physics == MemorySegment.NULL) return null
		return physics
	}

	/**
	 * create a plane actor. The plane equation is n.x + d = 0
	 *
	 * @param sdk the PxPhysics object
	 * @param plane a plane of the form n.x + d = 0
	 * @param material the material for the new object's shape
	 *
	 * @return a new static actor, or NULL if it could not be constructed
	 *
	 * @see PhysXRigidStatic
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun pxCreatePlane(sdk: MemorySegment, plane: MemorySegment, material: MemorySegment): MemorySegment? {
		val planePtr = pxCreatePlane.invokeExact(
			sdk,
			plane,
			material
		) as MemorySegment
		if (planePtr == MemorySegment.NULL) return null
		return planePtr
	}

	/**
	 * Create a pvd instance.
	 * @param foundation is the foundation instance that stores the allocator and error callbacks.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun pxCreatePvd(foundation: PhysXFoundation): PhysXPvd = PhysXPvd(
		linker,
		pxCreatePvd.invokeExact(foundation.segment) as MemorySegment
	)

	/**
	 * Create default dispatcher, extensions SDK needs to be initialized first.
	 *
	 * @param numThreads Number of worker threads the dispatcher should use.
	 * @param affinityMasks Array with affinity mask for each thread. If not defined, default masks will be used.
	 * @param mode is the strategy employed when a busy-wait is encountered.
	 * @param yieldProcessorCount specifies the number of times a OS-specific yield processor command will be executed
	 * during each cycle of a busy-wait in the event that the specified mode is [PxDefaultCpuDispatcherWaitForWorkMode.eYIELD_PROCESSOR]
	 *
	 * *numThreads may be zero in which case no worker thread are initialized and
	 * simulation tasks will be executed on the thread that calls [PhysXScene.simulate]*
	 *
	 * *yieldProcessorCount must be greater than zero if [PxDefaultCpuDispatcherWaitForWorkMode.eYIELD_PROCESSOR] is the
	 * chosen mode and equal to zero for all other modes.*
	 *
	 * *[PxDefaultCpuDispatcherWaitForWorkMode.eYIELD_THREAD] and [PxDefaultCpuDispatcherWaitForWorkMode.eYIELD_PROCESSOR]
	 * modes will use compute resources even if the simulation is not running.
	 * It is left to users to keep threads inactive, if so desired, when no simulation is running.*
	 *
	 * @see PhysXDefaultCpuDispatcher
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun pxDefaultCpuDispatcherCreate(
		arena: Arena,
		numThreads: UInt,
		affinityMasks: Mutable<UInt>? = null,
		mode: PxDefaultCpuDispatcherWaitForWorkMode = PxDefaultCpuDispatcherWaitForWorkMode.eWAIT_FOR_WORK,
		yieldProcessorCount: UInt = 0u
	): PhysXCpuDispatcher {
		val aM = if (affinityMasks != null) arena.allocate(PxU32) else MemorySegment.NULL
		val cpu = pxDefaultCpuDispatcherCreate.invokeExact(
			numThreads.toInt(),
			aM,
			mode.ordinal,
			yieldProcessorCount.toInt()
		) as MemorySegment
		if (affinityMasks != null) affinityMasks.set(aM.get(PxU32, 0).toUInt())
		return PhysXCpuDispatcher(cpu)
	}

	/**
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun pxDefaultPvdSocketTransportCreate(
		arena: Arena,
		host: String, port: Int, timeoutInMilliseconds: UInt
	): PhysXPvdTransport = PhysXPvdTransport(
		pxDefaultPvdSocketTransportCreate.invokeExact(
			arena.allocateFrom(host, Charsets.UTF_8), port, timeoutInMilliseconds.toInt()
		) as MemorySegment
	)

	/**
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun pxDefaultPvdFileTransportCreate(
		arena: Arena,
		name: String?
	): PhysXPvdTransport = PhysXPvdTransport(
		pxDefaultPvdFileTransportCreate.invokeExact(
			if (name != null) arena.allocateFrom(name, Charsets.UTF_8)
			else MemorySegment.NULL
		) as MemorySegment
	)

	/**
	 * Implementation of a simple filter shader that emulates PhysX 2.8.x filtering
	 *
	 * This shader provides the following logic:
	 * - If one of the two filter objects is a trigger, the pair is acccepted and [PxPairFlag.eTRIGGER_DEFAULT] will be used for trigger reports
	 * - Else, if the filter mask logic (see further below) discards the pair it will be suppressed ([PxFilterFlag.eSUPPRESS])
	 * - Else, the pair gets accepted and collision response gets enabled ([PxPairFlag.eCONTACT_DEFAULT])
	 *
	 * Filter mask logic:
	 * Given the two [PxFilterData] structures fd0 and fd1 of two collision objects, the pair passes the filter if the following
	 * conditions are met:
	 *
	 * 	1) Collision groups of the pair are enabled
	 * 	2) Collision filtering equation is satisfied
	 *
	 * Each actor can belong to a single collision group. Use PxSetGroup to set the group of an actor and PxGetGroup to retrieve the group of an actor.
	 * A collision group is an integer value between 0 and 31 defining which group the actor belongs to. Because that value is written to an actor's
	 * shapes internally (it is stored in the shapes' PxFilterData), this feature does not work with shared shapes, unless they all belong to actors
	 * whose groups are similar. For example it would not work to share a shape between actors A and B, and then assign A to group 0 and B to group 1,
	 * as they would both internally try to write different group values to the same shape.
	 *
	 * Once actors are assigned to groups, it is possible to define how groups collide with each-other using the PxSetGroupCollisionFlag function.
	 * Use this function to set a simple boolean value per group pairs, defining if the corresponding groups should collide. If not, collisions between
	 * actors of these non-colliding groups will be automatically disabled by the PxDefaultSimulationFilterShader.
	 *
	 * @see PxSimulationFilterShader
	 * @see PxGetGroupCollisionFlag
	 * @see PxSetGroupCollisionFlag
	 * @see PxGetGroup
	 * @see PxSetGroup
	 */
	fun pxDefaultSimulationFilterShader(
		attributes0: Int,
		filterData0: MemorySegment,
		attributes1: Int,
		filterData1: MemorySegment,
		pairFlags: MemorySegment,
		constantBlock: MemorySegment,
		constantBlockSize: Int
	): Short {
		TODO("Ah")
	}
}