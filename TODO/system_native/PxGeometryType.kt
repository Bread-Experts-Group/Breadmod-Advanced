package org.bread_experts_group.breadmod_advanced.system_native

/**
  A geometry type.
 *
 * Used to distinguish the type of a [PhysXGeometry] object.
 */
@Suppress("EnumEntryName")
enum class PxGeometryType {
	eSPHERE,
	ePLANE,
	eCAPSULE,
	eBOX,
	eCONVEXCORE,
	eCONVEXMESH,
	ePARTICLESYSTEM,
	eTETRAHEDRONMESH,
	eTRIANGLEMESH,
	eHEIGHTFIELD,
	eCUSTOM
}