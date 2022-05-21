package personal.opensrcerer.bonkersmusic.server.requests.media

import personal.opensrcerer.bonkersmusic.server.requests.RequestPath
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.VoidRequest
import java.io.Serializable

class StreamRequest(
    override val queryParams: Map<String, Serializable>
) : VoidRequest() {

    constructor(id: String, bitrate: String) : this(
        mapOf(Pair("id", id), Pair("maxBitRate", bitrate))
    )

    override val path: RequestPath = RequestPath.STREAM
}