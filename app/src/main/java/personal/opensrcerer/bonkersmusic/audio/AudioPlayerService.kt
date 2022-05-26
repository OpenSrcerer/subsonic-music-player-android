package personal.opensrcerer.bonkersmusic.audio

import android.media.MediaPlayer
import android.util.Log
import personal.opensrcerer.bonkersmusic.server.requests.RequestUtils
import personal.opensrcerer.bonkersmusic.server.requests.media.StreamRequest
import personal.opensrcerer.bonkersmusic.server.responses.browsing.Directory
import personal.opensrcerer.bonkersmusic.ui.dto.TrackPositionInfo
import personal.opensrcerer.bonkersmusic.ui.models.HomeScreenModel

object AudioPlayerService {

    private var mediaPlayer: MediaPlayer? = null
    private var currentSong: Directory.Child? = null

    fun getAudioData(): TrackPositionInfo {
        var dur: Int? = null
        var pos: Int? = null

        if (mediaPlayer?.isPlaying == true) {
            dur = mediaPlayer?.duration
            pos = mediaPlayer?.currentPosition
        }

        return if (dur == null || pos == null)
                TrackPositionInfo()
            else
                TrackPositionInfo(dur, pos)
    }

    fun play(song: Directory.Child) {
        val streamUrl = RequestUtils.getUrl(
            StreamRequest(
            song.id, "320"
        )).toString()

        currentSong = song
        HomeScreenModel.getHomeModel().onPlayJob()

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
        mediaPlayer!!.setOnErrorListener { player, what, extra -> onPlayError(player) }
        mediaPlayer!!.setOnPreparedListener { player -> onPrepared(player) }
        mediaPlayer!!.setOnCompletionListener { player -> onCompletion(player) }
        mediaPlayer!!.setOnBufferingUpdateListener { player, what -> onBufferingUpdate(player, what) }

        mediaPlayer!!.setDataSource(streamUrl)
        mediaPlayer!!.prepareAsync()
        mediaPlayer!!.playbackParams = mediaPlayer!!.playbackParams.setSpeed(1f)
    }

    fun stopPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        if (mediaPlayer != null) {
            mediaPlayer = null
        }
    }
    fun togglePause() {
        if (mediaPlayer?.isPlaying != true) {
            mediaPlayer?.start()
            HomeScreenModel.getHomeModel().onPlayJob()
        } else {
            HomeScreenModel.getHomeModel().onPause()
            mediaPlayer?.pause()
        }

    }

    fun hasSong(): Boolean {
        return currentSong != null
    }

    fun getCurrentSong(): Directory.Child {
        return currentSong ?: Directory.Child()
    }

    // Listeners
    private fun onPrepared(player: MediaPlayer) {
        player.start()
    }

    private fun onBufferingUpdate(player: MediaPlayer, progress: Int): Boolean {
        Log.w("MediaBuffering", "$progress%")
        if (progress > 25) {
            player.start()
        }
        return false
    }

    private fun onCompletion(player: MediaPlayer) {

    }

    private fun onPlayError(player: MediaPlayer): Boolean {
        return false
    }
}