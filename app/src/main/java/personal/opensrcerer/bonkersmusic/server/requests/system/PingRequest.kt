package personal.opensrcerer.bonkersmusic.server.requests.system

import personal.opensrcerer.bonkersmusic.server.requests.RequestPath
import personal.opensrcerer.bonkersmusic.server.requests.subsonic.VoidRequest

class PingRequest : VoidRequest() {
    override val path: RequestPath = RequestPath.PING
}
