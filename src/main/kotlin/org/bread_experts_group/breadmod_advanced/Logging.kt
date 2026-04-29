package org.bread_experts_group.breadmod_advanced

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

private val logger: Logger = LogManager.getLogger("BreadModAdvanced Catch-All")

fun println(a: Any?): Unit = logger.info(a)
fun println(): Unit = logger.info("")