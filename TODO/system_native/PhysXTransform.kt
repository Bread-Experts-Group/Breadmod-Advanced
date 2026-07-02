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

import org.bread_experts_group.breadmod_advanced.system_native.PhysXQuatT.ReadWrite.Companion.PxIdentityD
import org.bread_experts_group.breadmod_advanced.system_native.PhysXQuatT.ReadWrite.Companion.PxIdentityF

/**
 * class representing a rigid euclidean transform as a quaternion and a vector
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
abstract class PhysXTransformT<T : Any> {
	abstract val q: PhysXQuatT<T>
	abstract val p: PhysXVec3T<T>

	/**
	 * Transform transform to parent (returns compound transform: first src, then *this)
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun transform(src: PhysXTransformT<T>): ReadWrite<T> {
		// src = [srct, srcr] -> [r*srct + t, r*srcr]
		return ReadWrite(q.rotate(src.p) + p, q * src.q)
	}

	abstract class ReadOnly<T : Any> : PhysXTransformT<T>() {
		@DefinedProperty(0) abstract override val q: PhysXQuatT<T>
		@DefinedProperty(1) abstract override val p: PhysXVec3T<T>
	}

	open class ReadWrite<T : Any>(
		@DefinedProperty(0) override var q: PhysXQuatT<T>,
		@DefinedProperty(1) override var p: PhysXVec3T<T>
	) : PhysXTransformT<T>() {
		@Suppress("UNCHECKED_CAST")
		constructor(position: PhysXVec3T<T>) : this(
			when (position.x) {
				is Float -> PxIdentityF
				is Double -> PxIdentityD
				else -> throw IllegalArgumentException("Unsupported type ... ${position.x::class}")
			} as PhysXQuatT<T>,
			position
		)

		constructor(p0: PhysXVec3T<T>, q0: PhysXQuatT<T>) : this(q0, p0)
	}
}