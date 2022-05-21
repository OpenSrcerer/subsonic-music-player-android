package personal.opensrcerer.bonkersmusic.server.responses.search

import org.simpleframework.xml.*
import personal.opensrcerer.bonkersmusic.server.responses.entities.Album
import personal.opensrcerer.bonkersmusic.server.responses.entities.Artist
import personal.opensrcerer.bonkersmusic.server.responses.entities.Song
import personal.opensrcerer.bonkersmusic.server.responses.subsonic.SubsonicResponse

class Result3(
    @param:Element(name = "searchResult3")
    @get:Element(name = "searchResult3")
    val searchResult3: SearchResult3
) : SubsonicResponse(), SearchResult {

    @Root(name = "searchResult3")
    class SearchResult3(
        @param:ElementList(inline = true)
        @get:ElementList(inline = true)
        val artists: List<Artist>? = null,

        @param:ElementList(inline = true)
        @get:ElementList(inline = true)
        val albums: List<Album>? = null,

        @param:ElementList(inline = true)
        @get:ElementList(inline = true)
        val songs: List<Song>? = null
    )

    override fun getArtists() : List<Artist>? {
        return this.searchResult3.artists
    }

    override fun getAlbums() : List<Album>? {
        return this.searchResult3.albums
    }

    override fun getSongs() : List<Song>? {
        return this.searchResult3.songs
    }
}