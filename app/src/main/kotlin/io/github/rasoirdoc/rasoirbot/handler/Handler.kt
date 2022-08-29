package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient
import kotlinx.coroutines.CoroutineScope

interface Handler {
    suspend fun CoroutineScope.handle(client: GatewayDiscordClient)
}