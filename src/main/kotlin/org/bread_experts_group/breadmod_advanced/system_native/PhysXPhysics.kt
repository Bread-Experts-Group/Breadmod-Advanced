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
import org.bread_experts_group.ffi.getDowncallVoid
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.invoke.MethodHandle

/**
 * Abstract singleton factory class used for instancing objects in the Physics SDK.
 *
 * In addition you can use PxPhysics to set global parameters which will effect all scenes and create
 * objects that can be shared across multiple scenes.
 *
 * You can get an instance of this class by calling [PhysXLibrary.pxCreatePhysics].
 *
 * @since In accordance with PhysX 5.6.1
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 *
 * @see [PhysXLibrary.pxCreatePhysics] [PhysXScene]
 */
class PhysXPhysics internal constructor(linker: Linker, private val segment: MemorySegment) {
	private val release: MethodHandle

	init {
		val vtable = segment.reinterpret(`void*`.byteSize()).get(`void*`, 0)
			.reinterpret(`void*`.byteSize() * 64)
		release = vtable.getAtIndex(`void*`, 1).getDowncallVoid(
			linker,
			PxFoundation.ptr.withName("self")
		)
	}

	/**
	 * Destroys the instance it is called on.
	 *
	 * Use this release method to destroy an instance of this class. Be sure
	 * to not keep a reference to this object after calling release.
	 * Avoid release calls while a scene is simulating (in between simulate() and fetchResults() calls).
	 *
	 * Note that this must be called once for each prior call to PxCreatePhysics, as
	 * there is a reference counter. Also note that you mustn't destroy the PxFoundation instance (holding the allocator, error callback etc.)
	 * until after the reference count reaches 0 and the SDK is actually removed.
	 *
	 * Releasing an SDK will also release any objects created through it (scenes, triangle meshes, convex meshes, heightfields, shapes etc.),
	 * provided the user hasn't already done so.
	 *
	 * *Releasing the PxPhysics instance is a prerequisite to releasing the PxFoundation instance.*
	 *
	 * @since In accordance with PhysX 5.6.1
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 *
	 * @see PhysXFoundation
	 * @see PhysXLibrary.pxCreatePhysics
	 */
	fun release() {
		this.release.invokeExact(segment)
	}
}