package personal.opensrcerer.bonkersmusic.server.responses.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import personal.opensrcerer.bonkersmusic.server.responses.entities.Album
import personal.opensrcerer.bonkersmusic.server.responses.entities.Artist
import personal.opensrcerer.bonkersmusic.server.responses.entities.Song
import personal.opensrcerer.bonkersmusic.server.responses.subsonic.SubsonicResponse

class Result2 : SubsonicResponse(), SearchResult {

    @JsonProperty("searchResult2")
    private val searchResult2 = SearchResult2()

    private class SearchResult2 {
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JacksonXmlElementWrapper(useWrapping = false)
        val artist: Array<Artist>? = null

        @JsonIgnoreProperties(ignoreUnknown = true)
        @JacksonXmlElementWrapper(useWrapping = false)
        val album: Array<Album>? = null

        @JsonIgnoreProperties(ignoreUnknown = true)
        @JacksonXmlElementWrapper(useWrapping = false)
        val song: Array<Song>? = null
    }

    override fun getArtists() : Array<Artist>? {
        return this.searchResult2.artist
    }

    override fun getAlbums() : Array<Album>? {
        return this.searchResult2.album
    }

    override fun getSongs() : Array<Song>? {
        return this.searchResult2.song
    }
}