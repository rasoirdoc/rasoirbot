package io.github.rasoirdoc.rasoirbot.launcher

object Environment {
    val botToken: String = System.getenv("DISCORD_BOT_TOKEN") ?: throw IllegalArgumentException("Please set the \"DISCORD_BOT_TOKEN\" environment variable.")
    val alwaysAdmins: List<String> = (System.getenv("ALWAYS_ADMINS") ?: throw IllegalArgumentException("Please set the \"ALWAYS_ADMINS\" environment variable.")).split(",")
}