package personal.opensrcerer.bonkersmusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import personal.opensrcerer.bonkersmusic.ui.dto.ServerIngestionFlow
import personal.opensrcerer.bonkersmusic.ui.models.BrowseScreenModel
import personal.opensrcerer.bonkersmusic.ui.models.ServerScreensModel
import personal.opensrcerer.bonkersmusic.ui.screens.*
import personal.opensrcerer.bonkersmusic.ui.theme.BonkersMusicClientTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BonkersMusicClientTheme {
                val navController = rememberNavController()
                val serverScreenModel = ServerScreensModel.getScreenModel()
                var startDestination = "loading"

                serverScreenModel.initServer(this, navController)
                if (serverScreenModel.currServer.value() == null) {
                    startDestination = "server"
                }

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
                    composable(route = "explore") {
                        ExploreScreen(navController)
                    }
                    composable(route = "server") {
                        ServerScreen(navController)
                    }
                    composable(route = "loading") {
                        ServerIngestionScreen(
                            flow = ServerIngestionFlow.LOGIN
                        )
                    }
                    composable(route = "welcome") {
                        WelcomeScreen()
                    }
                }
            }
        }
    }

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
}