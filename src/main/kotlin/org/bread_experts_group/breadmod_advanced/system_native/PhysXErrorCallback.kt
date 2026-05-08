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

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.Logger
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.int
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.`void*`
import org.bread_experts_group.ffi.nativeLinker
import org.bread_experts_group.generic.Flaggable.Companion.from
import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.MemorySegment
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.util.*

/**
 * User defined interface class.
 * Used by the library to emit debug information.
 *
 * *The SDK state should not be modified from within any error reporting functions.*
 *
 * **Threading:** The SDK sequences its calls to the output stream using a mutex, so the class need not
be implemented in a thread-safe manner if the SDK is the only client.
 * @since In accordance with PhysX 5.6.1
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 */
interface PhysXErrorCallback {
	class Standard(private val logger: Logger) : PhysXErrorCallback {
		override fun reportError(code: EnumSet<PxErrorCode>, message: MemorySegment, file: MemorySegment, line: Int) {
			if (logger.level < Level.ERROR) return
			val fileName = file.reinterpret(Long.MAX_VALUE).getString(0, Charsets.UTF_8)
			val message = message.reinterpret(Long.MAX_VALUE).getString(0, Charsets.UTF_8)
			logger.error("[${code.joinToString(", ")}] @ $fileName:$line: \"$message\"")
		}
	}

	/**
	 * Internal upcall to [destructor] for use by C++
	 * @author Miko Elbrecht
	 */
	@Suppress("Unused")
	private fun destructorUPCALL(self: MemorySegment) = this.destructor()

	/**
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 */
	fun destructor() {
	}

	/**
	 * Internal upcall to [reportError] for use by C++
	 * @author Miko Elbrecht
	 */
	@Suppress("Unused")
	private fun reportErrorUPCALL(
		self: MemorySegment,
		code: Int,
		message: MemorySegment,
		file: MemorySegment,
		line: Int
	) = this.reportError(
		PxErrorCode.entries.from(code),
		message, file, line
	)

	/**
	 * Reports an error code.
	 * @param code Error code, see [PxErrorCode]
	 * @param message Message to display.
	 * @param file File error occurred in.
	 * @param line Line number error occurred on.
	 * @since In accordance with PhysX 5.6.1
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	*/
	fun reportError(
		code: EnumSet<PxErrorCode>,
		message: MemorySegment,
		file: MemorySegment,
		line: Int
	)

	/**
	 * Allocates this structure into an [Arena] for native use.
	 * @author Miko Elbrecht
	 */
	fun allocateStructure(arena: Arena): MemorySegment {
		val structure = arena.allocate(`void*`)
		val vtable = arena.allocate(`void*`, 2)
		structure.setAtIndex(`void*`, 0, vtable)
		val destructor = nativeLinker.upcallStub(
			MethodHandles.lookup().findVirtual(
				PhysXErrorCallback::class.java,
				"destructorUPCALL",
				MethodType.methodType(Void.TYPE, MemorySegment::class.java)
			).bindTo(this),
			FunctionDescriptor.ofVoid(`void*`),
			arena
		)
		vtable.setAtIndex(`void*`, 0, destructor)
		val reportError = nativeLinker.upcallStub(
			MethodHandles.lookup().findVirtual(
				PhysXErrorCallback::class.java,
				"reportErrorUPCALL",
				MethodType.methodType(
					Void.TYPE,
					MemorySegment::class.java, Int::class.java,
					MemorySegment::class.java, MemorySegment::class.java, Int::class.java
				)
			).bindTo(this),
			FunctionDescriptor.ofVoid(`void*`, int, `void*`, `void*`, int),
			arena
		)
		vtable.setAtIndex(`void*`, 1, reportError)
		return structure
	}
}