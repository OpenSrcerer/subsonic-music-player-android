/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.ui.dto

// DTO to be used for login fields
data class ServerField(
    val title: String,
    val hint: String = title,
    val content: String,
    val type: Type,
    val updateContent: (String) -> Unit // Call this to update the content
) {
    enum class Type {
        REGULAR,
        NUMBER,
        PASSWORD
    }
}