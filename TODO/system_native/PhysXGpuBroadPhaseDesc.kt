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

/**
 * Descriptor for the GPU broad-phase.
 *
 * This struct contains parameters that are only relevant for the GPU broad-phase.
 *
 * **gpuBroadPhaseNbBitsShift**:
 * - The GPU broadphase encodes bounds as integers, and then right-shifts the data by this amount of bits.
 *   This makes the bounds a bit larger, which avoids losing and recreating overlaps over and over when
 *   two objects are just touching. This effect is similar to what can be achieved with the contact distance
 *   parameter, and the amount by which the bounds are inflated depends on the distance from the bounds to
 *   the origin (as the bounds encoding does not use a regular float-to-integer conversion, but instead a
 *   reinterpretation of the float's bits). The default value in the GPU broadphase has always been 4 bits
 *   but it is safe to use 0 here for more accurate bounds.
 *
 * **gpuBroadPhaseNbBitsEnvID**:
 * - The bits lost by the previous shifts (gpuBroadPhaseNbBitsShiftXYZ) can be replaced with bits of the
 *   environment IDs. This only makes sense when these parameters are used (see [PhysXActor.setEnvironmentID]
 *   and [PhysXAggregate.setEnvironmentID]). In this case a number of bits from the environment IDs are stored
 *   in the MSBs of encoded bounds. This has the effect of virtually spreading the bounds over 3D space,
 *   which reduces the number of internal overlaps inside the broad-phase. This is mainly useful in RL
 *   scenarios with "co-located" environments, but it can also provide performance gains with regular grid
 *   configurations, that also generate a lot of internal overlaps on all coordinate axes.
 *   Beware: when using this feature, each object of each environment should be assigned a proper environment
 *   ID. Objects shared between all environments (i.e. objects whose environment ID is PX_INVALID_U32) will
 *   otherwise be internally assigned bounds that cover the entire 3D space, creating a lot of overlaps and
 *   potential performance issues. This is only a concern when gpuBroadPhaseNbBitsEnvIDX/Y/Z are non zero,
 *   shared objects are fine otherwise.
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 *
 * @see [PxBroadPhaseType.eGPU]
 */
data class PhysXGpuBroadPhaseDesc(
	/**
	 * number of bits used for "snap to grid" on the X axis
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val gpuBroadPhaseNbBitsShiftX: PxU8_t = 4u,
	/**
	 * number of bits used for "snap to grid" on the Y axis
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val gpuBroadPhaseNbBitsShiftY: PxU8_t = 4u,
	/**
	 * number of bits used for "snap to grid" on the Z axis
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val gpuBroadPhaseNbBitsShiftZ: PxU8_t = 4u,
	/**
	 * number of environment ID bits merged with the bounds on the X axis
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val gpuBroadPhaseNbBitsEnvIDX: PxU8_t = 0u,
	/**
	 * number of environment ID bits merged with the bounds on the Y axis
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val gpuBroadPhaseNbBitsEnvIDY: PxU8_t = 0u,
	/**
	 * number of environment ID bits merged with the bounds on the Z axis
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	val gpuBroadPhaseNbBitsEnvIDZ: PxU8_t = 0u,
)