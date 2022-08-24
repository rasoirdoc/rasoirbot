package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.Event

interface EventHandler<T: Event> {
    suspend fun handle(event: T, client: GatewayDiscordClient)
}