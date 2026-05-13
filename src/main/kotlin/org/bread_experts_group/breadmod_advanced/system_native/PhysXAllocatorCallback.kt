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

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment

/**
 * Abstract base class for an application defined memory allocator that can be used by the Nv library.
 *
 * *The SDK state should not be modified from within any allocation/free function.*
 *
 * **Threading:** All methods of this class should be thread safe as it can be called from the user thread
   or the physics processing thread(s).
 * @since In accordance with PhysX 5.6.1
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 */
abstract class PhysXAllocatorCallback {
	object Standard : PhysXAllocatorCallback() {
		private val managed: MutableMap<MemorySegment, Arena> = mutableMapOf()

		override fun allocate(
			self: MemorySegment,
			size: Long,
			typeName: MemorySegment,
			fileName: MemorySegment,
			line: Int
		): MemorySegment {
			val allocator = Arena.ofShared()
//			val size = when (size) {
//				is NativeSize.B64 -> size.value
//				is NativeSize.B32 -> size.value.toLong() and 0xFFFFFFFF
//			}
			val segment = allocator.allocate(size)
			this.managed[segment] = allocator
			return segment
		}

		override fun deallocate(
			self: MemorySegment,
			ptr: MemorySegment
		) {
			this.managed[ptr]?.close()
		}
	}

	/**
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 */
	@VirtualFunction(0)
	open fun destructor(self: MemorySegment) {
	}

	/**
	 * Allocates size bytes of memory, which must be 16-byte aligned.
	 *
	 * This method should never return [MemorySegment.NULL]. If you run out of memory, then
	   you should terminate the app or take some other appropriate action.
	 *
	 * **Threading:** This function should be thread safe as it can be called in the context of the user thread
	   and physics processing thread(s).
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 *
	 * @param size			Number of bytes to allocate.
	 * @param typeName		Name of the datatype that is being allocated
	 * @param fileName		The source file which allocated the memory
	 * @param line			The source line which allocated the memory
	 * @return				The allocated block of memory.
	 */
	@VirtualFunction(1)
	abstract fun allocate(
		self: MemorySegment,
		size: Long,
		typeName: MemorySegment,
		fileName: MemorySegment,
		line: Int
	): MemorySegment

	/**
	 * Frees memory previously allocated by allocate().
	 *
	 * **Threading:** This function should be thread safe as it can be called in the context of the user thread
	   and physics processing thread(s).
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 *
	 * @param ptr Memory to free.
	 */
	@VirtualFunction(2)
	abstract fun deallocate(
		self: MemorySegment,
		ptr: MemorySegment
	)

//	override fun allocateStructure(arena: Arena): MemorySegment {
//		val structure = arena.allocate(`void*`)
//		val vtable = arena.allocate(`void*`, 3)
//		structure.setAtIndex(`void*`, 0, vtable)
//		val destructor = nativeLinker.upcallStub(
//			MethodHandles.lookup().findVirtual(
//				PhysXAllocatorCallback::class.java,
//				"destructorUPCALL",
//				MethodType.methodType(Void.TYPE, MemorySegment::class.java)
//			).bindTo(this),
//			FunctionDescriptor.ofVoid(`void*`),
//			arena
//		)
//		vtable.setAtIndex(`void*`, 0, destructor)
//		val allocate = nativeLinker.upcallStub( // TODO: detect bitness
//			MethodHandles.lookup().findVirtual(
//				PhysXAllocatorCallback::class.java,
//				"allocateUPCALL64",
//				MethodType.methodType(
//					MemorySegment::class.java,
//					MemorySegment::class.java, Long::class.java, MemorySegment::class.java,
//					MemorySegment::class.java, Int::class.java
//				)
//			).bindTo(this),
//			FunctionDescriptor.of(
//				`void*`,
//				`void*`, ValueLayout.JAVA_LONG, `void*`,
//				`void*`, int
//			),
//			arena
//		)
//		vtable.setAtIndex(`void*`, 1, allocate)
//		val deallocate = nativeLinker.upcallStub(
//			MethodHandles.lookup().findVirtual(
//				PhysXAllocatorCallback::class.java,
//				"deallocateUPCALL",
//				MethodType.methodType(
//					Void.TYPE,
//					MemorySegment::class.java, MemorySegment::class.java
//				)
//			).bindTo(this),
//			FunctionDescriptor.ofVoid(`void*`, `void*`),
//			arena
//		)
//		vtable.setAtIndex(`void*`, 2, deallocate)
//		return structure
//	}
}