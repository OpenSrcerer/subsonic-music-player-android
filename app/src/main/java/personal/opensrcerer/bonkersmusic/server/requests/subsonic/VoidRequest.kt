/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.server.requests.subsonic

abstract class VoidRequest : SubsonicRequest<Void>() {
    override fun getClazz(): Class<out Void> {
        return Void::class.java
    }
}