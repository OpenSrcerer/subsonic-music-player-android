package personal.opensrcerer.bonkersmusic.ui.models

import androidx.lifecycle.ViewModel
import personal.opensrcerer.bonkersmusic.server.client.SubsonicService
import personal.opensrcerer.bonkersmusic.server.requests.browsing.DirectoryRequest
import personal.opensrcerer.bonkersmusic.server.requests.browsing.IndexesRequest
import personal.opensrcerer.bonkersmusic.server.responses.browsing.Directory
import personal.opensrcerer.bonkersmusic.server.responses.browsing.Indexes
import personal.opensrcerer.bonkersmusic.ui.dto.BrowseScreenLocation
import java.time.Duration
import java.time.temporal.ChronoUnit

class BrowseScreenModel : ViewModel() {

    // --- Context Keeper ---
    val currentPage = Stateable(BrowseScreenLocation.BROWSE)

    // --- Main Browse Page ---
    val noArtists = Stateable(false)
    val artists = Stateable(Indexes.empty())
    val currArtistId = Stateable("")

    // --- Album Page ---
    val noAlbums = Stateable(false)
    val albums = Stateable<List<Directory.Child>>(emptyList())
    val currAlbumId = Stateable("")

    // --- Song Page ---
    val noSongs = Stateable(false)
    val songs = Stateable<List<Directory.Child>>(emptyList())

    init {
        getArtists()
    }

    fun getArtists() {
        SubsonicService
            .request(IndexesRequest())
            .timeout(Duration.of(5000, ChronoUnit.MILLIS))
            .doOnError { noArtists changeTo true }
            .subscribe { idxs ->
                noArtists changeTo false
                artists changeTo idxs
            }
    }

    fun getAlbums() {
        SubsonicService
            .request(DirectoryRequest(mapOf(
                Pair("id", currArtistId.value())
            )))
            .timeout(Duration.of(5000, ChronoUnit.MILLIS))
            .doOnError { noAlbums changeTo true }
            .subscribe { dirs ->
                if (!dirs.getChildren().isEmpty())
                    albums changeTo dirs.getChildren()
                else
                    noAlbums changeTo true
            }
    }

    fun getSongs() {
        SubsonicService
            .request(DirectoryRequest(mapOf(
                Pair("id", currAlbumId.value())
            )))
            .timeout(Duration.of(5000, ChronoUnit.MILLIS))
            .doOnError { noSongs changeTo true }
            .subscribe { dirs ->
                if (!dirs.getChildren().isEmpty())
                    songs changeTo dirs.getChildren()
                else
                    noSongs changeTo true
            }
    }

    companion object {
        private var browseModel: BrowseScreenModel? = null

        fun getBrowseModel(): BrowseScreenModel {
            if (browseModel == null) browseModel = BrowseScreenModel()
            return browseModel!!
        }
    }
}