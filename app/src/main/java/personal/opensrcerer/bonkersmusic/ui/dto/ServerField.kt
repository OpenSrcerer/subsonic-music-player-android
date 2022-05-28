package personal.opensrcerer.bonkersmusic.ui.dto

data class ServerField(
    val title: String,
    val hint: String = title,
    val content: String,
    val type: Type,
    val updateContent: (String) -> Unit
) {
    enum class Type {
        REGULAR,
        NUMBER,
        PASSWORD
    }
}