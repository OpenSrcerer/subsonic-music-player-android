package personal.opensrcerer.bonkersmusic.ui.dto

import androidx.annotation.DrawableRes

data class BottomMenuButton(
    val title: String,
    @DrawableRes val iconId: Int,
    val navAction: () -> Unit
)
