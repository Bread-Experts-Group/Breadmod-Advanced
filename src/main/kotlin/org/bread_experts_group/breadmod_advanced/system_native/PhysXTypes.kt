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

import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.float
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.int32_t
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.uint16_t
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.uint32_t
import org.bread_experts_group.breadmod_advanced.system_native.CanonicalLayouts.`void*`
import org.bread_experts_group.generic.FlagSet
import java.lang.foreign.*
import java.lang.invoke.MethodType

typealias PxI32_t = Int

typealias PxU8_t = UByte
typealias PxU16_t = UShort
typealias PxU32_t = UInt
typealias PxU64_t = ULong
typealias PxVec3_t = PxVec3T<Float>
typealias PxVec3d_t = PxVec3T<Double>
typealias PxReal_t = Float

typealias CUevent = MemorySegment

/**
 * Filter method to specify how a pair of potentially colliding objects should be processed.
 *
 * Collision filtering is a mechanism to specify how a pair of potentially colliding objects should be processed by the
 * simulation. A pair of objects is potentially colliding if the bounding volumes of the two objects overlap.
 * In short, a collision filter decides whether a collision pair should get processed, temporarily ignored or discarded.
 * If a collision pair should get processed, the filter can additionally specify how it should get processed, for instance,
 * whether contacts should get resolved, which callbacks should get invoked or which reports should be sent etc.
 * The function returns the PxFilterFlag flags and sets the PxPairFlag flags to define what the simulation should do with the
 * given collision pair.
 *
 * *A default implementation of a filter shader is provided in the PhysX extensions library, see [PxDefaultSimulationFilterShader].*
 *
 * This methods gets called when:
 * - The bounding volumes of two objects start to overlap.
 * - The bounding volumes of two objects overlap and the filter data or filter attributes of one of the objects changed
 * - A re-filtering was forced through resetFiltering() (see [PhysXScene.resetFiltering])
 * - Filtering is requested in scene queries
 *
 * *Certain pairs of objects are always ignored and this method does not get called. This is the case for the
 * following pairs:*
 *
 * - Pair of static rigid actors
 * - A static rigid actor and a kinematic actor (unless one is a trigger or if explicitly enabled through [PxPairFilteringMode.eKEEP])
 * - Two kinematic actors (unless one is a trigger or if explicitly enabled through [PxPairFilteringMode.eKEEP])
 * - Two jointed rigid bodies and the joint was defined to disable collision
 * - Two articulation links if connected through an articulation joint
 *
 * *This is a performance critical method and should be stateless. You should neither access external objects
 * from within this method nor should you call external methods that are not inlined. If you need a more complex
 * logic to filter a collision pair then use the filter callback mechanism for this pair (see [PhysXSimulationFilterCallback],
 * [PxFilterFlag.eCALLBACK], [PxFilterFlag.eNOTIFY]).*
 *
 * @param attributes0 The filter attribute of the first object
 * @param filterData0 The custom filter data of the first object
 * @param attributes1 The filter attribute of the second object
 * @param filterData1 The custom filter data of the second object
 * @param pairFlags Flags giving additional information on how an accepted pair should get processed
 * @param constantBlock The constant global filter data (see [PhysXSceneDesc.filterShaderData])
 * @return Filter flags defining whether the pair should be discarded, temporarily ignored, processed and whether the
 * filter callback should get invoked for this pair, and pair flags giving additional information on how an accepted pair should get processed
 *
 * @see PhysXSimulationFilterCallback
 * @see PhysXFilterData
 * @see PhysXFilterObjectAttributes
 * @see PxFilterFlag
 * @see PxPairFlag
 * @see PhysXSceneDesc.filterShader
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
val PxSimulationFilterShader = MethodType.methodType(
	FlagSet::class.java,
	PhysXFilterObjectAttributes::class.java,
	PhysXFilterData::class.java,
	PhysXFilterObjectAttributes::class.java,
	PhysXFilterData::class.java,
	Mutable::class.java,
	MemorySegment::class.java
)

val PxI32: ValueLayout.OfInt = int32_t

val PxU16: ValueLayout.OfShort = uint16_t
val PxU32: ValueLayout.OfInt = uint32_t
val PxReal: ValueLayout = float

@Suppress("FloatingPointLiteralPrecision")
val PX_MAX_F32: Float = 3.4028234663852885981170418348452e+38F

val PX_MAX_REAL: Float = PX_MAX_F32
val PX_MAX_BOUNDS_EXTENTS: Float = PX_MAX_REAL * 0.25f

val PxPairFlags: ValueLayout.OfShort = PxU16

val PxFilterObjectAttributes: ValueLayout.OfInt = PxU32

val PxFilterData: StructLayout = MemoryLayout.structLayout(
	PxU32.withName("word0"),
	PxU32.withName("word1"),
	PxU32.withName("word2"),
	PxU32.withName("word3")
)

val PxFoundation: AddressLayout = `void*`
val PxAllocatorCallback: AddressLayout = `void*`
val PxErrorCallback: AddressLayout = `void*`

val PxCudaContextManager: AddressLayout = `void*`
val PxCudaContextManagerDesc: AddressLayout = `void*`
val PxProfilerCallback: AddressLayout = `void*`
val PxDefaultCpuDispatcher: AddressLayout = `void*`

val PxPhysics: AddressLayout = `void*`

val PxScene: AddressLayout = `void*`

val PxPvd: AddressLayout = `void*`
val PxPvdTransport: AddressLayout = `void*`

val PxOmniPvd: AddressLayout = `void*`

val PxTolerancesScale: StructLayout = MemoryLayout.structLayout(
	float.withName("length"),
	float.withName("speed")
)