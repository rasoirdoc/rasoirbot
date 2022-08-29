package io.github.rasoirdoc.rasoirbot.usecase

import discord4j.common.util.Snowflake
import discord4j.core.GatewayDiscordClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asFlow
import mu.KotlinLogging

class AutoAdminGranter(private val client: GatewayDiscordClient, private val admins: List<Snowflake>, private val guildId: Snowflake) {
    private val logger = KotlinLogging.logger {}

    suspend fun CoroutineScope.grantAdmins() {
        logger.debug { "Verifying roles of fixed perpetual admin users..." }
        val adminRole = client.getGuildRoles(guildId).asFlow().firstOrNull { it.name == "admin" } ?: throw NoSuchElementException("Cannot find admin role here :(")
        val expectedAdminMembers = client.requestMembers(guildId).asFlow().filter { admins.contains(it.id) }.toList()
        val missingAdmins = expectedAdminMembers.filter { user -> user.roles.asFlow().map { role -> role.name }.toList().none { it == adminRole.name } }

        if (missingAdmins.isNotEmpty()) {
            logger.warn {
                "Found ${missingAdmins.joinToString(", ", "\"", "\"") { it.tag }} without admin roles, fixing this..."
            }
            missingAdmins.forEach { it.addRole(adminRole.id).asFlow().collect() }
        }
    }
}