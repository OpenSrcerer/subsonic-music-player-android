package personal.opensrcerer.bonkersmusic.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import personal.opensrcerer.bonkersmusic.R
import personal.opensrcerer.bonkersmusic.ui.common.BottomBar
import personal.opensrcerer.bonkersmusic.ui.common.TopBar
import personal.opensrcerer.bonkersmusic.ui.theme.DeepBlue

@Composable
fun ServerScreen(navigator: NavController) {

    Scaffold(
        topBar = { TopBar(navigator = navigator) },
        content = {
            Box(
                modifier = Modifier
                    .background(DeepBlue)
                    .fillMaxSize()
            ) {
                GreetingSection()
            }
        },
        bottomBar = { BottomBar(navigator = navigator) }
    )
}

@Composable
fun GreetingSection(
    name: String = "Bonkers"
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to \$servername, $name",
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "\$serverdescription",
                style = MaterialTheme.typography.body1
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}