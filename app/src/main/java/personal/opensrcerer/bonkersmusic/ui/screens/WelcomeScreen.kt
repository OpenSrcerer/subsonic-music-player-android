package personal.opensrcerer.bonkersmusic.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import personal.opensrcerer.bonkersmusic.R
import personal.opensrcerer.bonkersmusic.ui.dto.IngestionPageType
import personal.opensrcerer.bonkersmusic.ui.models.ServerScreensModel
import personal.opensrcerer.bonkersmusic.ui.theme.ButtonBlue
import personal.opensrcerer.bonkersmusic.ui.theme.DarkerDeepBlue
import personal.opensrcerer.bonkersmusic.ui.theme.LightRed
import personal.opensrcerer.bonkersmusic.ui.theme.TextWhite

@Composable
fun WelcomeScreen(
    navController: NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .background(DarkerDeepBlue)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to the Subsonic server interface!",
                color = TextWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 20.dp, end = 20.dp)
            )
            GlideImage(
                imageModel = R.raw.wave,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(350.dp)
                    .padding(bottom = 50.dp, end = 45.dp)
            )
            Text(
                text = "Get Started",
                color = TextWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        ServerScreensModel.getScreenModel().pageType changeTo IngestionPageType.LOGIN
                        navController.navigate("ingestion")
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 20.dp, horizontal = 15.dp)
            )
        }
    }
}