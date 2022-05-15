package personal.opensrcerer.bonkersmusic.ui.dto

import androidx.annotation.DrawableRes

data class BottomBarButton(
    val title: String,
    @DrawableRes val iconId: Int,
    val navRoute: String
)
