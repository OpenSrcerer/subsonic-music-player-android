package personal.opensrcerer.bonkersmusic.ui.models

import androidx.lifecycle.ViewModel
import personal.opensrcerer.bonkersmusic.server.client.SubsonicService
import personal.opensrcerer.bonkersmusic.server.requests.browsing.IndexesRequest
import personal.opensrcerer.bonkersmusic.server.responses.browsing.Indexes
import java.time.Duration
import java.time.temporal.ChronoUnit

class BrowseScreenModel : ViewModel() {
    val indexes = Stateable(Indexes.empty())

    init {
        SubsonicService
            .request(IndexesRequest())
            .timeout(Duration.of(3000, ChronoUnit.MILLIS))
            .doOnCancel { /* timeout handling */ }
            .subscribe { idxs -> indexes to idxs }
    }

    companion object {
        private var browseModel: BrowseScreenModel? = null

        fun getBrowseModel(): BrowseScreenModel {
            if (browseModel == null) browseModel = BrowseScreenModel()
            return browseModel!!
        }
    }
}