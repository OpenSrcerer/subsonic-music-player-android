/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.server.client

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.SubsonicRequest
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.VoidRequest
import personal.opensrcerer.bonkersmusic.server.responses.subsonic.SubsonicMonoCallback
import personal.opensrcerer.bonkersmusic.server.responses.subsonic.SubsonicResponse
import reactor.core.publisher.Mono
import java.util.concurrent.TimeUnit
import java.util.logging.Level

// Service to communicate with the Subsonic API
object  SubsonicService {
    private val loggerKey: String? = SubsonicService::class.simpleName
    private val client: OkHttpClient

    init {
        Log.d(loggerKey, "Initializing OkHttp singleton for subsonic servers...")
        client = OkHttpClient().newBuilder()
            .callTimeout(5000, TimeUnit.MILLISECONDS)
            .build()
    }

    // Make a request with a generic response
    fun request(req: VoidRequest): Mono<SubsonicResponse> {
        return Mono.create<SubsonicResponse> { monoSink ->
            client.newCall(
                Request.Builder()
                    .url(RequestUtils.getUrl(req))
                    .build()
            ).enqueue(SubsonicMonoCallback(monoSink, SubsonicResponse::class.java)) }
            .log("Subsonic Requests", Level.INFO)
    }

    // Make a request with a specific response
    fun <T> request(req: SubsonicRequest<T>): Mono<T>
    where T : SubsonicResponse {
        return Mono.create<T> { monoSink ->
            client.newCall(
                Request.Builder()
                    .url(RequestUtils.getUrl(req))
                    .build()
            ).enqueue(SubsonicMonoCallback(monoSink, req.getClazz())) }
            .log("Subsonic Requests", Level.INFO)
    }

    fun shutdown() {
        client.dispatcher.executorService.shutdown()
        client.connectionPool.evictAll()
        client.cache?.close()
    }
}