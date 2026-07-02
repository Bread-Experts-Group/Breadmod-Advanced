package org.bread_experts_group.breadmod_advanced.system_native

import org.bread_experts_group.generic.FlagSet

/**
 * Collection of flags describing the actions to take for a collision pair.
 *
 * @see PxSimulationFilterShader.filter
 * @see PhysXSimulationFilterCallback
 */
@Suppress("EnumEntryName")
enum class PxPairFlag {
	/**
	 * Process the contacts of this collision pair in the dynamics solver.
	 *
	 * *Only takes effect if the colliding actors are rigid bodies.*
	 */
	eSOLVE_CONTACT,

	/**
	 * Call contact modification callback for this collision pair
	 *
	 * *Only takes effect if the colliding actors are rigid bodies.*
	 *
	 * @see PhysXContactModifyCallback
	 */
	eMODIFY_CONTACTS,

	/**
	 * Call contact report callback or trigger callback when this collision pair starts to be in contact.
	 *
	 * If one of the two collision objects is a trigger shape (see [PxShapeFlag.eTRIGGER_SHAPE])
	 * then the trigger callback will get called as soon as the other object enters the trigger volume.
	 * If none of the two collision objects is a trigger shape then the contact report callback will get
	 * called when the actors of this collision pair start to be in contact.
	 *
	 * *Only takes effect if the colliding actors are rigid bodies.*
	 *
	 * *Only takes effect if eDETECT_DISCRETE_CONTACT or eDETECT_CCD_CONTACT is raised*
	 *
	 * @see PhysXSimulationEventCallback.onContact
	 * @see PhysXSimulationEventCallback.onTrigger
	 */
	eNOTIFY_TOUCH_FOUND,

	/**
	 * Call contact report callback while this collision pair is in contact
	 *
	 * If none of the two collision objects is a trigger shape then the contact report callback will get
	 * called while the actors of this collision pair are in contact.
	 *
	 * *Triggers do not support this event. Persistent trigger contacts need to be tracked separately
	 * by observing [eNOTIFY_TOUCH_FOUND]/[eNOTIFY_TOUCH_LOST] events.*
	 *
	 * *Only takes effect if the colliding actors are rigid bodies.*
	 *
	 * *No report will get sent if the objects in contact are sleeping.*
	 *
	 * *Only takes effect if [eDETECT_DISCRETE_CONTACT] or [eDETECT_CCD_CONTACT] is raised*
	 *
	 * *If this flag gets enabled while a pair is in touch already, there will be no
	 * [eNOTIFY_TOUCH_PERSISTS] events until the pair loses and regains touch.*
	 *
	 * @see PhysXSimulationEventCallback.onContact
	 * @see PhysXSimulationEventCallback.onTrigger
	 */
	eNOTIFY_TOUCH_PERSISTS,

	/**
	 * Call contact report callback or trigger callback when this collision pair stops to be in contact
	 *
	 * If one of the two collision objects is a trigger shape (see [PxShapeFlag.eTRIGGER_SHAPE])
	 * then the trigger callback will get called as soon as the other object leaves the trigger volume.
	 * If none of the two collision objects is a trigger shape then the contact report callback will get
	 * called when the actors of this collision pair stop to be in contact.
	 *
	 * *Only takes effect if the colliding actors are rigid bodies.*
	 *
	 * *This event will also get triggered if one of the colliding objects gets deleted.*
	 *
	 * *Only takes effect if [eDETECT_DISCRETE_CONTACT] or [eDETECT_CCD_CONTACT] is raised*
	 *
	 * @see PhysXSimulationEventCallback.onContact
	 * @see PhysXSimulationEventCallback.onTrigger
	 */
	eNOTIFY_TOUCH_LOST,

