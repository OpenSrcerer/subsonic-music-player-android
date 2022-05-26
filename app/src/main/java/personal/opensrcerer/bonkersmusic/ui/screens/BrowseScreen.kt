package personal.opensrcerer.bonkersmusic.ui.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import personal.opensrcerer.bonkersmusic.audio.AudioPlayerService
import personal.opensrcerer.bonkersmusic.server.responses.browsing.Directory
import personal.opensrcerer.bonkersmusic.server.responses.entities.Artist
import personal.opensrcerer.bonkersmusic.ui.common.BottomBar
import personal.opensrcerer.bonkersmusic.ui.common.TopBar
import personal.opensrcerer.bonkersmusic.ui.dto.BrowseScreenLocation
import personal.opensrcerer.bonkersmusic.ui.dto.Feature
import personal.opensrcerer.bonkersmusic.ui.models.BrowseScreenModel
import personal.opensrcerer.bonkersmusic.ui.theme.*
import personal.opensrcerer.bonkersmusic.ui.util.standardQuadFromTo

@ExperimentalFoundationApi
@Composable
fun BrowseScreen(
    navigator: NavController,
    model: BrowseScreenModel = BrowseScreenModel.getBrowseModel()
) {
    Scaffold(
        topBar = { TopBar(navigator = navigator) },
        content = {
              Column(
                  modifier = Modifier
                      .background(DeepBlue)
                      .fillMaxSize()
              ) {
                  when (model.currentPage.value()) {
                      BrowseScreenLocation.BROWSE -> ArtistList(model)
                      BrowseScreenLocation.ALBUMS -> AlbumList(model)
                      BrowseScreenLocation.SONGS -> SongList(model)
                  }
              }
        },
        bottomBar = { BottomBar(navigator = navigator) }
    )
}

@Composable
fun NoData(
    text: String,
    callback: () -> Unit
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
                text = "No $text found",
                color = TextWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Reload",
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { callback() }
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ArtistList(
    model: BrowseScreenModel
) {
    if (model.noArtists.value()) {
        NoData("artists") { model.getArtists() }
        return
    }

    LazyColumn(
        modifier = Modifier
            .padding(bottom = 70.dp)
    ) {
        model.artists.value().getIndexes()?.forEach { index ->
            stickyHeader {
                StickyHeaderStyle(name = index.name)
            }

            items(index.artists) { artist ->
                ArtistBlockStyle(model, artist)
            }
        }
    }
}

// --- Browse Screen ---
@Composable
fun StickyHeaderStyle(
    name: String
) {
    Row(
        modifier = Modifier
            .background(DarkerDeepBlue)
            .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.h1,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ArtistBlockStyle(
    model: BrowseScreenModel,
    artist: Artist
) {
    Row(
        modifier = Modifier
            .clickable {
                model.currArtistId changeTo artist.id
                model.getAlbums()
                model.currentPage changeTo BrowseScreenLocation.ALBUMS
            }
            .background(DeepBlue)
            .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
            .fillMaxWidth()
    ) {
        val artistName =
            if (artist.embedName().length > 30)
                "${artist.embedName().subSequence(0, 30)}..."
            else
                artist.embedName()
        Text(
            text = artistName,
            style = MaterialTheme.typography.h2,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

// --- Album Screen ---
@ExperimentalFoundationApi
@Composable
fun AlbumList(
    model: BrowseScreenModel
) {
    if (model.noAlbums.value()) {
        NoData("albums") { model.getAlbums() }
    }

    val albums = model.albums.value()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Play Again",
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(15.dp)
        )
    }
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(albums.size) {
            Album(model, albums[it])
        }
    }
}

@Composable
fun Album(
    model: BrowseScreenModel,
    album: Directory.Child
) {
    val albumTitle =
        if (album.title.length > 30)
            "${album.title.subSequence(0, 30)}..."
        else
            album.title

    BoxWithConstraints(
        modifier = Modifier
            .clickable {
                model.currAlbumId changeTo album.id
                model.getSongs()
                model.currentPage changeTo BrowseScreenLocation.SONGS
            }
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(OrangeYellow1)
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
                color = OrangeYellow2
            )
            drawPath(
                path = lightPath,
                color = OrangeYellow3
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = albumTitle,
                style = MaterialTheme.typography.h2,
                lineHeight = 26.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
        }
    }
}

// --- Song Screen ---
@ExperimentalFoundationApi
@Composable
fun SongList(
    model: BrowseScreenModel
) {
    if (model.noSongs.value()) {
        NoData("songs") { model.getSongs() }
        return
    }

    LazyColumn(
        modifier = Modifier
            .padding(bottom = 70.dp)
    ) {
        model.songs.value().forEach { song ->
            item {
                SongBlockStyle(model, song)
            }
        }
    }
}

@Composable
fun SongBlockStyle(
    model: BrowseScreenModel,
    song: Directory.Child
) {
    Row(
        modifier = Modifier
            .clickable {
                AudioPlayerService.play(song)
            }
            .background(DeepBlue)
            .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
            .fillMaxWidth()
    ) {
        val songTitle =
            "${song.track} - ".plus(if (song.title.length > 30)
                "${song.title.subSequence(0, 30)}..."
            else
                song.title)
        Text(
            text = songTitle,
            style = MaterialTheme.typography.h2,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}