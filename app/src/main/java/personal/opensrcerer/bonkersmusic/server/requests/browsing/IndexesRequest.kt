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