package personal.opensrcerer.bonkersmusic.server.db.dto

data class SubsonicServer(
    val id: Int = 1,
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val version: String
)