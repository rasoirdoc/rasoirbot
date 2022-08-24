Rasoir Bot
==========

> "Ce bot est d'un ennui"

_Albert Einstein_

This bot performs various utility tasks on the Rasoir d'Oc' discord server.

Build
-----

    ./gradlew build

Run
---

    DISCORD_BOT_TOKEN=<bot token> ALWAYS_ADMINS=<comma separated snowflakes ids of admins> java -jar app/build/libs/app-all.jar

    # Example
    DISCORD_BOT_TOKEN=123abc ALWAYS_ADMINS=1234,5678 java -jar app/build/libs/app-all.jar
