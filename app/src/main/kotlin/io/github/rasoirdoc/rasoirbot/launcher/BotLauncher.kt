package io.github.rasoirdoc.rasoirbot.launcher

import discord4j.core.DiscordClient
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.gateway.intent.Intent
import discord4j.gateway.intent.IntentSet
import io.github.rasoirdoc.rasoirbot.handler.EchoHandler
import io.github.rasoirdoc.rasoirbot.handler.PingHandler
import io.github.rasoirdoc.rasoirbot.handler.ReadyHandler
import io.github.rasoirdoc.rasoirbot.handler.Wirer
import reactor.core.publisher.Mono
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main() {
    DiscordClient.create(Environment.botToken).gateway()
        .setEnabledIntents(IntentSet.nonPrivileged().or(IntentSet.of(Intent.GUILD_MEMBERS)))
        .withGateway { c ->
            Mono.`when`(
                Wirer.onEvent(MessageCreateEvent::class, PingHandler(), c),
                Wirer.onEvent(ReadyEvent::class, ReadyHandler(Environment.alwaysAdmins), c),
                Wirer.repeat(EchoHandler(Environment.alwaysAdmins), 30.toDuration(DurationUnit.SECONDS), c),
                Wirer.delay(EchoHandler(Environment.alwaysAdmins), 10.toDuration(DurationUnit.SECONDS), c)
            )
        }
        .block()
}
