/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import personal.opensrcerer.bonkersmusic.audio.AudioPlayerService
import personal.opensrcerer.bonkersmusic.ui.models.BrowseScreenModel
import personal.opensrcerer.bonkersmusic.ui.models.HomeScreenModel
import personal.opensrcerer.bonkersmusic.ui.models.ServerScreensModel
import personal.opensrcerer.bonkersmusic.ui.screens.*
import personal.opensrcerer.bonkersmusic.ui.theme.BonkersMusicClientTheme
import java.lang.Math.sqrt
import java.util.*

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the sensor manager
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        Objects.requireNonNull(sensorManager)
            .registerListener(sensorListener, sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)

        // Init composables
        setContent {
            BonkersMusicClientTheme {
                // Variable home destination depending on server
                val navController = rememberNavController()
                val serverScreenModel = ServerScreensModel.getScreenModel()
                var startDestination = "ingestion"

                serverScreenModel.initServer(this)
                if (serverScreenModel.currServer.value() == null) {
                    startDestination = "welcome"
                }

                // Create all routres for navigation
                NavHost(navController, startDestination) {
                    composable(route = "home") {
                        HomeScreen(navController)
                    }
                    composable(route = "browse") {
                        val model = BrowseScreenModel.getBrowseModel()
                        BrowseScreen(
                            navController,
                            model,
                            model.currPageType.value()
                        )
                    }
                    composable(route = "server") {
                        ServerScreen(navController)
                    }
                    composable(route = "ingestion") {
                        ServerIngestionScreen(navController = navController)
                    }
                    composable(route = "welcome") {
                        WelcomeScreen(navController)
                    }
                }
            }
        }
    }

    // Extension function to make Navigation Graphs for composables
    private fun NavGraphBuilder.composable(
        route: String,
        arguments: List<NamedNavArgument> = emptyList(),
        deepLinks: List<NavDeepLink> = emptyList(),
        content: @Composable (NavBackStackEntry) -> Unit
    ) {
        addDestination(
            ComposeNavigator.Destination(provider[ComposeNavigator::class], content).apply {
                this.route = route
                arguments.forEach { (argumentName, argument) ->
                    addArgument(argumentName, argument)
                }
                deepLinks.forEach { deepLink ->
                    addDeepLink(deepLink)
                }
            }
        )
    }

    // Method for sensor listening to accelerometer :)
    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(sensEvent: SensorEvent) {

            var currAccel = HomeScreenModel.getHomeModel().currAccel.value()
            val lastAccel = currAccel

            // Accelerometer values
            val x = sensEvent.values[0]
            val y = sensEvent.values[1]
            val z = sensEvent.values[2]

            // Get the accelerations
            currAccel = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currAccel - lastAccel
            val composedAcceleration = currAccel * 0.9f + delta

            HomeScreenModel.getHomeModel().currAccel changeTo currAccel

            // Detect a shake, stop or start!
            if (composedAcceleration > 12) {
                AudioPlayerService.togglePause()
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }
}