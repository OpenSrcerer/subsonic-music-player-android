/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.ui.dto

import personal.opensrcerer.bonkersmusic.server.responses.browsing.Directory
import personal.opensrcerer.bonkersmusic.ui.models.BrowseScreenModel

// Used for directory browsing on the browse page
data class ChildPage(
    val model: BrowseScreenModel,
    var data: Directory? = null
) {
    fun onDataSuccess(dir: Directory) {
        data = dir
    }
}
