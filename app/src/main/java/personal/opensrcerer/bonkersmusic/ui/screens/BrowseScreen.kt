package personal.opensrcerer.bonkersmusic.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.landscapist.coil.CoilImage
import personal.opensrcerer.bonkersmusic.audio.AudioPlayerService
import personal.opensrcerer.bonkersmusic.server.client.RequestUtils
import personal.opensrcerer.bonkersmusic.server.requests.media.CoverImageRequest
import personal.opensrcerer.bonkersmusic.server.responses.browsing.Directory
import personal.opensrcerer.bonkersmusic.server.responses.entities.Artist
import personal.opensrcerer.bonkersmusic.ui.common.BottomBar
import personal.opensrcerer.bonkersmusic.ui.common.TopBar
import personal.opensrcerer.bonkersmusic.ui.dto.BrowseScreenType
import personal.opensrcerer.bonkersmusic.ui.dto.ChildPage
import personal.opensrcerer.bonkersmusic.ui.models.BrowseScreenModel
import personal.opensrcerer.bonkersmusic.ui.theme.*
import personal.opensrcerer.bonkersmusic.R

@ExperimentalFoundationApi
@Composable
fun BrowseScreen(
    navigator: NavController,
    model: BrowseScreenModel = BrowseScreenModel.getBrowseModel(),
    pageType: BrowseScreenType
) {
    Scaffold(
        topBar = { TopBar(navigator, model) },
        content = {
              Column(
                  modifier = Modifier
                      .background(DeepBlue)
                      .fillMaxSize()
              ) {
                  when (pageType) {
                      BrowseScreenType.LOADING -> Loading()
                      BrowseScreenType.BROWSE -> ArtistList(model)
                      BrowseScreenType.ALBUMS -> BlockChildrenList(model.currChildPage()!!)
                      BrowseScreenType.CHILDREN -> ListViewChildren(
                          model,
                          model.currChildPage()!!,
                          navigator
                      )
                      BrowseScreenType.NO_DATA -> NoData {  }
                  }
              }
        },
        bottomBar = { BottomBar(navigator = navigator) }
    )
}

@Composable
fun NoData(
    reload: () -> Unit
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
                text = "No data found",
                color = TextWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            )
            Text(
                text = "Try Again",
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { reload() }
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }
}

@Composable
fun Loading() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Loading data...",
                color = TextWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ArtistList(
    model: BrowseScreenModel
) {
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 70.dp)
    ) {
        model.artists.value().getIndexes()?.forEach { index ->
            stickyHeader {
                StickyHeaderStyle(name = index.name)
            }

            items(index.artists) { artist ->
                ListedArtistStyle(model, artist)
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
fun ListedArtistStyle(
    model: BrowseScreenModel,
    artist: Artist
) {
    Row(
        modifier = Modifier
            .clickable { model.downDir(artist.id) }
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
fun BlockChildrenList(
    page: ChildPage
) {
    val children = page.data?.getChildren()!!

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Folders",
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(15.dp)
        )
    }
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(children.size) {
            BlockChild(page, children[it])
        }
    }
}

@Composable
fun BlockChild(
    childPage: ChildPage,
    child: Directory.Child
) {
    val albumTitle = child.title

    BoxWithConstraints(
        modifier = Modifier
            .clickable { childPage.model.downDir(child.id) }
            .padding(7.5.dp)
            .aspectRatio(0.8f)
            .clip(RoundedCornerShape(10.dp))
            .background(BlueViolet4)
    ) {
        CoilImage(
            imageModel = RequestUtils.getUrl(CoverImageRequest(child.coverArt)),
            modifier = Modifier
                .padding(top = 50.dp)
                .size(200.dp)
                .align(Alignment.BottomCenter),
            error = ImageVector.vectorResource(id = R.drawable.ic_folder)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 9.dp, top = 3.dp, end = 4.dp)
        ) {
            Text(
                text = albumTitle,
                style = MaterialTheme.typography.h2,
                lineHeight = 20.sp,
                modifier = Modifier.align(Alignment.TopStart),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}

// --- Song Screen ---
@ExperimentalFoundationApi
@Composable
fun ListViewChildren(
    model: BrowseScreenModel,
    page: ChildPage,
    navigator: NavController
) {
    val children = page.data!!.getChildren()

    LazyColumn(
        modifier = Modifier
            .padding(bottom = 70.dp)
    ) {
       children.forEach { child ->
            item {
                ListedChildStyle(model, child, navigator)
            }
        }
    }
}

@Composable
fun ListedChildStyle(
    model: BrowseScreenModel,
    child: Directory.Child,
    navigator: NavController
) {
    Row(
        modifier = Modifier
            .clickable {
                if (child.isDir) {
                    model.downDir(child.id)
                    return@clickable
                }

                if (AudioPlayerService.hasSong()) {
                    AudioPlayerService.stopPlayer()
                }

                AudioPlayerService.play(child)
                navigator.navigate("home")
            }
            .background(DeepBlue)
            .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
            .fillMaxWidth()
    ) {
        val childTitle =
            "${child.track} - ".plus(if (child.title.length > 30)
                "${child.title.subSequence(0, 30)}..."
            else
                child.title)
        Text(
            text = childTitle,
            style = MaterialTheme.typography.h2,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}