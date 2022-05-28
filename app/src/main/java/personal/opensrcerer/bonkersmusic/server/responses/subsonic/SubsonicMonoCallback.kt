/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.server.responses.subsonic

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.simpleframework.xml.core.Persister
import reactor.core.publisher.MonoSink
import java.io.IOException
import java.io.Reader
import java.io.StringReader
import java.time.Instant
import java.time.temporal.ChronoUnit

// HTTP callback to be called by OkHttp asynchronously
// In order to parse the XML response by the Subsonic API
class SubsonicMonoCallback<T>(
    private val sink: MonoSink<T>,
    private val clazz: Class<out T>
    ) : Callback
    where T : SubsonicResponse {
    override fun onResponse(call: Call, response: Response) {
        val body = response.body?.string()

        println(body)

        if (body.isNullOrEmpty()) {
            onFailure(call, IOException("Response body sent by the Subsonic Server was null."))
            return
        }

        try {
            val res = parse(clazz, body)
            res.setTime(ChronoUnit.MILLIS.between(
                Instant.ofEpochMilli(response.sentRequestAtMillis),
                Instant.ofEpochMilli(response.receivedResponseAtMillis)
            ))
            sink.success(res)
        } catch (e: Exception) {
            onFailure(call, IOException(e.message))
        }
    }

    override fun onFailure(call: Call, e: IOException) {
        sink.error(e)
    }

    private companion object {
        val mapper = Persister()

        fun <T> parse(
            clazz: Class<T>,
            xml: String
        ): T {
            val xmlReader: Reader = StringReader(xml)
            return mapper.read(
                clazz,
                xmlReader,
                false
            )
        }
    }
}