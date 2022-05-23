package personal.opensrcerer.bonkersmusic.server.responses.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import personal.opensrcerer.bonkersmusic.server.responses.enum.Unknown

@Root(name = "album")
class Album @JvmOverloads constructor(
    @param:Attribute(name = "id")
    @get:Attribute(name = "id")
    val id: String = Unknown.ID.value,

    @param:Attribute(name = "parent", required = false)
    @get:Attribute(name = "parent", required = false)
    val parent: String? = Unknown.PARENT.value,

    @param:Attribute(name = "name", required = false)
    @get:Attribute(name = "name", required = false)
    val name: String? = Unknown.ALBUM.value,

    @param:Attribute(name = "artist", required = false)
    @get:Attribute(name = "artist", required = false)
    val artist: String? = Unknown.ARTIST_NAME.value,

    @param:Attribute(name = "isDir", required = false)
    @get:Attribute(name = "isDir", required = false)
    val isDir: String? = Unknown.IS_DIRECTORY.value,

    @param:Attribute(name = "coverArt", required = false)
    @get:Attribute(name = "coverArt", required = false)
    val coverArt: String? = Unknown.COVER_ART.value
) : EmbeddableEntity {
    override fun id(): String {
        return id
    }

    override fun embedName(): String {
        return name ?: Unknown.ALBUM.name
    }

    override fun embedValue(): String {
        return artist ?: Unknown.ARTIST_NAME.value
    }
}