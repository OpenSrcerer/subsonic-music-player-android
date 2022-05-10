package personal.opensrcerer.bonkersmusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import personal.opensrcerer.bonkersmusic.ui.HomeScreen
import personal.opensrcerer.bonkersmusic.ui.theme.BonkersMusicClientTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BonkersMusicClientTheme {
                HomeScreen()
            }
        }
    }
}