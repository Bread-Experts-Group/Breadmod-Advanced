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

import kotlin.math.sqrt

/**
 * This is a quaternion class. For more information on quaternion mathematics
 * consult a mathematics source on complex numbers.
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
abstract class PhysXQuatT<T : Any> {
	abstract val x: T
	abstract val y: T
	abstract val z: T
	abstract val w: T

	/**
	 * rotates passed vec by this (assumed unitary)
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	fun rotate(v: PhysXVec3T<T>): PhysXVec3T.ReadWrite<T> {
		when (v.x) {
			is Float -> {
				v as PhysXVec3T<Float>
				this as PhysXQuatT<Float>
				val vx = 2f * v.x
				val vy = 2f * v.y
				val vz = 2f * v.z
				val w2 = (w * w) - 0.5f
				val dot2 = (x * vx + y * vy + z * vz)
				return PhysXVec3T.ReadWrite(
					vx * w2 + (y * vz - z * vy) * w + x * dot2,
					vy * w2 + (z * vx - x * vz) * w + y * dot2,
					vz * w2 + (x * vy - y * vx) * w + z * dot2
				) as PhysXVec3T.ReadWrite<T>
			}

			is Double -> {
				v as PhysXVec3T<Double>
				this as PhysXQuatT<Double>
				val vx = 2.0 * v.x
				val vy = 2.0 * v.y
				val vz = 2.0 * v.z
				val w2 = (w * w) - 0.5
				val dot2 = (x * vx + y * vy + z * vz)
				return PhysXVec3T.ReadWrite(
					vx * w2 + (y * vz - z * vy) * w + x * dot2,
					vy * w2 + (z * vx - x * vz) * w + y * dot2,
					vz * w2 + (x * vy - y * vx) * w + z * dot2
				) as PhysXVec3T.ReadWrite<T>
			}

			else -> throw IllegalArgumentException("Unsupported type ... ${v.x::class}")
		}
	}

	/**
	 * quaternion multiplication
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	operator fun times(q: PhysXQuatT<T>): ReadWrite<T> {
		when (q.x) {
			is Float -> {
				this as PhysXQuatT<Float>
				q as PhysXQuatT<Float>
				return ReadWrite(
					w * q.x + q.w * x + y * q.z - q.y * z,
					w * q.y + q.w * y + z * q.x - q.z * x,
					w * q.z + q.w * z + x * q.y - q.x * y,
					w * q.w - x * q.x - y * q.y - z * q.z
				) as ReadWrite<T>
			}

			is Double -> {
				this as PhysXQuatT<Double>
				q as PhysXQuatT<Double>
				return ReadWrite(
					w * q.x + q.w * x + y * q.z - q.y * z,
					w * q.y + q.w * y + z * q.x - q.z * x,
					w * q.z + q.w * z + x * q.y - q.x * y,
					w * q.w - x * q.x - y * q.y - z * q.z
				) as ReadWrite<T>
			}

			else -> throw IllegalArgumentException("Unsupported type ... ${q.x::class}")
		}
	}

	/**
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	operator fun times(r: T): ReadWrite<T> {
		when (r) {
			is Float -> {
				this as PhysXQuatT<Float>
				return ReadWrite(
					x * r,
					y * r,
					z * r,
					w * r
				) as ReadWrite<T>
			}

			is Double -> {
				this as PhysXQuatT<Double>
				return ReadWrite(
					x * r,
					y * r,
					z * r,
					w * r
				) as ReadWrite<T>
			}

			else -> throw IllegalArgumentException("Unsupported type ... ${r::class}")
		}
	}

	/**
	 * This is the squared 4D vector length, should be 1 for unit quaternions.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	fun magnitudeSquared(): T = when (this.x) {
		is Float -> {
			this as PhysXQuatT<Float>
			x * x + y * y + z * z + w * w
		}

		is Double -> {
			this as PhysXQuatT<Double>
			x * x + y * y + z * z + w * w
		}

		else -> throw IllegalArgumentException("Unsupported type ... ${this.x::class}")
	} as T

	/**
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	fun magnitude(): T = when (this.x) {
		is Float -> {
			this as PhysXQuatT<Float>
			sqrt(magnitudeSquared())
		}

		is Double -> {
			this as PhysXQuatT<Double>
			sqrt(magnitudeSquared())
		}

		else -> throw IllegalArgumentException("Unsupported type ... ${this.x::class}")
	} as T

	/**
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	fun getNormalized(): ReadWrite<T> = when (this.x) {
		is Float -> {
			this as PhysXQuatT<Float>
			val s = 1f / magnitude()
			ReadWrite(
				x * s,
				y * s,
				z * s,
				w * s
			) as ReadWrite<T>
		}

		is Double -> {
			this as PhysXQuatT<Double>
			val s = 1.0 / magnitude()
			ReadWrite(
				x * s,
				y * s,
				z * s,
				w * s
			) as ReadWrite<T>
		}

		else -> throw IllegalArgumentException("Unsupported type ... ${this.x::class}")
	}

	abstract class ReadOnly<T : Any> : PhysXQuatT<T>() {
		@DefinedProperty(0) abstract override val x: T
		@DefinedProperty(1) abstract override val y: T
		@DefinedProperty(2) abstract override val z: T
		@DefinedProperty(3) abstract override val w: T
	}

	open class ReadWrite<T : Any>(
		@DefinedProperty(0) override var x: T,
		@DefinedProperty(1) override var y: T,
		@DefinedProperty(2) override var z: T,
		@DefinedProperty(3) override var w: T
	) : PhysXQuatT<T>() {
		companion object {
			val PxIdentityF: ReadWrite<Float>
				get() = ReadWrite(0f, 0f, 0f, 1f)
			val PxIdentityD: ReadWrite<Double>
				get() = ReadWrite(0.0, 0.0, 0.0, 1.0)
		}
	}
}