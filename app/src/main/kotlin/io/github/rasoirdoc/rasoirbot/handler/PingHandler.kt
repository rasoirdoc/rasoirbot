package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import kotlinx.coroutines.reactive.awaitSingle

class PingHandler: EventHandler<MessageCreateEvent> {
    override suspend fun handle(event: MessageCreateEvent, client: GatewayDiscordClient) {
        val message = event.message
        if (message.content == "!ping") {
            val channel = message.channel.awaitSingle()
            channel.createMessage("Pong!").awaitSingle()
        }
    }
}