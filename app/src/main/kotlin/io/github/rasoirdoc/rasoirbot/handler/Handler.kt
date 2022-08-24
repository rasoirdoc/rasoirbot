package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient

interface Handler {
    suspend fun handle(client: GatewayDiscordClient)
}