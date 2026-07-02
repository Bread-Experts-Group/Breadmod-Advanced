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

package org.bread_experts_group.breadmod_advanced.system_native

import java.lang.foreign.MemorySegment

/**
 * Descriptor used to create a [PxCudaContextManager]

 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
data class PhysXCudaContextManagerDesc(
	/**
	 * The CUDA context to manage
	 *
	 * If left NULL, the [PhysXCudaContextManager] will create a new context.  If
	 * graphicsDevice is also not NULL, this new CUDA context will be bound to
	 * that graphics device, enabling the use of CUDA/Graphics interop features.
	 *
	 * If ctx is not NULL, the specified context must be applied to the thread
	 * that is allocating the [PhysXCudaContextManager] at creation time (aka, it
	 * cannot be popped).  The [PhysXCudaContextManager] will take ownership of the
	 * context until the manager is released.  All access to the context must be
	 * gated by lock acquisition.
	 *
	 * If the user provides a context for the [PhysXCudaContextManager], the context
	 * _must_ have either been created on the GPU ordinal returned by
	 * [PxGetSuggestedCudaDeviceOrdinal] or on your graphics device.

	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(0) var ctx: MemorySegment = MemorySegment.NULL,
	/**
	 * D3D device pointer or OpenGl context handle
	 *
	 * Only applicable when ctx is NULL, thus forcing a new context to be
	 * created.  In that case, the created context will be bound to this
	 * graphics device.

	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(1) var graphicsDevice: MemorySegment = MemorySegment.NULL,
	/**
	 * CUDA device ordinal
	 *
	 * Only applicable when ctx is NULL, thus forcing a new context to be created based on the CUDA device ordinal.
	 * The first CUDA device will have an ordinal value of 0 and so on.
	 * If the CUDA device ordinal is -1, the device selected will be queried from the environment variable PHYSX_GPU_DEVICE.
	 *
	 * *If the environment variable PHYSX_GPU_DEVICE is not found, the CUDA device ordinal will default to 0.*

	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(2) var deviceOrdinal: PxI32_t = -1,
	/**
	 * Application-specific GUID
	 *
	 * If your application employs PhysX modules that use CUDA you need to use a GUID
	 * so that patches for new architectures can be released for your game.You can obtain a GUID for your
	 * application from Nvidia.

	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(3) var appGUID: MemorySegment = MemorySegment.NULL,
	/**
	 *  Application-specific device memory allocator
	 *
	 * the application can implement an device memory allocator, which inherites [PhysXDeviceAllocatorCallback], and
	 * pass that to the [PhysXCudaContextManagerDesc]. The SDK will use that allocator to allocate device memory instead of
	 * using the defaul CUDA device memory allocator.

	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	@DefinedProperty(4) var deviceAllocator: MemorySegment = MemorySegment.NULL
)