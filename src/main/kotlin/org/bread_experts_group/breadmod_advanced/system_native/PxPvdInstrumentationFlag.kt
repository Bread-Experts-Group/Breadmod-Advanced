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

import org.bread_experts_group.generic.Flaggable

/**
 * types of instrumentation that PVD can do.
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
enum class PxPvdInstrumentationFlag : Flaggable {
	/**
	 * Send debugging information to PVD.

	 * This information is the actual object data of the rigid statics, shapes,
	   articulations, etc.  Sending this information has a noticeable impact on
	   performance and thus this flag should not be set if you want an accurate
	   performance profile.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eDEBUG,

	/**
	 * Send profile information to PVD.

	 * This information populates PVD's profile view.  It has (at this time) negligible
	   cost compared to Debug information and makes PVD *much* more useful so it is quite
	   highly recommended.

	 * This flag works together with a PxCreatePhysics parameter.
	 * Using it allows the SDK to send profile events to PVD.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	ePROFILE,

	/**
	 * Send memory information to PVD.

	 * The PVD sdk side hooks into the Foundation memory controller and listens to
	   allocation/deallocation events.  This has a noticable hit on the first frame,
	   however, this data is somewhat compressed and the PhysX SDK doesn't allocate much
	   once it hits a steady state.  This information also has a fairly negligible
	   impact and thus is also highly recommended.

	 * This flag works together with a PxCreatePhysics parameter,
	   trackOutstandingAllocations.  Using both of them together allows users to have
	   an accurate view of the overall memory usage of the simulation at the cost of
	   a hashtable lookup per allocation/deallocation.  Again, PhysX makes a best effort
	   attempt not to allocate or deallocate during simulation so this hashtable lookup
	   tends to have no effect past the first frame.

	 * Sending memory information without tracking outstanding allocations means that
	   PVD will accurate information about the state of the memory system before the
	   actual connection happened.
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	eMEMORY;

	override val position: Long = 1L shl ordinal
}