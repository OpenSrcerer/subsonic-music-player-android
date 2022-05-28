/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.server.requests.media

import personal.opensrcerer.bonkersmusic.server.requests.RequestPath
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.VoidRequest
import java.io.Serializable

class CoverImageRequest(
    override val queryParams: Map<String, Serializable>
) : VoidRequest() {

    constructor(id: String) : this(
        mapOf(Pair("id", id), Pair("size", 512))
    )

    override val path: RequestPath = RequestPath.GET_COVER_ART
}