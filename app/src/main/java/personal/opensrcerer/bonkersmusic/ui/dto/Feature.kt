package personal.opensrcerer.bonkersmusic.ui.dto

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Feature(
    val title: String,
    @DrawableRes val iconId: Int,
    val lightColor: Color = Color.Transparent,
    val mediumColor: Color = Color.Transparent,
    val darkColor: Color = Color.Transparent
)
