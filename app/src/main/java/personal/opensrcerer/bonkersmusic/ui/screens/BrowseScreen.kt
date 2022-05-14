package personal.opensrcerer.bonkersmusic.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import personal.opensrcerer.bonkersmusic.ui.common.BottomMenu

@Composable
fun BrowseScreen(navigator: NavController) {
    BottomMenu(
        modifier = Modifier,
        navigator = navigator
    )
}