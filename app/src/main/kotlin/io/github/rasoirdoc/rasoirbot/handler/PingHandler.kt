package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.reactive.asFlow

class PingHandler: EventHandler<MessageCreateEvent> {
    override suspend fun CoroutineScope.handle(event: MessageCreateEvent, client: GatewayDiscordClient) {
        val message = event.message
        if (message.content == "!ping") {
            val channel = message.channel.asFlow().first()
            channel.createMessage("Pong!").asFlow().first()
        }
    }
}