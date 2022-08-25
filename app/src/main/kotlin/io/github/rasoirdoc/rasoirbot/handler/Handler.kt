package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import reactor.core.publisher.Mono
import kotlin.time.Duration

interface Handler {
    suspend fun CoroutineScope.handle(client: GatewayDiscordClient)

    fun atDelay(delay: Duration, client: GatewayDiscordClient): Mono<Unit> {
        val logger = KotlinLogging.logger {}

        return mono { delay(delay)
            logger.info { "Triggering handler '${this::class.simpleName}' on '$delay' delay" }
            handle(client) }
    }

    fun every(delay: Duration, client: GatewayDiscordClient): Mono<Unit> {
        val logger = KotlinLogging.logger {}

        return mono { while (isActive) {
            delay(delay)
            logger.info { "Triggering handler '${this::class.simpleName}' on repeated '$delay' delay" }
            handle(client) } }
    }
}