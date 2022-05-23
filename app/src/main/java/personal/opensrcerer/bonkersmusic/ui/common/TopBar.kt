package personal.opensrcerer.bonkersmusic.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import personal.opensrcerer.bonkersmusic.ui.theme.DarkerDeepBlue

@Composable
fun TopBar(
    navigator: NavController
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkerDeepBlue)
            .padding(15.dp)
    ) {
        Column {
            Text(
                text = "< Back",
                style = MaterialTheme.typography.h2,
                modifier = Modifier
                    .clickable {
                        navigator.navigate("home")
                    }
            )
        }
    }
}