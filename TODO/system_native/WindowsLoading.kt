package org.bread_experts_group.breadmod_advanced.system_native

import org.bread_experts_group.ffi.*
import org.bread_experts_group.ffi.windows.*
import java.lang.foreign.MemorySegment

object WindowsLoading {
	val k32_loadLibraryExW: (library: String, flags: Int) -> MemorySegment
	val k32_getProcAddress: (library: MemorySegment, procedure: String) -> MemorySegment

	init {
		val k32 = autoArena.getLookup("Kernel32.dll")!!
		val ll = k32.getDowncall(
			nativeLinker, "LoadLibraryExW",
			arrayOf(
				HMODULE,
				LPCWSTR.withName("lpLibFilename"),
				HANDLE.withName("hFile"),
				DWORD.withName("dwFlags")
			),
			listOf(gleCapture)
		)!!
		this.k32_loadLibraryExW = { library, flags ->
			val library = ll.invokeExact(
				capturedStateSegment,
				autoArena.allocateFrom(library, Charsets.UTF_16LE),
				MemorySegment.NULL,
				flags
			) as MemorySegment
			if (library == MemorySegment.NULL) throwLastError()
			library
		}
		val gpa = k32.getDowncall(
			nativeLinker, "GetProcAddress",
			arrayOf(
				FARPROC,
				HMODULE.withName("hModule"),
				LPCSTR.withName("lpProcName")
			),
			listOf(gleCapture)
		)!!
		this.k32_getProcAddress = { library, procedure ->
			val addr = gpa.invokeExact(
				capturedStateSegment,
				library,
				autoArena.allocateFrom(procedure, Charsets.US_ASCII)
			) as MemorySegment
			if (addr == MemorySegment.NULL) throwLastError()
			addr
		}
	}
}