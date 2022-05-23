package personal.opensrcerer.bonkersmusic.server.responses.entities

interface EmbeddableEntity {
    fun id(): String
    fun embedName(): String
    fun embedValue(): String
}
