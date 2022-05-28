/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.ui.models

import androidx.lifecycle.ViewModel
import personal.opensrcerer.bonkersmusic.server.client.SubsonicService
import personal.opensrcerer.bonkersmusic.server.requests.browsing.DirectoryRequest
import personal.opensrcerer.bonkersmusic.server.requests.browsing.IndexesRequest
import personal.opensrcerer.bonkersmusic.server.responses.browsing.Indexes
import personal.opensrcerer.bonkersmusic.ui.dto.BrowseScreenType
import personal.opensrcerer.bonkersmusic.ui.dto.ChildPage
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*

class BrowseScreenModel : ViewModel() {

    // --- Dir Browser ---
    private val contextStack: Stack<ChildPage> = Stack()
    val currPageType: Stateable<BrowseScreenType> = Stateable(BrowseScreenType.LOADING)

    // --- Main Browse Page ---
    val artists = Stateable(Indexes.empty())

    init {
        getArtists()
    }

    // --- Directory Navigation ---
    fun currChildPage(): ChildPage? {
        if (contextStack.empty()) {
            return null
        }
        return contextStack.peek()
    }

    fun downDir(newDirId: String) {
        currPageType changeTo BrowseScreenType.LOADING
        val child = ChildPage(this)
        getChildren(newDirId, child)
        contextStack.push(child)
    }

    fun upDir() {
        currPageType changeTo BrowseScreenType.LOADING
        if (!contextStack.empty()) {
            contextStack.pop()
            if (!contextStack.empty()) {
                currPageType changeTo (contextStack.peek().data?.getType() ?: BrowseScreenType.NO_DATA)
            } else {
                currPageType changeTo BrowseScreenType.BROWSE
            }
        }
    }

    // --- Interface With Server ---
    private fun getArtists() {
        SubsonicService
            .request(IndexesRequest())
            .timeout(Duration.of(5000, ChronoUnit.MILLIS))
            .doOnError { currPageType changeTo BrowseScreenType.NO_DATA }
            .subscribe { idxs ->
                artists changeTo idxs
                currPageType changeTo BrowseScreenType.BROWSE
            }
    }

    private fun getChildren(
        dirId: String,
        childPage: ChildPage
    ) {
        SubsonicService
            .request(DirectoryRequest(mapOf(
                Pair("id", dirId)
            )))
            .timeout(Duration.of(5000, ChronoUnit.MILLIS))
            .doOnError { currPageType changeTo BrowseScreenType.NO_DATA }
            .subscribe {
                childPage.onDataSuccess(it)
                currPageType changeTo it.getType()
            }
    }

    companion object {
        private var browseModel: BrowseScreenModel? = null

        fun getBrowseModel(): BrowseScreenModel {
            if (browseModel == null) browseModel = BrowseScreenModel()
            return browseModel!!
        }
    }
}