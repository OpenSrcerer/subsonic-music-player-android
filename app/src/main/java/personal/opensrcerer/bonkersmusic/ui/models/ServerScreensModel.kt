package personal.opensrcerer.bonkersmusic.ui.models

import android.content.Context
import androidx.navigation.NavController
import personal.opensrcerer.bonkersmusic.db.ServerDbClient
import personal.opensrcerer.bonkersmusic.db.dto.SubsonicServer
import personal.opensrcerer.bonkersmusic.server.client.SubsonicService
import personal.opensrcerer.bonkersmusic.server.requests.system.PingRequest
import personal.opensrcerer.bonkersmusic.server.responses.subsonic.SubsonicResponse
import personal.opensrcerer.bonkersmusic.ui.dto.ServerField
import reactor.util.retry.Retry
import java.time.Duration
import java.time.temporal.ChronoUnit

class ServerScreensModel {

    // --- Server Data ---
    val currServer: NullableStateable<SubsonicServer> = NullableStateable(null)
    val serverResponse: NullableStateable<SubsonicResponse> = NullableStateable(null)
    val serverError = Stateable(false)

    // --- Server Ingestion Form ---
    val hostnameContent = Stateable("")
    val portContent = Stateable("")
    val usernameContent = Stateable("")
    val passwordContent = Stateable("")

    // --- Interface With Database ---
    fun initServer(ctx: Context, navController: NavController) {
        retrieveServer(ctx)
        if (currServer.value() != null) {
            testServerConn(navController)
        }
    }

    fun retrieveServer(ctx: Context) {
        val dbClient = ServerDbClient(ctx, false)
        val server = dbClient.getServer()

        if (server != null) {
            currServer changeTo server
        }
    }

    fun upsertServer(ctx: Context, newServer: SubsonicServer) {
        val dbClient = ServerDbClient(ctx, true)
        val server = dbClient.getServer()

        if (server != null) {
            currServer changeTo server
        }
    }

    // --- Interface With Server ---
    private fun testServerConn(navController: NavController) {
        if (currServer.value() == null) {
            return
        }

        SubsonicService
            .request(PingRequest())
            .retryWhen(Retry.backoff(3, Duration.ofMillis(500)))
            .timeout(Duration.of(5000, ChronoUnit.MILLIS))
            .doOnError { serverError changeTo true }
            .subscribe { res ->
                serverError changeTo false
                serverResponse changeTo res
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