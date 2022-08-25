package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.Event
import mu.KotlinLogging
import reactor.core.publisher.Mono
import kotlin.reflect.KClass
import kotlin.time.Duration

object Wirer {
    private val logger = KotlinLogging.logger {}

    // I'd rather use these utility methods instead of those in EventHandler and Handler, but I don't know how to 'bind'
    // the CoroutineScope in 'this' from here.
    /*fun delay(handler: Handler, delay: Duration, client: GatewayDiscordClient): Mono<Unit> {
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
    }*/

    // We can still use these methods, but they are less interesting
    fun delay(handler: Handler, delay: Duration, client: GatewayDiscordClient): Mono<Unit> {
        return handler.atDelay(delay, client)
    }

    fun repeat(handler: Handler, delay: Duration, client: GatewayDiscordClient): Mono<Unit> {
        return handler.every(delay, client)
    }

    fun <T: Event>onEvent(clazz: KClass<T>, handler: EventHandler<T>, client: GatewayDiscordClient): Mono<Unit> {
        return handler.on(clazz, client)
    }
}