package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient
import io.github.rasoirdoc.rasoirbot.usecase.SanityChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

class SanityCheckerHandler(private val sanityChecker: SanityChecker): Handler {
    override suspend fun CoroutineScope.handle(client: GatewayDiscordClient) {
        with(sanityChecker) {
            coroutineScope { performChecks() }
        }
    }
}