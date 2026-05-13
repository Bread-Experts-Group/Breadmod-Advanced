package org.bread_experts_group.breadmod_advanced.system_native

import java.lang.foreign.MemorySegment

interface SegmentExposed {
	val segment: MemorySegment
}