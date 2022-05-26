package personal.opensrcerer.bonkersmusic.server.requests.browsing

import personal.opensrcerer.bonkersmusic.server.requests.RequestPath
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.SubsonicRequest
import personal.opensrcerer.bonkersmusic.server.responses.browsing.Directory
import java.io.Serializable

class DirectoryRequest(
    queryParams: Map<String, Serializable>
): SubsonicRequest<Directory>(queryParams) {
    override val path: RequestPath = RequestPath.GET_MUSIC_DIRECTORY

    override fun getClazz(): Class<out Directory> {
        return Directory::class.java
    }
}