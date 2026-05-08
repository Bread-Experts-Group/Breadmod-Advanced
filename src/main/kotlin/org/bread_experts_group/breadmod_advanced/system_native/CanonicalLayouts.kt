package org.bread_experts_group.breadmod_advanced.system_native

import org.bread_experts_group.ffi.nativeLinker
import java.lang.foreign.AddressLayout
import java.lang.foreign.ValueLayout

// TODO: Should be a BSL thing

object CanonicalLayouts {
	val bool: ValueLayout
	val char: ValueLayout
	val short: ValueLayout
	val int: ValueLayout
	val long: ValueLayout
	val `long long`: ValueLayout
	val float: ValueLayout
	val double: ValueLayout
	val size_t: ValueLayout
	val wchar_t: ValueLayout
	val `void*`: AddressLayout

	val int8_t: ValueLayout.OfByte
	val uint8_t: ValueLayout.OfByte

	val int32_t: ValueLayout.OfInt
	val uint32_t: ValueLayout.OfInt

	val int64_t: ValueLayout.OfLong
	val uint64_t: ValueLayout.OfLong

	@Suppress("UnusedReceiverParameter")
	val Any.ptr: AddressLayout
		get() = `void*`

	init {
		val layouts = nativeLinker.canonicalLayouts()
		this.`void*` = layouts["void*"]!! as AddressLayout
		this.bool = layouts["bool"]!! as ValueLayout
		this.char = layouts["char"]!! as ValueLayout
		this.short = layouts["short"]!! as ValueLayout
		this.int = layouts["int"]!! as ValueLayout
		this.long = layouts["long"]!! as ValueLayout
		this.`long long` = layouts["long long"]!! as ValueLayout
		this.float = layouts["float"]!! as ValueLayout
		this.double = layouts["double"]!! as ValueLayout
		this.size_t = layouts["size_t"]!! as ValueLayout
		this.wchar_t = layouts["wchar_t"]!! as ValueLayout

		if (this.bool is ValueLayout.OfByte) this.int8_t = this.bool
		else if (this.char is ValueLayout.OfByte) this.int8_t = this.char
		else throw IllegalStateException("No suitable C types for int8.")
		this.uint8_t = this.int8_t

		if (this.int is ValueLayout.OfInt) this.int32_t = this.int
		else if (this.long is ValueLayout.OfInt) this.int32_t = this.long
		else throw IllegalStateException("No suitable C types for int32.")
		this.uint32_t = this.int32_t

		if (this.int is ValueLayout.OfLong) this.int64_t = this.int
		else if (this.long is ValueLayout.OfLong) this.int64_t = this.long
		else if (this.`long long` is ValueLayout.OfLong) this.int64_t = this.`long long`
		else throw IllegalStateException("No suitable C types for int64.")
		this.uint64_t = this.int64_t
	}
}