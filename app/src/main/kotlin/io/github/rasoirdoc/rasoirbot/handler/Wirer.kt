package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import reactor.core.publisher.Mono
import kotlin.reflect.KClass
import kotlin.time.Duration

object Wirer {
    private val logger = KotlinLogging.logger {}

    fun delay(handler: Handler, delay: Duration, client: GatewayDiscordClient): Mono<Unit> {
        return mono { delay(delay)
            logger.info { "Triggering handler '${handler::class.simpleName}' on '$delay' delay" }
            handler.handle(client) }
    }

    fun repeat(handler: Handler, delay: Duration, client: GatewayDiscordClient): Mono<Unit> {
        return mono { while (isActive) {
            delay(delay)
            logger.info { "Triggering handler '${handler::class.simpleName}' on repeated '$delay' delay" }
            handler.handle(client) } }
    }

    fun <T: Event>onEvent(clazz: KClass<T>, handler: EventHandler<T>, client: GatewayDiscordClient): Mono<Unit> {
        return mono { client.on(clazz.java).asFlow().onEach { e ->
            logger.info { "Triggering handler '${handler::class.simpleName}' on '${clazz.simpleName}' event" }
            handler.handle(e, client) }.collect() }
    }
}