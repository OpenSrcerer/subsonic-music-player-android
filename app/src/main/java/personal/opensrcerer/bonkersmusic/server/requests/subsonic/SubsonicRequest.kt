package personal.opensrcerer.bonkersmusic.server.requests.subsonic

import personal.opensrcerer.bonkersmusic.server.requests.RequestPath
import java.io.Serializable

abstract class SubsonicRequest<out T>(
    open val queryParams : Map<String, Serializable>
) {
    constructor() : this(emptyMap())

    abstract val path : RequestPath

    abstract fun getClazz() : Class<out T>
}