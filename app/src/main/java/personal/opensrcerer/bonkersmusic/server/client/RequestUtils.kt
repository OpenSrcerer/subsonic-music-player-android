/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.server.client

import okhttp3.HttpUrl
import personal.opensrcerer.bonkersmusic.db.dto.SubsonicServer
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.SubsonicRequest
import personal.opensrcerer.bonkersmusic.server.responses.subsonic.ApiVersion
import personal.opensrcerer.bonkersmusic.ui.models.ServerScreensModel
import java.security.MessageDigest
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

object RequestUtils {
    private val legacyApiVersion = ApiVersion(1, 12, 0)
    private val charPool : List<Char> = ('a'..'z') + ('0'..'9')
    private val screenModel = ServerScreensModel.getScreenModel()

    fun <T> getUrl(
        req: SubsonicRequest<T>
    ): HttpUrl {
        val config: SubsonicServer = screenModel.currServer.value()!!
        val builder = HttpUrl.Builder()
            .scheme(config.url.protocol)
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
        builder.host(config.url.host)
            .port(config.url.port)
            .addQueryParameter("v", config.version)
            .addQueryParameter("c", "android-client")
        val legacy = ApiVersion.parse(config.version) <= legacyApiVersion
        addCredentials(builder, config, legacy)
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
        val hash = md.digest(
            saltedPassword.toByteArray()).toHex()
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

fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }