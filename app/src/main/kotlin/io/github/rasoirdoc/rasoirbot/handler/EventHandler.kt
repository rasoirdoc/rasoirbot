package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import reactor.core.publisher.Mono
import kotlin.reflect.KClass

interface EventHandler<T: Event> {
    suspend fun CoroutineScope.handle(event: T, client: GatewayDiscordClient)

    fun on(clazz: KClass<T>, client: GatewayDiscordClient): Mono<Unit> {
        val logger = KotlinLogging.logger {}

        return mono { client.on(clazz.java).asFlow().onEach { e ->
            logger.info { "Triggering handler '${this::class.simpleName}' on '${clazz.simpleName}' event" }
            handle(e, client) }.collect() }
    }
}