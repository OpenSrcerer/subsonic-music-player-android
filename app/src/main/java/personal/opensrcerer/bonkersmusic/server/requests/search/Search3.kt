/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.server.requests.search

import personal.opensrcerer.bonkersmusic.server.requests.RequestPath
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.SubsonicRequest
import personal.opensrcerer.bonkersmusic.server.responses.search.Result3
import java.io.Serializable

data class Search3(
    override val queryParams: Map<String, Serializable>
    ) : SubsonicRequest<Result3>() {

    override val path: RequestPath = RequestPath.SEARCH3

    override fun getClazz(): Class<out Result3> {
        return Result3::class.java
    }
}
