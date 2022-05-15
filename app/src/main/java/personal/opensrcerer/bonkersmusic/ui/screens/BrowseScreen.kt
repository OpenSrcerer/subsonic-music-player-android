package personal.opensrcerer.bonkersmusic.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import personal.opensrcerer.bonkersmusic.ui.common.BottomBar
import personal.opensrcerer.bonkersmusic.ui.common.TopBar

@ExperimentalFoundationApi
@Composable
fun BrowseScreen(navigator: NavController) {
    Scaffold(
        topBar = { TopBar(navigator = navigator) },
        content = { },
        bottomBar = { BottomBar(navigator = navigator) }
    )
}