/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.server.requests.browsing

import personal.opensrcerer.bonkersmusic.server.requests.RequestPath
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.SubsonicRequest
import personal.opensrcerer.bonkersmusic.server.responses.browsing.Indexes

class IndexesRequest: SubsonicRequest<Indexes>() {
    override val path: RequestPath = RequestPath.GET_INDEXES

    override fun getClazz(): Class<out Indexes> {
        return Indexes::class.java
    }
}