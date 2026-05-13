package org.bread_experts_group.breadmod_advanced.system_native

class AbsoluteBitField(initialCapacity: Int = Int.SIZE_BITS - 1) {
	@Suppress("MemberVisibilityCanBePrivate")
	var bitField: IntArray = IntArray((initialCapacity / Int.SIZE_BITS) + 1)

	operator fun set(index: Long, value: Boolean) {
		val position = index / Int.SIZE_BITS
		if (position > Int.MAX_VALUE) throw ArrayIndexOutOfBoundsException(
			"Index $index too large (> ${(Int.MAX_VALUE * Int.SIZE_BITS) - 1})"
		)
		if ((bitField.size * Int.SIZE_BITS) - 1 < position) {
			val newField = IntArray((position / Int.SIZE_BITS).toInt() + 1)
			System.arraycopy(bitField, 0, newField, 0, bitField.size)
			bitField = newField
		}
		val mask = 1 shl (index % Int.SIZE_BITS).toInt()
		val i = bitField[position.toInt()]
		bitField[position.toInt()] = if (value) i or mask else i and mask.inv()
	}

	operator fun get(index: Long): Boolean {
		val position = index / Int.SIZE_BITS
		if (position >= bitField.size) throw ArrayIndexOutOfBoundsException(
			"Index $index out of range (> ${(bitField.size * Int.SIZE_BITS) - 1})"
		)
		return (bitField[position.toInt()] and (1 shl ((index % Int.MAX_VALUE).toInt()))) != 0
	}
}