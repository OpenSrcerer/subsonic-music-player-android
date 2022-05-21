package personal.opensrcerer.bonkersmusic.server.responses.entities

import com.fasterxml.jackson.annotation.JsonProperty
import personal.opensrcerer.bonkersmusic.server.responses.enum.Unknown
import personal.opensrcerer.bonkersmusic.ui.dto.EmbeddableEntity
import java.util.*

class Artist (
    @JsonProperty("id")      id: String?,
    @JsonProperty("name")    name: String?,
    @JsonProperty("starred") val starred: Date?
) : EmbeddableEntity {

    val id = id ?: Unknown.ID.value
    val name = name ?: Unknown.ARTIST_NAME.value

    override fun id(): String {
        return id
    }

    override fun embedName(): String {
        return name
    }

    override fun embedValue(): String {
        return id
    }
}