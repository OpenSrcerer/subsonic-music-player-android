package personal.opensrcerer.bonkersmusic.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import personal.opensrcerer.bonkersmusic.server.responses.browsing.Indexes
import personal.opensrcerer.bonkersmusic.server.responses.entities.Artist
import personal.opensrcerer.bonkersmusic.ui.common.BottomBar
import personal.opensrcerer.bonkersmusic.ui.common.TopBar
import personal.opensrcerer.bonkersmusic.ui.models.BrowseScreenModel
import personal.opensrcerer.bonkersmusic.ui.models.Stateable
import personal.opensrcerer.bonkersmusic.ui.theme.DarkerDeepBlue
import personal.opensrcerer.bonkersmusic.ui.theme.DeepBlue

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
                  ArtistList(model.indexes)
              }
        },
        bottomBar = { BottomBar(navigator = navigator) }
    )
}

@ExperimentalFoundationApi
@Composable
fun ArtistList(indexes: Stateable<Indexes>) {
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 70.dp)
    ) {
        indexes.value().getIndexes()?.forEach { index ->
            stickyHeader {
                StickyHeaderStyle(name = index.name)
            }

            items(index.artists) { artist ->
                ArtistBlockStyle(artist)
            }
        }
    }
}

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
fun ArtistBlockStyle(artist: Artist) {
    Row(
        modifier = Modifier
            .clickable {  }
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