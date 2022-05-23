package personal.opensrcerer.bonkersmusic.ui.dto

class TrackPositionInfo(
    dur: Int?, // Duration of media in ms
    currPos: Int? // Current position of media in ms
) {
    // All values in seconds
    val minutesIn: Int
    val minutesOut: Int
    val secondsIn: String
    val secondsOut: String
    val sliderPos: Float

    constructor() : this(null, null)

    init {
        if (dur != null && currPos != null) {
            val remainingTime = (dur - currPos) / 1000
            val secIn = ((currPos / 1000) % 60).toString()
            val secOut = (remainingTime % 60).toString()

            minutesIn = (currPos / 1000) / 60
            minutesOut = remainingTime / 60
            sliderPos = currPos.toFloat() / dur

            secondsIn = if (secIn.length == 1) "0".plus(secIn) else secIn
            secondsOut = if (secOut.length == 1) "0".plus(secOut) else secOut
        } else {
            minutesIn = 0
            minutesOut = 0
            secondsIn = "00"
            secondsOut = "00"
            sliderPos = 0f
        }
    }
}
