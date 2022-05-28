/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

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
import personal.opensrcerer.bonkersmusic.ui.models.BrowseScreenModel
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

@Composable
fun TopBar(
    navigator: NavController,
    model: BrowseScreenModel
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
                        if (model.currChildPage() != null) {
                            model.upDir()
                            return@clickable
                        }
                        navigator.navigate("home")
                    }
            )
        }
    }
}