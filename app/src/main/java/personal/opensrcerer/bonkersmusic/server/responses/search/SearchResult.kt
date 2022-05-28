/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

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