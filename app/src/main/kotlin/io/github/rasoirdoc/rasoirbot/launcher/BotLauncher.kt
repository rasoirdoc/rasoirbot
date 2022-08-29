package io.github.rasoirdoc.rasoirbot.launcher

import discord4j.common.util.Snowflake
import discord4j.core.DiscordClient
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.gateway.intent.Intent
import discord4j.gateway.intent.IntentSet
import io.github.rasoirdoc.rasoirbot.handler.PingHandler
import io.github.rasoirdoc.rasoirbot.handler.SanityCheckerHandler
import io.github.rasoirdoc.rasoirbot.handler.SanityCheckerReadyHandler
import io.github.rasoirdoc.rasoirbot.handler.Wirer
import io.github.rasoirdoc.rasoirbot.usecase.AutoAdminGranter
import io.github.rasoirdoc.rasoirbot.usecase.SanityChecker
import reactor.core.publisher.Mono
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main() {
    DiscordClient.create(Environment.botToken).gateway()
        .setEnabledIntents(IntentSet.nonPrivileged().or(IntentSet.of(Intent.GUILD_MEMBERS)))
        .withGateway { c ->
            // Initializing use cases
            val autoAdminGranter = AutoAdminGranter(c, Environment.alwaysAdmins.map { Snowflake.of(it) }, Snowflake.of(Environment.operatingGuild))
            val sanityChecker = SanityChecker(autoAdminGranter)

            // Wiring handlers
            Mono.`when`(
                Wirer.onEvent(MessageCreateEvent::class, PingHandler(), c),
                // The following event seems not to be trigger every time, maybe a race condition of the server: https://github.com/Discord4J/Discord4J/issues/810
                Wirer.onEvent(ReadyEvent::class, SanityCheckerReadyHandler(sanityChecker), c),
                Wirer.repeat(SanityCheckerHandler(sanityChecker), 30.toDuration(DurationUnit.SECONDS), c),
                Wirer.delay(SanityCheckerHandler(sanityChecker), 10.toDuration(DurationUnit.SECONDS), c)
            )
        }
        .block()
}
