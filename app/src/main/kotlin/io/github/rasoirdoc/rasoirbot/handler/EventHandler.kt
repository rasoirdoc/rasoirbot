package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.Event
import kotlinx.coroutines.CoroutineScope

interface EventHandler<T: Event> {
    suspend fun CoroutineScope.handle(event: T, client: GatewayDiscordClient)
}