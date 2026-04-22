package org.bread_experts_group.breadmodadvanced.preload_agent

import java.lang.instrument.Instrumentation

class Agent {
	companion object {
		@JvmStatic
		fun premain(agentArgs: String?, instrumentation: Instrumentation) {
			println("THE ADVANCED AGENT HAS LOADED")
		}
	}
}