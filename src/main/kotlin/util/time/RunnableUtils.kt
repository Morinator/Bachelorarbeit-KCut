package util.time

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun runWithTimeout(task: Runnable, time: Long) {
    val myThread = Executors.newSingleThreadExecutor().submit { task.run() }

    try {
        myThread.get(time, TimeUnit.SECONDS)
    } catch (e: TimeoutException) {
        myThread.cancel(true)
    } finally {
        Executors.newSingleThreadExecutor().shutdownNow()
    }
}
