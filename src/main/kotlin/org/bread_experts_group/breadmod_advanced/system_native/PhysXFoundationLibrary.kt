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

import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.ptr
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.`void*`
import org.bread_experts_group.ffi.getDowncall
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.SymbolLookup
import java.lang.invoke.MethodHandle

class PhysXFoundationLibrary private constructor(
	private val pxCreateFoundation: MethodHandle
) {
	constructor(
		lookup: SymbolLookup,
		linker: Linker,
	) : this(
		lookup.getDowncall(
			linker, "PxCreateFoundation", `void*`,
			PxU32.withName("version"),
			PxAllocatorCallback.ptr.withName("allocator"),
			PxErrorCallback.ptr.withName("errorCallback")
		)!!
	)

	/**
	 * Creates an instance of the foundation class

	 * The foundation class is needed to initialize higher level SDKs. There may be only one instance per process.
	 * Calling this method after an instance has been created already will result in an error message and NULL will be
	   returned.

	 * @param version Version number we are expecting (should be #PX_PHYSICS_VERSION)
	 * @param allocator User supplied interface for allocating memory(see #PxAllocatorCallback)
	 * @param errorCallback User supplied interface for reporting errors and displaying messages(see #PxErrorCallback)
	 * @return Foundation instance on success, null if operation failed

	 * @see PhysXFoundation
	 * @since In accordance with PhysX 5.6.1
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 */
	fun pxCreateFoundation(
		linker: Linker, arena: Arena,
		version: PxU32_t, allocator: PhysXAllocatorCallback, errorCallback: PhysXErrorCallback
	): PhysXFoundation? {
		val segment = pxCreateFoundation.invokeExact(
			version.toInt(),
			allocator.allocateStructure(arena),
			errorCallback.allocateStructure(arena)
		) as MemorySegment
		if (segment == MemorySegment.NULL) return null
		return PhysXFoundation(linker, segment)
	}
}