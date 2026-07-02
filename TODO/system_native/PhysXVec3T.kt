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
 * 3 Element vector class.
 *
 * This is a 3-dimensional vector class with public data members.
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
abstract class PhysXVec3T<T : Any> {
	abstract val x: T
	abstract val y: T
	abstract val z: T

	/**
	 * negation
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	operator fun unaryMinus(): ReadWrite<T> = when (this.x) {
		is Float -> {
			this as PhysXVec3T<Float>
			ReadWrite(
				-x,
				-y,
				-z
			) as ReadWrite<T>
		}

		is Double -> {
			this as PhysXVec3T<Double>
			ReadWrite(
				-x,
				-y,
				-z
			) as ReadWrite<T>
		}

		else -> throw IllegalArgumentException("Unsupported type ... ${this.x::class}")
	}

	/**
	 * vector addition
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	operator fun plus(v: PhysXVec3T<T>): ReadWrite<T> = when (v.x) {
		is Float -> {
			v as PhysXVec3T<Float>
			this as PhysXVec3T<Float>
			ReadWrite(
				x + v.x,
				y + v.y,
				z + v.z
			) as ReadWrite<T>
		}

		is Double -> {
			v as PhysXVec3T<Double>
			this as PhysXVec3T<Double>
			ReadWrite(
				x + v.x,
				y + v.y,
				z + v.z
			) as ReadWrite<T>
		}

		else -> throw IllegalArgumentException("Unsupported type ... ${v.x::class}")
	}

	/**
	 * vector addition (mutating)
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	operator fun ReadWrite<T>.plusAssign(v: PhysXVec3T<T>): Unit = when (v.x) {
		is Float -> {
			v as PhysXVec3T<Float>
			this as ReadWrite<Float>
			this.x += v.x
			this.y += v.y
			this.z += v.z
		}

		is Double -> {
			v as PhysXVec3T<Double>
			this as ReadWrite<Double>
			this.x += v.x
			this.y += v.y
			this.z += v.z
		}

		else -> throw IllegalArgumentException("Unsupported type ... ${v.x::class}")
	}

	/**
	 * scalar post-multiplication
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	operator fun times(f: T): ReadWrite<T> = when (f) {
		is Float -> {
			this as PhysXVec3T<Float>
			ReadWrite(
				x * f,
				y * f,
				z * f
			) as ReadWrite<T>
		}

		is Double -> {
			this as PhysXVec3T<Double>
			ReadWrite(
				x * f,
				y * f,
				z * f
			) as ReadWrite<T>
		}

		else -> throw IllegalArgumentException("Unsupported type ... ${f::class}")
	}

	/**
	 * scalar post-multiplication (mutating)
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	operator fun ReadWrite<T>.timesAssign(f: T) = when (f) {
		is Float -> {
			this as ReadWrite<Float>
			this.x *= f
			this.y *= f
			this.z *= f
		}

		is Double -> {
			this as ReadWrite<Double>
			this.x *= f
			this.y *= f
			this.z *= f
		}

		else -> throw IllegalArgumentException("Unsupported type ... ${f::class}")
	}

	/**
	 * returns the squared magnitude
	 *
	 * Avoids calling [Math.sqrt]!
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	fun magnitudeSquared(): T = when (this.x) {
		is Float -> {
			this as PhysXVec3T<Float>
			x * x + y * y + z * z
		}

		is Double -> {
			this as PhysXVec3T<Double>
			x * x + y * y + z * z
		}

		else -> throw IllegalArgumentException("Unsupported type ... ${this.x::class}")
	} as T

	/**
	 * returns the magnitude
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	fun magnitude(): T = when (this.x) {
		is Float -> {
			this as PhysXVec3T<Float>
			sqrt(magnitudeSquared().toDouble()).toFloat()
		}

		is Double -> {
			this as PhysXVec3T<Double>
			sqrt(magnitudeSquared())
		}

		else -> throw IllegalArgumentException("Unsupported type ... ${this.x::class}")
	} as T

	/**
	 * returns the scalar product of this and other.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	fun dot(v: PhysXVec3T<T>): T = when (v.x) {
		is Float -> {
			v as PhysXVec3T<Float>
			this as PhysXVec3T<Float>
			x * v.x + y * v.y + z * v.z
		}

		is Double -> {
			v as PhysXVec3T<Double>
			this as PhysXVec3T<Double>
			x * v.x + y * v.y + z * v.z
		}

		else -> throw IllegalArgumentException("Unsupported type ... ${this.x::class}")
	} as T

	/**
	 * cross product
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@Suppress("UNCHECKED_CAST")
	fun cross(v: PhysXVec3T<T>) = when (v.x) {
		is Float -> {
			v as PhysXVec3T<Float>
			this as PhysXVec3T<Float>
			ReadWrite(
				y * v.z - z * v.y,
				z * v.x - x * v.z,
				x * v.y - y * v.x
			)
		}

		is Double -> {
			v as PhysXVec3T<Double>
			this as PhysXVec3T<Double>
			ReadWrite(
				y * v.z - z * v.y,
				z * v.x - x * v.z,
				x * v.y - y * v.x
			)
		}

		else -> throw IllegalArgumentException("Unsupported type ... ${this.x::class}")
	} as PhysXVec3T<T>

	abstract class ReadOnly<T : Any> : PhysXVec3T<T>() {
		@DefinedProperty(0) abstract override val x: T
		@DefinedProperty(1) abstract override val y: T
		@DefinedProperty(2) abstract override val z: T
	}

	open class ReadWrite<T : Any>(
		@DefinedProperty(0) override var x: T,
		@DefinedProperty(1) override var y: T,
		@DefinedProperty(2) override var z: T,
	) : PhysXVec3T<T>() {
		companion object {
			val PxZeroF: ReadWrite<Float>
				get() = ReadWrite(0f, 0f, 0f)
		}
	}
}