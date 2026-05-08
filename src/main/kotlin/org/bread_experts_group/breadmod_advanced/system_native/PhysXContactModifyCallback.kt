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
 * An interface class that the user can implement in order to modify contact constraints.
 *
 * **Threading:** It is **necessary** to make this class thread safe as it will be called in the context of the
 * simulation thread. It might also be necessary to make it reentrant, since some calls can be made by multi-threaded
 * parts of the physics engine.
 *
 * You can enable the use of this contact modification callback by raising the flag [PhysXPairFlag.eMODIFY_CONTACTS] in
 * the filter shader/callback (see [PxSimulationFilterShader]) for a pair of rigid body objects.
 *
 * Please note:
 * + Raising the contact modification flag will not wake the actors up automatically.
 * + It is not possible to turn off the performance degradation by simply removing the callback from the scene, the
 *   filter shader/callback has to be used to clear the contact modification flag.
 * + The contacts will only be reported as long as the actors are awake. There will be no callbacks while the actors are sleeping.
 *
 * @see PxScene.setContactModifyCallback
 * @see PxScene.getContactModifyCallback
 *
 * @author Miko Elbrecht (Kotlin)
 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
 * @since In accordance with PhysX 5.6.1
 */
interface PhysXContactModifyCallback {
	/**
	 * Passes modifiable arrays of contacts to the application.
	 *
	 * The initial contacts are regenerated from scratch each frame by collision detection.
	 *
	 * The number of contacts can not be changed, so you cannot add your own contacts.  You may however
	 * disable contacts using [PhysXContactSet.ignore].
	 *
	 * @param pairs The contact pairs that may be modified
	 *
	 * @see PhysXContactModifyPair
	 *
	 * @author Miko Elbrecht (Kotlin)
	 * @author NVIDIA Corporation, AGEIA Technologies, Inc. NovodeX AG. (Library headers, documentation, see copyright notice)
	 * @since In accordance with PhysX 5.6.1
	 */
	fun onContactModify(pairs: Array<PhysXContactModifyPair>)
}