package personal.opensrcerer.bonkersmusic.ui.models

import android.content.Context
import personal.opensrcerer.bonkersmusic.db.ServerDbClient
import personal.opensrcerer.bonkersmusic.db.dto.SubsonicServer
import personal.opensrcerer.bonkersmusic.server.client.SubsonicService
import personal.opensrcerer.bonkersmusic.server.requests.system.PingRequest
import personal.opensrcerer.bonkersmusic.server.responses.subsonic.SubsonicResponse
import personal.opensrcerer.bonkersmusic.ui.dto.IngestionPageType
import reactor.util.retry.Retry
import java.time.Duration
import java.time.temporal.ChronoUnit

class ServerScreensModel {

    // --- Server Data ---
    val currServer: NullableStateable<SubsonicServer> = NullableStateable(null)
    val serverResponse: NullableStateable<SubsonicResponse> = NullableStateable(null)
    val serverReady = Stateable(false)

    // --- Server Ingestion Form ---
    val hostnameContent = Stateable("")
    val portContent = Stateable("")
    val usernameContent = Stateable("")
    val passwordContent = Stateable("")

    // --- Ingestion Page ---
    val pageType = Stateable(IngestionPageType.CONNECTING)

    // --- Interface With Forms ---
    fun dumpServerDataToForms() {
        if (currServer.value() == null) {
            return
        }

        val server = currServer.value()
        hostnameContent changeTo "${server?.host}"
        portContent changeTo "${server?.port}"
        usernameContent changeTo "${server?.username}"
        passwordContent changeTo ""
    }

    fun clearFormData() {
        hostnameContent changeTo ""
        portContent changeTo ""
        usernameContent changeTo ""
        passwordContent changeTo ""
    }

    // --- Interface With Database ---
    fun initServer(ctx: Context) {
        retrieveServer(ctx)
        if (currServer.value() != null) {
            testServerConnection()
        }
    }

    private fun retrieveServer(ctx: Context) {
        val dbClient = ServerDbClient(ctx, false)
        val server = dbClient.getServer()

        if (server != null) {
            currServer changeTo server
        }
        dbClient.close()
    }

    fun upsertServer(ctx: Context, newServer: SubsonicServer) {
        val dbClient = ServerDbClient(ctx, true)
        dbClient.replaceServer(newServer)
        currServer changeTo newServer
        dbClient.close()
    }

    fun removeServer(ctx: Context) {
        val dbClient = ServerDbClient(ctx, true)
        dbClient.removeServer()
        dbClient.close()
    }

    // --- Interface With Server ---
    fun testServerConnection() {
        if (currServer.value() == null) {
            return
        }

        SubsonicService
            .request(PingRequest())
            .delayElement(Duration.ofMillis(1000))
            .retryWhen(Retry.backoff(3, Duration.ofMillis(500)))
            .timeout(Duration.of(5000, ChronoUnit.MILLIS))
            .doOnError {
                pageType changeTo IngestionPageType.FAILED
            }
            .subscribe { res ->
                serverResponse changeTo res
                if (res.error != null) {
                    pageType changeTo IngestionPageType.FAILED
                    return@subscribe
                }
                serverReady changeTo true
            }
    }

    companion object {
        private var screensModel: ServerScreensModel? = null

        fun getScreenModel(): ServerScreensModel {
            if (screensModel == null) screensModel = ServerScreensModel()
            return screensModel!!
        }
    }
}