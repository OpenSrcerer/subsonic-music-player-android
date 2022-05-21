package personal.opensrcerer.bonkersmusic.server.requests.browsing

import personal.opensrcerer.bonkersmusic.server.requests.RequestPath
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.SubsonicRequest
import personal.opensrcerer.bonkersmusic.server.responses.browsing.MusicFolders

class MusicFoldersRequest : SubsonicRequest<MusicFolders>() {
    override val path: RequestPath = RequestPath.GET_MUSIC_FOLDERS

    override fun getClazz(): Class<out MusicFolders> {
        return MusicFolders::class.java
    }
}
