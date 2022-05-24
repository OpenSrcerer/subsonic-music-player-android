package personal.opensrcerer.bonkersmusic.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import personal.opensrcerer.bonkersmusic.R
import personal.opensrcerer.bonkersmusic.audio.AudioPlayerService
import personal.opensrcerer.bonkersmusic.server.requests.RequestUtils
import personal.opensrcerer.bonkersmusic.server.requests.media.StreamRequest
import personal.opensrcerer.bonkersmusic.ui.common.BottomBar
import personal.opensrcerer.bonkersmusic.ui.dto.Feature
import personal.opensrcerer.bonkersmusic.ui.models.HomeScreenModel
import personal.opensrcerer.bonkersmusic.ui.theme.BlueViolet1
import personal.opensrcerer.bonkersmusic.ui.theme.BlueViolet2
import personal.opensrcerer.bonkersmusic.ui.theme.BlueViolet3
import personal.opensrcerer.bonkersmusic.ui.theme.DeepBlue
import personal.opensrcerer.bonkersmusic.ui.util.Scheduler
import personal.opensrcerer.bonkersmusic.ui.util.standardQuadFromTo

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    navigator: NavController,
    model: HomeScreenModel = HomeScreenModel.getHomeModel()
) {
    Scaffold(
        topBar = { },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DeepBlue)
            )
            Column {
                CurrentlyPlayingCover(
                    Feature(
                        title = "What Would You Have Me Do?",
                        R.drawable.ic_headphone,
                        BlueViolet1,
                        BlueViolet2,
                        BlueViolet3
                    )
                )
                CurrentlyPlayingText(
                    "Lie",
                    "Awake",
                    "Dream Theater"
                )
                SliderControls(model)
                ButtonControls(
                    model = model,
                    buttons = listOf("PP")
                )
            }
        },
        bottomBar = { BottomBar(navigator = navigator) }
    )
}

@Composable
fun CurrentlyPlayingCover(
    feature: Feature
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(feature.darkColor)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        // Medium colored path
        val mediumPoint1 = Offset(0f, height * 0.3f)
        val mediumPoint2 = Offset(width * 0.1f, height * 0.35f)
        val mediumPoint3 = Offset(width * 0.4f, height * 0.05f)
        val mediumPoint4 = Offset(width * 0.75f, height * 0.7f)
        val mediumPoint5 = Offset(width * 1.4f, -height.toFloat())

        val mediumPath = Path().apply {
            moveTo(mediumPoint1.x, mediumPoint1.y)
            standardQuadFromTo(mediumPoint1, mediumPoint2)
            standardQuadFromTo(mediumPoint2, mediumPoint3)
            standardQuadFromTo(mediumPoint3, mediumPoint4)
            standardQuadFromTo(mediumPoint4, mediumPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }

        // Light colored path
        val lightPoint1 = Offset(0f, height * 0.35f)
        val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

        val lightPath = Path().apply {
            moveTo(lightPoint1.x, lightPoint1.y)
            standardQuadFromTo(lightPoint1, lightPoint2)
            standardQuadFromTo(lightPoint2, lightPoint3)
            standardQuadFromTo(lightPoint3, lightPoint4)
            standardQuadFromTo(lightPoint4, lightPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawPath(
                path = mediumPath,
                color = feature.mediumColor
            )
            drawPath(
                path = lightPath,
                color = feature.lightColor
            )
        }
    }
}

@Composable
fun CurrentlyPlayingText(
    currentSongTitle: String,
    currentSongAlbum: String,
    currentSongArtist: String
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 70.dp)
        ) {
            Text(
                text = currentSongTitle,
                style = MaterialTheme.typography.h1,
                lineHeight = 26.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "$currentSongAlbum - $currentSongArtist",
                style = MaterialTheme.typography.h2,
                lineHeight = 26.sp
            )
        }
    }
}

@Composable
fun SliderControls(model: HomeScreenModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${model.minutesIn.value()}:${model.secondsIn.value()}",
                        style = MaterialTheme.typography.h2,
                        lineHeight = 12.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "-${model.minutesOut.value()}:${model.secondsOut.value()}",
                        style = MaterialTheme.typography.h2,
                        lineHeight = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Slider(
                    value = model.sliderPos.value(),
                    valueRange = 0f..1f,
                    onValueChange = { model.sliderPos to it }
                )
            }
        }
    }
}

@Composable
fun ButtonControls(
    model: HomeScreenModel,
    buttons: List<String>
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth()
    ) {
        LazyRow {
            items(buttons.size) {
                Box(
                    modifier = Modifier
                        .padding(start = 160.dp, end = 20.dp)
                        .clickable {
                            val url = RequestUtils.getUrl(StreamRequest(
                                "1793", "320"
                            )).toString()
                            AudioPlayerService.play(url)
                            model.onSongPlayed()
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = "Test",
                        tint = Color.White
                    )
                }
            }
        }
    }
}