package org.bread_experts_group.breadmod_advanced.system_native

sealed interface NativeSize {
	data class B64(val value: Long) : NativeSize
	data class B32(val value: Int) : NativeSize
}