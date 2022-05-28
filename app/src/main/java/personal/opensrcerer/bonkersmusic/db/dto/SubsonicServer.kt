/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.db.dto

// DTO that represents a subsonic server to be stored in the DB
data class SubsonicServer(
    val id: Int = 1,
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val version: String
)