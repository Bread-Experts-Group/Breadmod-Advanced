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
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.ptr
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.uint8_t
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.`void*`
import org.bread_experts_group.ffi.getDowncall
import org.bread_experts_group.generic.FlagSet
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.invoke.MethodHandle

/**
 * [PhysXPvd] is the top-level class for the PVD framework, and the main customer interface for PVD
   configuration.It is a singleton class, instantiated and owned by the application.
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
class PhysXPvd internal constructor(
	linker: Linker,
	internal val segment: MemorySegment
) {
	private val connect: MethodHandle

	init {
		val vtable = segment.reinterpret(`void*`.byteSize()).get(`void*`, 0)
			.reinterpret(`void*`.byteSize() * 13)
		connect = vtable.getAtIndex(`void*`, 6).getDowncall(
			linker, bool,
			PxPvd.ptr.withName("self"),
			PxPvdTransport.ptr.withName("transport"),
			uint8_t.ptr.withName("flags")
		)
	}

	fun connect(
		arena: Arena,
		transport: PhysXPvdTransport, flags: FlagSet<PxPvdInstrumentationFlag>
	): Boolean {
		val pxFlags = arena.allocate(uint8_t)
		pxFlags.set(uint8_t, 0, flags.maskB)
		return this.connect.invokeExact(segment, transport.segment, pxFlags) as Boolean
	}
}