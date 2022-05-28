package personal.opensrcerer.bonkersmusic.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import personal.opensrcerer.bonkersmusic.R
import personal.opensrcerer.bonkersmusic.ui.theme.DarkerDeepBlue
import personal.opensrcerer.bonkersmusic.ui.theme.TextWhite

@Composable
fun WelcomeScreen() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxHeight()
            .background(DarkerDeepBlue)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Welcome!",
                color = TextWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            )
            GlideImage(
                imageModel = R.raw.loading,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(250.dp)
            )
        }
    }
}