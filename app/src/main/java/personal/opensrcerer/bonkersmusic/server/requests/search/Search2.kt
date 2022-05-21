package personal.opensrcerer.bonkersmusic.server.requests.search

import personal.opensrcerer.bonkersmusic.server.requests.RequestPath
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.SubsonicRequest
import personal.opensrcerer.bonkersmusic.server.responses.search.Result2
import java.io.Serializable

data class Search2(
    override val queryParams: Map<String, Serializable>
    ) : SubsonicRequest<Result2>() {

    override val path: RequestPath = RequestPath.SEARCH2

    override fun getClazz(): Class<out Result2> {
        return Result2::class.java
    }
}
