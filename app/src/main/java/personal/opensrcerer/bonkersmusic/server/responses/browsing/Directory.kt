/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.server.responses.browsing

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import personal.opensrcerer.bonkersmusic.server.responses.enum.Unknown
import personal.opensrcerer.bonkersmusic.server.responses.subsonic.SubsonicResponse
import personal.opensrcerer.bonkersmusic.ui.dto.BrowseScreenType

data class Directory(
    @param:Element(name = "directory")
    @get:Element(name = "directory")
    val directories: DirectoryData
): SubsonicResponse() {
    @Root(name = "directory")
    data class DirectoryData @JvmOverloads constructor(
        @param:Attribute(name = "id")
        @get:Attribute(name = "id")
        val id: String = Unknown.ID.value,

        @param:Attribute(name = "name")
        @get:Attribute(name = "name")
        val name: String = Unknown.ARTIST_NAME.value,

        @param:ElementList(entry = "child", inline = true, required = false)
        @get:ElementList(entry = "child", inline = true, required = false)
        val children: List<Child>? = null
    )

    @Root(name = "child")
    class Child {
        @field:Attribute(name = "id")
        var id: String = Unknown.ID.value

        @field:Attribute(name = "title")
        var title: String = Unknown.TITLE.value

        @field:Attribute(name = "album", required = false)
        var album: String = Unknown.ALBUM.value

        @field:Attribute(name = "artist", required = false)
        var artist: String = Unknown.ARTIST.value

        @field:Attribute(name = "coverArt", required = false)
        var coverArt: String = Unknown.COVER_ART.value

        @field:Attribute(name = "track", required = false)
        var track: String = "?"

        @field:Attribute(name = "isDir", required = false)
        var isDir: Boolean = false
    }

    fun getChildren(): List<Child> {
        return directories.children ?: emptyList()
    }

    fun getType(): BrowseScreenType {
        return if (getChildren().stream().allMatch { child -> child.isDir })
            BrowseScreenType.ALBUMS
        else
            BrowseScreenType.CHILDREN
    }
}