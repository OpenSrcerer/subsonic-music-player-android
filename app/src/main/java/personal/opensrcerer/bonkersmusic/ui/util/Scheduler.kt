package personal.opensrcerer.bonkersmusic.ui.util

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

object Scheduler {
    val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(2)
}