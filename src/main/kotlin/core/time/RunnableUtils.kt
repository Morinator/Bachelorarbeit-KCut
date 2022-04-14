package core.time

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun timeoutAsNull(task: Runnable, time: Long) {
    val checkerThread = Executors.newSingleThreadExecutor().submit { task.run() }

    try {
        checkerThread.get(time, TimeUnit.SECONDS)
        println("successful")
    } catch (e: TimeoutException) {
        checkerThread.cancel(true)
        println("timed out")
    } finally {
        Executors.newSingleThreadExecutor().shutdownNow()
    }
}
