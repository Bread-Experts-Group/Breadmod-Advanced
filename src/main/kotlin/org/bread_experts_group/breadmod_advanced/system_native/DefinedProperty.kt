package org.bread_experts_group.breadmod_advanced.system_native

@Target(
	AnnotationTarget.PROPERTY,
	AnnotationTarget.VALUE_PARAMETER
)
annotation class DefinedProperty(val index: Long)