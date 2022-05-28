/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.server.responses.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import personal.opensrcerer.bonkersmusic.server.responses.enum.Unknown

@Root(name = "artist")
data class Artist @JvmOverloads constructor(
    @param:Attribute(name = "id")
    @get:Attribute(name = "id")
    val id: String = Unknown.ID.value,

    @param:Attribute(name = "name", required = false)
    @get:Attribute(name = "name", required = false)
    val name: String? = Unknown.ARTIST_NAME.value,
) : EmbeddableEntity {
    override fun id(): String {
        return id
    }

    override fun embedName(): String {
        return name ?: Unknown.ARTIST_NAME.name
    }

    override fun embedValue(): String {
        return id
    }
}