package io.github.rasoirdoc.rasoirbot.handler

import discord4j.core.GatewayDiscordClient
import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging

class EchoHandler(private val hardCodedAdmins: List<String>): Handler {
    private val logger = KotlinLogging.logger {}

    override suspend fun handle(client: GatewayDiscordClient) {
        val guild = client.guilds.collectList().awaitSingle()
        logger.info { "Last guild is ${guild.last().name}" }

        val members = guild.last().members.collectList().awaitSingle()
        logger.info { "Last member is ${members.last().tag}" }
        members.filter { hardCodedAdmins.contains(it.id.asString()) }.forEach {
            logger.info("${it.tag} is an admin \\o/")
//            val privateChannel = it.privateChannel.awaitSingle()
//            privateChannel.createMessage("Hello admin friend :)").awaitSingle()
        }
    }
}