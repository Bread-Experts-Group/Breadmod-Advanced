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
 * VersionNumbers:  The combination of these
   numbers uniquely identifies the API, and should
   be incremented when the SDK API changes.  This may
   include changes to file formats.
 *
 * This header is included in the main SDK header files
   so that the entire SDK and everything that builds on it
   is completely rebuilt when this file changes.  Thus,
   this file is not to include a frequently changing
   build number.  See BuildNumber.h for that.

 * Each of these three values should stay below 255 because
   sometimes they are stored in a byte.

 * @since In accordance with PhysX 5.6.1
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
*/
object PxPhysicsVersion {
	const val PX_PHYSICS_VERSION_MAJOR: UInt = 5u
	const val PX_PHYSICS_VERSION_MINOR: UInt = 6u
	const val PX_PHYSICS_VERSION_BUGFIX: UInt = 1u
	val PX_PHYSICS_VERSION: UInt = ((PX_PHYSICS_VERSION_MAJOR shl 24) + (PX_PHYSICS_VERSION_MINOR shl 16) + (PX_PHYSICS_VERSION_BUGFIX shl 8) + 0u)
}