	/**
	 * Call contact report callback when this collision pair is in contact during CCD passes.
	 *
	 * If CCD with multiple passes is enabled, then a fast moving object might bounce on and off the same
	 * object multiple times. Hence, the same pair might be in contact multiple times during a simulation step.
	 * This flag will make sure that all the detected collision during CCD will get reported. For performance
	 * reasons, the system can not always tell whether the contact pair lost touch in one of the previous CCD
	 * passes and thus can also not always tell whether the contact is new or has persisted. eNOTIFY_TOUCH_CCD
	 * just reports when the two collision objects were detected as being in contact during a CCD pass.
	 *
	 * *Only takes effect if the colliding actors are rigid bodies.*
	 *
	 * *Trigger shapes are not supported.*
	 *
	 * *Only takes effect if [eDETECT_CCD_CONTACT] is raised*
	 *
	 * @see PhysXSimulationEventCallback.onContact
	 * @see PhysXSimulationEventCallback.onTrigger
	 */
	eNOTIFY_TOUCH_CCD,

	/**
	 * Call contact report callback when the contact force between the actors of this collision pair exceeds one of
	 * the actor-defined force thresholds.
	 *
	 * *Only takes effect if the colliding actors are rigid bodies.*
	 *
	 * *Only takes effect if [eDETECT_DISCRETE_CONTACT] or [eDETECT_CCD_CONTACT] is raised*
	 *
	 * *Only works with PGS solver, and only on CPU.*
	 *
	 * @see PhysXSimulationEventCallback.onContact
	 */
	eNOTIFY_THRESHOLD_FORCE_FOUND,

	/**
	 * Call contact report callback when the contact force between the actors of this collision pair continues to exceed one of the actor-defined force thresholds.
	 *
	 * *Only takes effect if the colliding actors are rigid bodies.*
	 *
	 * *If a pair gets re-filtered and this flag has previously been disabled, then the report will not get fired
	 * in the same frame even if the force threshold has been reached in the previous one
	 * (unless [eNOTIFY_THRESHOLD_FORCE_FOUND] has been set in the previous frame).*
	 *
	 * *Only takes effect if [eDETECT_DISCRETE_CONTACT] or [eDETECT_CCD_CONTACT] is raised*
	 *
	 * *Only works with PGS solver, and only on CPU.*
	 *
	 * @see PhysXSimulationEventCallback.onContact
	 */
	eNOTIFY_THRESHOLD_FORCE_PERSISTS,

	/**
	 * Call contact report callback when the contact force between the actors of this collision pair falls below one
	 * of the actor-defined force thresholds (includes the case where this collision pair stops being in contact).
	 *
	 * *Only takes effect if the colliding actors are rigid bodies.*
	 *
	 * *If a pair gets re-filtered and this flag has previously been disabled, then the report will not get fired in
	 * the same frame even if the force threshold has been reached in the previous one
	 * (unless [eNOTIFY_THRESHOLD_FORCE_FOUND] or [eNOTIFY_THRESHOLD_FORCE_PERSISTS] has been set in the previous frame).*
	 *
	 * *Only takes effect if [eDETECT_DISCRETE_CONTACT] or [eDETECT_CCD_CONTACT] is raised*
	 *
	 * *Only works with PGS solver, and only on CPU.*
	 *
	 * @see PhysXSimulationEventCallback.onContact
	 */
	eNOTIFY_THRESHOLD_FORCE_LOST,

	/**
	 * Provide contact points in contact reports for this collision pair.
	 *
	 * *Only takes effect if the colliding actors are rigid bodies and if used in combination with the flags
	 * eNOTIFY_TOUCH_... or eNOTIFY_THRESHOLD_FORCE_...*
	 *
	 * *Only takes effect if [eDETECT_DISCRETE_CONTACT] or [eDETECT_CCD_CONTACT] is raised*
	 *
	 * @see PhysXSimulationEventCallback.onContact
	 * @see PhysXContactPair
	 * @see PhysXContactPair.extractContacts
	 */
	eNOTIFY_CONTACT_POINTS,

	/**
	 * This flag is used to indicate whether this pair generates discrete collision detection contacts.
	 *
	 * *Contacts are only responded to if [eSOLVE_CONTACT] is enabled.*
	 */
	eDETECT_DISCRETE_CONTACT,

