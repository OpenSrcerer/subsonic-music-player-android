package personal.opensrcerer.bonkersmusic.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import personal.opensrcerer.bonkersmusic.R
import personal.opensrcerer.bonkersmusic.ui.dto.BottomBarButton
import personal.opensrcerer.bonkersmusic.ui.theme.AquaBlue
import personal.opensrcerer.bonkersmusic.ui.theme.ButtonBlue
import personal.opensrcerer.bonkersmusic.ui.theme.DarkerDeepBlue

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    navigator: NavController
) {
    val backStackEntry by navigator.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(DarkerDeepBlue)
            .padding(15.dp)
    ) {
        bottomMenuItems().forEach { item ->
            BottomBarItem(
                item = item,
                isSelected = currentRoute.equals(item.navRoute),
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor
            ) {
                navigator.navigate(item.navRoute)
            }
        }
    }
}

@Composable
fun BottomBarItem(
    item: BottomBarButton,
    isSelected: Boolean = false,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable {
                onItemClick()
            }
            .clip(RoundedCornerShape(10.dp))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                tint = if (isSelected) activeHighlightColor else inactiveTextColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = item.title,
            color = if (isSelected) activeTextColor else inactiveTextColor
        )
    }
}

fun bottomMenuItems(): List<BottomBarButton> {
    return listOf(
        BottomBarButton("Home", R.drawable.ic_home, "home"),
        BottomBarButton("Browse", R.drawable.ic_search, "browse"),
        BottomBarButton("Explore", R.drawable.ic_moon, "explore"),
        BottomBarButton("Server", R.drawable.ic_music, "server")
    )
}