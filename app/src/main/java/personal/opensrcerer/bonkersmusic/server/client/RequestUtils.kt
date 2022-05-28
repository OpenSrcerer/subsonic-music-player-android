package personal.opensrcerer.bonkersmusic.server.client

import okhttp3.HttpUrl
import personal.opensrcerer.bonkersmusic.db.dto.SubsonicServer
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.SubsonicRequest
import personal.opensrcerer.bonkersmusic.ui.models.ServerScreensModel
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

object RequestUtils {
    private val charPool : List<Char> = ('a'..'z') + ('0'..'9')
    private val screenModel = ServerScreensModel.getScreenModel()

    fun <T> getUrl(
        req: SubsonicRequest<T>
    ): HttpUrl {
        val config: SubsonicServer = screenModel.currServer.value()!!
        val builder = HttpUrl.Builder()
            .scheme("http")
            .addPathSegment("rest")
            .addPathSegment(req.path.toString())
        addConfigParams(builder, config)
        req.queryParams.forEach { entry -> builder.addQueryParameter(entry.key, entry.value.toString()) }
        return builder.build()
    }

    private fun addConfigParams(
        builder: HttpUrl.Builder,
        config: SubsonicServer
    ) {
        builder.host(config.host)
            .port(config.port)
            .addQueryParameter("v", config.version)
            .addQueryParameter("c", "android-client")
        addCredentials(builder, config, true)
    }

    private fun addCredentials(
        builder: HttpUrl.Builder,
        config: SubsonicServer,
        legacy: Boolean
    ) {
        builder.addQueryParameter("u", config.username)

        if (legacy) {
            builder.addQueryParameter("p", config.password)
            return
        }

        val hashSalt = hashMd5(config.password)
        builder.addQueryParameter("t", hashSalt.first)
            .addQueryParameter("s", hashSalt.second)
    }

    private fun hashMd5(password: String): Pair<String, String> {
        val md = MessageDigest.getInstance("MD5")
        val salt = getNextSalt()
        val saltedPassword = "$password$salt"
        val hash = BigInteger(1, md.digest(
            saltedPassword.toByteArray())
        ).toString(16).padStart(32, '0')

        return Pair(hash, salt)
    }

    private fun getNextSalt(): String {
        return ThreadLocalRandom.current()
            .ints(20L, 0, charPool.size)
            .asSequence()
            .map(charPool::get)
            .joinToString("")
    }
}