	/**
	 * This flag is used to indicate whether this pair generates CCD contacts.
	 *
	 * *The contacts will only be responded to if eSOLVE_CONTACT is enabled on this pair.*
	 * *The scene must have [PxSceneFlag.eENABLE_CCD] enabled to use this feature.*
	 * *Non-static bodies of the pair should have [PxRigidBodyFlag.eENABLE_CCD] specified for this feature to work correctly.*
	 * *This flag is not supported with trigger shapes. However, CCD trigger events can be emulated using non-trigger shapes
	 * and requesting [eNOTIFY_TOUCH_FOUND] and [eNOTIFY_TOUCH_LOST] and not raising [eSOLVE_CONTACT] on the pair.*
	 *
	 * @see PxRigidBodyFlag.eENABLE_CCD
	 * @see PxSceneFlag.eENABLE_CCD
	 */
	eDETECT_CCD_CONTACT,

	/**
	 *  Provide pre solver velocities in contact reports for this collision pair.
	 *
	 * If the collision pair has contact reports enabled, the velocities of the rigid bodies before contacts have been solved
	 * will be provided in the contact report callback unless the pair lost touch in which case no data will be provided.
	 *
	 * *Usually it is not necessary to request these velocities as they will be available by querying the velocity from the provided
	 * PxRigidActor object directly. However, it might be the case that the velocity of a rigid body gets set while the simulation is running
	 * in which case the PxRigidActor would return this new velocity in the contact report callback and not the velocity the simulation used.*
	 *
	 * @see PhysXSimulationEventCallback.onContact
	 * @see PhysXContactPairVelocity
	 * @see PhysXContactPairHeader.extraDataStream
	 */
	ePRE_SOLVER_VELOCITY,

	/**
	 * Provide post solver velocities in contact reports for this collision pair.
	 *
	 * If the collision pair has contact reports enabled, the velocities of the rigid bodies after contacts have been solved
	 * will be provided in the contact report callback unless the pair lost touch in which case no data will be provided.
	 *
	 * @see PhysXSimulationEventCallback.onContact
	 * @see PhysXContactPairVelocity
	 * @see PhysXContactPairHeader.extraDataStream
	 */
	ePOST_SOLVER_VELOCITY,

	/**
	 * Provide rigid body poses in contact reports for this collision pair.
	 *
	 * If the collision pair has contact reports enabled, the rigid body poses at the contact event will be provided
	 * in the contact report callback unless the pair lost touch in which case no data will be provided.
	 *
	 * *Usually it is not necessary to request these poses as they will be available by querying the pose from the provided
	 * 	PxRigidActor object directly. However, it might be the case that the pose of a rigid body gets set while the simulation is running
	 * 	in which case the PxRigidActor would return this new pose in the contact report callback and not the pose the simulation used.
	 * 	Another use case is related to CCD with multiple passes enabled, A fast moving object might bounce on and off the same
	 * 	object multiple times. This flag can be used to request the rigid body poses at the time of impact for each such collision event.*
	 *
	 * @see PhysXSimulationEventCallback.onContact
	 * @see PhysXContactPairPose
	 * @see PhysXContactPairHeader.extraDataStream
	 */
	eCONTACT_EVENT_POSE,

	/**
	 * For internal use only.
	 */
	eNEXT_FREE;

	companion object {
		/**
		 * Provided default flag to do simple contact processing for this collision pair.
		 */
		val eCONTACT_DEFAULT: FlagSet<PxPairFlag> = FlagSet.of(eSOLVE_CONTACT, eDETECT_DISCRETE_CONTACT)

		/**
		 * Provided default flag to get commonly used trigger behavior for this collision pair.
		 */
		val eTRIGGER_DEFAULT: FlagSet<PxPairFlag> = FlagSet.of(eNOTIFY_TOUCH_FOUND, eNOTIFY_TOUCH_LOST, eDETECT_DISCRETE_CONTACT)
	}
}