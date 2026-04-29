package org.bread_experts_group.breadmod_advanced.preload_agent

import java.lang.instrument.Instrumentation

class Agent {
	companion object {
		@JvmStatic
		fun premain(agentArgs: String?, instrumentation: Instrumentation) {
			println("THE ADVANCED AGENT HAS LOADED")
		}
	}
}