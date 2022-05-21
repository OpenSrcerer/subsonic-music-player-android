package personal.opensrcerer.bonkersmusic.server.client

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import personal.opensrcerer.bonkersmusic.server.requests.RequestUtils
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.SubsonicRequest
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.VoidRequest
import personal.opensrcerer.bonkersmusic.server.responses.subsonic.SubsonicMonoCallback
import personal.opensrcerer.bonkersmusic.server.responses.subsonic.SubsonicResponse
import reactor.core.publisher.Mono
import java.util.concurrent.TimeUnit
import java.util.logging.Level

object SubsonicService {
    private val loggerKey: String? = SubsonicService::class.simpleName
    private val client: OkHttpClient

    init {
        Log.d(loggerKey, "Initializing OkHttp singleton for subsonic servers...")
        client = OkHttpClient().newBuilder()
            .callTimeout(5000, TimeUnit.MILLISECONDS)
            .build()
    }

    fun request(req: VoidRequest): Mono<SubsonicResponse> {
        return Mono.create<SubsonicResponse> { monoSink ->
            client.newCall(
                Request.Builder()
                    .url(RequestUtils.getUrl(req))
                    .build()
            ).enqueue(SubsonicMonoCallback(monoSink, SubsonicResponse::class.java)) }
            .log("Subsonic Requests", Level.INFO)
    }

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