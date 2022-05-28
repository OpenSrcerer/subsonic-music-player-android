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