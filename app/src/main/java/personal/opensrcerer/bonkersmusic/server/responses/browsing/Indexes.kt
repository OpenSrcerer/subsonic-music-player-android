package personal.opensrcerer.bonkersmusic.server.responses.browsing

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import personal.opensrcerer.bonkersmusic.server.responses.entities.Artist
import personal.opensrcerer.bonkersmusic.server.responses.entities.Song
import personal.opensrcerer.bonkersmusic.server.responses.enum.Unknown
import personal.opensrcerer.bonkersmusic.server.responses.subsonic.SubsonicResponse

class Indexes(
    @param:Element(name = "indexes")
    @get:Element(name = "indexes")
    val indexes: IndexesData
) : SubsonicResponse() {

    @Root(name = "indexes")
    data class IndexesData @JvmOverloads constructor(
        @param:Attribute(name = "lastModified")
        @get:Attribute(name = "lastModified")
        val lastModified: String?,

        @param:Attribute(name = "ignoredArticles")
        @get:Attribute(name = "ignoredArticles")
        val ignoredArticles: String?,

        @param:ElementList(entry = "child", inline = true, required = false)
        @get:ElementList(entry = "child", inline = true, required = false)
        val children: List<Song>? = null,

        @param:ElementList(entry = "shortcut", inline = true, required = false)
        @get:ElementList(entry = "shortcut", inline = true, required = false)
        val shortcuts: List<Shortcut>? = null,

        @param:ElementList(entry = "index", inline = true)
        @get:ElementList(entry = "index", inline = true)
        var indexes: Set<Index>? = null
    ) {
        init {
            indexes = indexes?.sortedBy { e -> e.name }?.toSet()
        }
    }

    data class Shortcut(
        @param:Attribute(name = "id")
        @get:Attribute(name = "id")
        val id: Long,

        @param:Attribute(name = "name")
        @get:Attribute(name = "name")
        val name: String
    )

    @Root(name = "index")
    data class Index constructor(
        @param:Attribute(name = "name")
        @get:Attribute(name = "name")
        val name: String,

        @param:ElementList(entry = "artist", inline = true)
        @get:ElementList(entry = "artist", inline = true)
        val artists: List<Artist>
    )

    fun getChildren(): List<Song>? {
        return this.indexes.children
    }

    fun getShortcuts(): List<Shortcut>? {
        return this.indexes.shortcuts
    }

    fun getIndexes(): Set<Index>? {
        return this.indexes.indexes
    }

    companion object {
        fun empty(): Indexes {
            return Indexes(
                IndexesData(
                    Unknown.DATE.value,
                    Unknown.UNKNOWN.value,
                    emptyList(),
                    emptyList(),
                    emptySet()
                )
            )
        }
    }
}