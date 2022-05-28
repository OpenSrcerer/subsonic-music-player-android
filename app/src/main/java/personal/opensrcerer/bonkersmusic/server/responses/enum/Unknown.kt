/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.server.responses.enum

// Unknown value fallbacks
enum class Unknown(val value: String) {
    UNKNOWN("Unknown"),
    ID("Unknown ID"),
    PARENT("Unknown Parent"),
    TITLE("Unknown Title"),
    IS_DIRECTORY("Unknown if Directory"),
    IS_VIDEO("Unknown if Video"),
    ALBUM("Unknown Album"),
    ARTIST("Unknown Artist"),
    ARTIST_NAME("Unknown Artist Name"),
    TRACK("Unknown Track"),
    YEAR("Unknown Year"),
    GENRE("Unknown Genre"),
    COVER_ART("Unknown Cover Art"),
    SIZE("Unknown Size"),
    CONTENT_TYPE("Unknown Content Type"),
    SUFFIX("Unknown Suffix"),
    TRANSCODED_CONTENT_TYPE("Unknown Transcoded Content Type"),
    TRANSCODED_SUFFIX("Unknown Transcoded Suffix"),
    DURATION("Unknown Duration"),
    BITRATE("Unknown Bitrate"),
    PATH("Unknown Path"),
    DATE("Unknown Date");

    override fun toString(): String {
        return value
    }
}