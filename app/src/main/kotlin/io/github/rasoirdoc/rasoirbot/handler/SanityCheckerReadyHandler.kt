package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.lifecycle.ReadyEvent
import io.github.rasoirdoc.rasoirbot.usecase.SanityChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

class SanityCheckerReadyHandler(private val sanityChecker: SanityChecker) : EventHandler<ReadyEvent> {
    override suspend fun CoroutineScope.handle(event: ReadyEvent, client: GatewayDiscordClient) {
        with(sanityChecker) {
            coroutineScope { performChecks() }
        }
    }
}
