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
import personal.opensrcerer.bonkersmusic.R
import personal.opensrcerer.bonkersmusic.ui.dto.BottomMenuButton
import personal.opensrcerer.bonkersmusic.ui.theme.AquaBlue
import personal.opensrcerer.bonkersmusic.ui.theme.ButtonBlue
import personal.opensrcerer.bonkersmusic.ui.theme.DeepBlue

@Composable
fun BottomMenu(
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    initialSelectedItemIndex: Int = 0,
    navigator: NavController
) {
    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(DeepBlue)
            .padding(15.dp)
    ) {
        BottomMenuItems(navigator).forEachIndexed { index, item ->
            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor
            ) {
                selectedItemIndex = index
            }
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuButton,
    isSelected: Boolean = false,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            onItemClick()
            item.navAction()
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) activeHighlightColor else Color.Transparent)
        ) {
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

fun BottomMenuItems(navigator: NavController): List<BottomMenuButton> {
    return listOf(
        BottomMenuButton("Home", R.drawable.ic_home) { navigator.navigate("home") },
        BottomMenuButton("Browse", R.drawable.ic_bubble) { navigator.navigate("browse") },
        BottomMenuButton("music", R.drawable.ic_moon) { navigator.navigate("home") },
        BottomMenuButton("life", R.drawable.ic_music) { navigator.navigate("home") },
        BottomMenuButton("nothing", R.drawable.ic_profile) { navigator.navigate("home") },
    )
}