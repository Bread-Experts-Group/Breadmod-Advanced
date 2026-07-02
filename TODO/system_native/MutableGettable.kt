package org.bread_experts_group.breadmod_advanced.system_native

interface MutableGettable<T> : Mutable<T> {
	fun get(): T
}