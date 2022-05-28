package personal.opensrcerer.bonkersmusic.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.glide.GlideImage
import personal.opensrcerer.bonkersmusic.R
import personal.opensrcerer.bonkersmusic.audio.AudioPlayerService
import personal.opensrcerer.bonkersmusic.server.client.RequestUtils
import personal.opensrcerer.bonkersmusic.server.requests.media.CoverImageRequest
import personal.opensrcerer.bonkersmusic.server.responses.browsing.Directory
import personal.opensrcerer.bonkersmusic.ui.common.BottomBar
import personal.opensrcerer.bonkersmusic.ui.models.HomeScreenModel
import personal.opensrcerer.bonkersmusic.ui.theme.ButtonBlue
import personal.opensrcerer.bonkersmusic.ui.theme.DeepBlue
import personal.opensrcerer.bonkersmusic.ui.theme.TextWhite

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    navigator: NavController,
    model: HomeScreenModel = HomeScreenModel.getHomeModel(),
    songIsPlaying: Boolean = HomeScreenModel.getHomeModel().songIsPlaying.value()
) {
    val currentSong = AudioPlayerService.getCurrentSong()

    Scaffold(
        topBar = { },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DeepBlue)
            )

            if (!model.songLoaded.value()) {
                NothingPlaying(navigator)
                return@Scaffold
            }

            Column {
                CurrentlyPlayingCover(currentSong)
                CurrentlyPlayingText(
                    currentSong.title,
                    currentSong.album,
                    currentSong.artist
                )
                SliderControls(model)
                ButtonControls(
                    songIsPlaying = songIsPlaying,
                    buttons = listOf("Play")
                )
            }
        },
        bottomBar = { BottomBar(navigator = navigator) }
    )
}

@Composable
fun NothingPlaying(
    navigator: NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Nothing playing right now...",
                color = TextWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            )
            Text(
                text = "Play some songs!",
                color = TextWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { navigator.navigate("browse") }
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
            GlideImage(
                imageModel = R.raw.sleeping,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(300.dp)
            )
        }
    }
}

@Composable
fun CurrentlyPlayingCover(currentSong: Directory.Child) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
    ) {
        CoilImage(
            imageModel = RequestUtils.getUrl(CoverImageRequest(currentSong.coverArt))
        )
    }
}

@Composable
fun CurrentlyPlayingText(
    currentSongTitle: String,
    currentSongAlbum: String,
    currentSongArtist: String
) {
    val songTitle =
        if (currentSongTitle.length > 29)
            "${currentSongTitle.subSequence(0, 29)}..."
        else
            currentSongTitle
    val artistAndAlbum =
        if ("$currentSongAlbum - $currentSongArtist".length > 29)
            "${"$currentSongAlbum - $currentSongArtist".subSequence(0, 29)}..."
        else
            "$currentSongAlbum - $currentSongArtist"

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
                text = songTitle,
                style = MaterialTheme.typography.h1,
                lineHeight = 26.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = artistAndAlbum,
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
                    onValueChange = {
                        model.onSliderChangeValueByUser(it)
                    },
                    onValueChangeFinished = {
                        model.onSliderChangeValueFinishByUser()
                    },
                )
            }
        }
    }
}

@Composable
fun ButtonControls(
    songIsPlaying: Boolean,
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
                            if (!AudioPlayerService.hasSong()) {
                                return@clickable
                            }
                            AudioPlayerService.togglePause()
                        }
                ) {
                    Icon(
                        painter = if (songIsPlaying)
                            painterResource(id = R.drawable.ic_pause)
                        else
                            painterResource(id = R.drawable.ic_play),
                        contentDescription = "Test",
                        tint = Color.White
                    )
                }
            }
        }
    }
}