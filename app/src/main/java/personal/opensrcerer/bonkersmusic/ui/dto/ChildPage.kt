package personal.opensrcerer.bonkersmusic.ui.dto

import personal.opensrcerer.bonkersmusic.server.responses.browsing.Directory
import personal.opensrcerer.bonkersmusic.ui.models.BrowseScreenModel

data class ChildPage(
    val model: BrowseScreenModel,
    var data: Directory? = null
) {
    fun onDataSuccess(dir: Directory) {
        data = dir
    }
}
