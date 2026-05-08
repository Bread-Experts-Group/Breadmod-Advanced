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
import org.bread_experts_group.ffi.getDowncall
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.SymbolLookup
import java.lang.invoke.MethodHandle

class PhysXLibrary private constructor(
	private val pxCreatePhysics: MethodHandle,
	private val pxCreatePvd: MethodHandle,
	private val pxDefaultPvdSocketTransportCreate: MethodHandle,
	private val pxDefaultPvdFileTransportCreate: MethodHandle,
	private val linker: Linker
) {
	constructor(
		lookup: SymbolLookup,
		linker: Linker
	) : this(
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
			linker, "PxCreatePvd", PxPvd.ptr,
			PxFoundation.ptr.withName("foundation")
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
		linker
	)

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
	): PhysXPhysics? {
		val tolerance = arena.allocate(PxTolerancesScale)
		`PxTolerancesScale defaultLength`.set(tolerance, 0, scale.defaultLength)
		`PxTolerancesScale defaultSpeed`.set(tolerance, 0, scale.defaultSpeed)
		val physics = pxCreatePhysics.invokeExact(
			version.toInt(),
			foundation.segment,
			tolerance,
			trackOutstandingAllocations,
			pvd?.segment ?: MemorySegment.NULL,
			omniPvd?.segment ?: MemorySegment.NULL
		) as MemorySegment
		if (physics == MemorySegment.NULL) return null
		return PhysXPhysics(linker, physics)
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
}