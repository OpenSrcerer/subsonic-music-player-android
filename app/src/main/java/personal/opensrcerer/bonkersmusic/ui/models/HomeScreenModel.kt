package personal.opensrcerer.bonkersmusic.ui.models

import androidx.lifecycle.ViewModel
import personal.opensrcerer.bonkersmusic.audio.AudioPlayerService
import personal.opensrcerer.bonkersmusic.ui.dto.TrackPositionInfo
import personal.opensrcerer.bonkersmusic.ui.util.Scheduler
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class HomeScreenModel : ViewModel() {
    private var scheduledJob: ScheduledFuture<*>? = null

    val minutesIn = Stateable(0)
    val minutesOut = Stateable(0)
    val secondsIn = Stateable("00")
    val secondsOut = Stateable("00")
    val sliderPos = Stateable(0f)

    fun onSongPlayed() {
        scheduledJob = Scheduler.executor.scheduleAtFixedRate(
            { setStateables(AudioPlayerService.getAudioData()) },
            1000,
            250,
            TimeUnit.MILLISECONDS
        )
    }

    private fun setStateables(trackInfo: TrackPositionInfo) {
        this.minutesIn to trackInfo.minutesIn
        this.minutesOut to trackInfo.minutesOut
        this.secondsIn to trackInfo.secondsIn
        this.secondsOut to trackInfo.secondsOut
        this.sliderPos to trackInfo.sliderPos
    }

    companion object {
        private var homeModel: HomeScreenModel? = null

        fun getHomeModel(): HomeScreenModel {
            if (homeModel == null) homeModel = HomeScreenModel()
            return homeModel!!
        }
    }
}