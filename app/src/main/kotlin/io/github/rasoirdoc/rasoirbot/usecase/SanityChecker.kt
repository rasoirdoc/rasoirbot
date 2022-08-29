package io.github.rasoirdoc.rasoirbot.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import mu.KotlinLogging

class SanityChecker(private val autoAdminGranter: AutoAdminGranter) {
    private val logger = KotlinLogging.logger {}

    suspend fun CoroutineScope.performChecks() {
/*        val guild = client.guilds.collectList().asFlow().first()
        logger.info { "Last guild is ${guild.last().name}" }

        val members = guild.last().members.collectList().asFlow().first()
        logger.info { "Last member is ${members.last().tag}" }
        members.filter { hardCodedAdmins.contains(it.id.asString()) }.forEach {
            logger.info("${it.tag} is an admin \\o/")
            val privateChannel = it.privateChannel.asFlow().first()
            privateChannel.createMessage("Hello admin friend :)").asFlow().first()
        }*/

        logger.debug { "Performing sanity checks..." }
        with(autoAdminGranter) {
            coroutineScope { grantAdmins() }
        }
    }
}