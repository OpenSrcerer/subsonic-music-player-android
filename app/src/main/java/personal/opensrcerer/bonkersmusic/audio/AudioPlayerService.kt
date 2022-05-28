/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.audio

import android.media.MediaPlayer
import personal.opensrcerer.bonkersmusic.server.client.RequestUtils
import personal.opensrcerer.bonkersmusic.server.requests.media.StreamRequest
import personal.opensrcerer.bonkersmusic.server.responses.browsing.Directory
import personal.opensrcerer.bonkersmusic.ui.dto.TrackPositionInfo
import personal.opensrcerer.bonkersmusic.ui.models.HomeScreenModel

// Service to play audio on the device
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
        mediaPlayer!!.setOnPreparedListener { player -> onPrepared(player) }
        mediaPlayer!!.setOnCompletionListener { onFinish() }

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

    fun seekTo(float: Float): TrackPositionInfo {
        togglePause()
        val seekValue = ((mediaPlayer?.duration ?: 0) * float).toInt()
        mediaPlayer?.seekTo(seekValue)
        togglePause()

        return TrackPositionInfo(mediaPlayer?.duration ?: 0, seekValue)
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

    private fun onFinish() {
        HomeScreenModel.getHomeModel().onFinish()
    }
}