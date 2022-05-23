package personal.opensrcerer.bonkersmusic.audio

import android.media.MediaPlayer
import android.util.Log
import personal.opensrcerer.bonkersmusic.ui.dto.TrackPositionInfo

object AudioPlayerService {

    private var mediaPlayer: MediaPlayer? = null

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

    fun play(streamUrl: String) {
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

    fun pausePlayer() {
        if (mediaPlayer?.isPlaying == true) mediaPlayer?.pause()
    }

    fun stopPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        if (mediaPlayer != null) {
            mediaPlayer = null
        }
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