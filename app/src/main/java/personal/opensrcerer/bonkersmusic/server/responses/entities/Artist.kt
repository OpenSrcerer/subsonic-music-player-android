package personal.opensrcerer.bonkersmusic.server.responses.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import personal.opensrcerer.bonkersmusic.server.responses.enum.Unknown

@Root(name = "artist")
class Artist @JvmOverloads constructor(
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