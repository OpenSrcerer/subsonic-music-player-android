/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.server.responses.subsonic

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

// Generic subsonic response class
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

    @Root(name = "error")
    class SubsonicError {
        @field:Attribute(name = "error", required = false)
        var code: Int = -1

        @field:Attribute(name = "message", required = false)
        var message: String = ""
    }

    fun setTime(time: Long) {
        requestTime = time
    }

    fun getTime() : Long {
        return requestTime
    }
}