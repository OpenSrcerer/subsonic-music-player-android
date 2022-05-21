package personal.opensrcerer.bonkersmusic.server.responses.subsonic

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "subsonic-response")
open class SubsonicResponse {
    @field:Attribute(name = "status", required = false)
    var status: String? = ""

    @field:Attribute(name = "version", required = false)
    var version: String? = ""

    @field:Attribute(name = "type", required = false)
    var type: String? = ""

    @field:Attribute(name = "serverVersion", required = false)
    var serverVersion: String? = ""

    @field:Attribute(name = "xmlns", required = false)
    var xmlns: String? = ""

    @field:Element(name = "error", required = false)
    var error: SubsonicError? = null

    private var requestTime: Long = 0

    class SubsonicError {
        @field:Attribute(name = "error", required = false)
        var code: Int = -1

        @field:Attribute(name = "error", required = false)
        var message: String = ""
    }

    fun setTime(time: Long) {
        requestTime = time
    }

    fun getTime() : Long {
        return requestTime
    }
}