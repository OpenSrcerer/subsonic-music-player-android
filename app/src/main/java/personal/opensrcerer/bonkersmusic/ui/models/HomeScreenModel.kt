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
    val songLoaded = Stateable(false)
    val songIsPlaying = Stateable(false)

    fun onPlayJob() {
        scheduledJob?.cancel(true)
        scheduledJob = Scheduler.executor.scheduleAtFixedRate(
            { setStateables(AudioPlayerService.getAudioData()) },
            1000,
            250,
            TimeUnit.MILLISECONDS
        )
        songLoaded changeTo true
        songIsPlaying changeTo true
    }

    fun onPause() {
        scheduledJob?.cancel(true)
        songIsPlaying changeTo false
    }

    private fun setStateables(trackInfo: TrackPositionInfo) {
        this.minutesIn changeTo trackInfo.minutesIn
        this.minutesOut changeTo trackInfo.minutesOut
        this.secondsIn changeTo trackInfo.secondsIn
        this.secondsOut changeTo trackInfo.secondsOut
        this.sliderPos changeTo trackInfo.sliderPos
    }

    companion object {
        private var homeModel: HomeScreenModel? = null

        fun getHomeModel(): HomeScreenModel {
            if (homeModel == null) homeModel = HomeScreenModel()
            return homeModel!!
        }
    }
}