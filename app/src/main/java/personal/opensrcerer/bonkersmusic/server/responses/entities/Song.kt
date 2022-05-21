package personal.opensrcerer.bonkersmusic.server.responses.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import personal.opensrcerer.bonkersmusic.server.responses.enum.Unknown
import personal.opensrcerer.bonkersmusic.ui.dto.EmbeddableEntity

@Root(name = "song")
class Song @JvmOverloads constructor(
    @param:Attribute(name = "id")
    @get:Attribute(name = "id")
    val id: String = Unknown.ID.value,

    @param:Attribute(name = "parent", required = false)
    @get:Attribute(name = "parent", required = false)
    val parent: String? = Unknown.PARENT.value,

    @param:Attribute(name = "title", required = false)
    @get:Attribute(name = "title", required = false)
    val title: String? = Unknown.TITLE.value,

    @param:Attribute(name = "isDir", required = false)
    @get:Attribute(name = "isDir", required = false)
    val isDir: String? = Unknown.IS_DIRECTORY.value,

    @param:Attribute(name = "isVideo", required = false)
    @get:Attribute(name = "isVideo", required = false)
    val isVideo: String? = Unknown.IS_VIDEO.value,

    @param:Attribute(name = "album", required = false)
    @get:Attribute(name = "album", required = false)
    val album: String? = Unknown.ALBUM.value,

    @param:Attribute(name = "artist", required = false)
    @get:Attribute(name = "artist", required = false)
    val artist: String? = Unknown.ARTIST.value,

    @param:Attribute(name = "track", required = false)
    @get:Attribute(name = "track", required = false)
    val track: String? = Unknown.TRACK.value,

    @param:Attribute(name = "year", required = false)
    @get:Attribute(name = "year", required = false)
    val year: String? = Unknown.YEAR.value,

    @param:Attribute(name = "genre", required = false)
    @get:Attribute(name = "genre", required = false)
    val genre: String? = Unknown.GENRE.value,

    @param:Attribute(name = "coverArt", required = false)
    @get:Attribute(name = "coverArt", required = false)
    val coverArt: String? = Unknown.COVER_ART.value,

    @param:Attribute(name = "size", required = false)
    @get:Attribute(name = "size", required = false)
    val size: String? = Unknown.SIZE.value,

    @param:Attribute(name = "contentType", required = false)
    @get:Attribute(name = "contentType", required = false)
    val contentType: String? = Unknown.CONTENT_TYPE.value,

    @param:Attribute(name = "suffix", required = false)
    @get:Attribute(name = "suffix", required = false)
    val suffix: String? = Unknown.SUFFIX.value,

    @param:Attribute(name = "transcodedContentType", required = false)
    @get:Attribute(name = "transcodedContentType", required = false)
    val transcodedContentType: String? = Unknown.TRANSCODED_CONTENT_TYPE.value,

    @param:Attribute(name = "transcodedSuffix", required = false)
    @get:Attribute(name = "transcodedSuffix", required = false)
    val transcodedSuffix: String? = Unknown.TRANSCODED_SUFFIX.value,

    @param:Attribute(name = "duration", required = false)
    @get:Attribute(name = "duration", required = false)
    val duration: String? = Unknown.DURATION.value,

    @param:Attribute(name = "bitrate", required = false)
    @get:Attribute(name = "bitrate", required = false)
    val bitrate: String? = Unknown.BITRATE.value,

    @param:Attribute(name = "path", required = false)
    @get:Attribute(name = "path", required = false)
    val path: String? = Unknown.PATH.value
) : EmbeddableEntity {
    override fun id(): String {
        return id
    }

    override fun embedName(): String {
        return title ?: Unknown.TITLE.name
    }

    override fun embedValue(): String {
        return "$artist - $album"
    }
}