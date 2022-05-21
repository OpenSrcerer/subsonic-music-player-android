package personal.opensrcerer.bonkersmusic.server.responses.search

import androidx.annotation.Nullable
import personal.opensrcerer.bonkersmusic.server.responses.entities.Album
import personal.opensrcerer.bonkersmusic.server.responses.entities.Artist
import personal.opensrcerer.bonkersmusic.server.responses.entities.Song

sealed interface SearchResult {
    @Nullable
    fun getSongs() : List<Song>?

    @Nullable
    fun getArtists() : List<Artist>?

    @Nullable
    fun getAlbums() : List<Album>?
}