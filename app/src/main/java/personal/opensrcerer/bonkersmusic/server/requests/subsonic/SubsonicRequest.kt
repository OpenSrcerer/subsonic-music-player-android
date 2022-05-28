/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.server.requests.subsonic

import personal.opensrcerer.bonkersmusic.server.requests.RequestPath
import java.io.Serializable

abstract class SubsonicRequest<out T>(
    open val queryParams : Map<String, Serializable>
) {
    constructor() : this(emptyMap())

    abstract val path : RequestPath

    abstract fun getClazz() : Class<out T>
}