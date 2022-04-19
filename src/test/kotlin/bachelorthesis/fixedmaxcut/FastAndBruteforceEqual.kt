package bachelorthesis.fixedmaxcut

import bachelorthesis.solveBruteForce
import bachelorthesis.solveFast
import graphlib.constructors.GraphFileReader
import org.junit.jupiter.api.Test
import util.time.timeoutAsNull
import java.io.File
import java.util.concurrent.Executors.newSingleThreadExecutor
import java.util.concurrent.TimeUnit.SECONDS
import java.util.concurrent.TimeoutException
import kotlin.test.assertEquals

class FastAndBruteforceEqual {

    @Test
    fun allGraphFilesWithTimeout() {
        val graphFiles = File("data/graphs").walk().filter { it.isFile }
        for (k in 1..3) {
            println("\n## k is $k ##")

            for (file in graphFiles) {
                val g = GraphFileReader.graphFromPath(file)

                val task = { // compare both and timeout if needed

                    val checkerThread = newSingleThreadExecutor().submit {
                        assertEquals(solveBruteForce(g, k), solveFast(g, k))
                    }

                    try {
                        checkerThread.get(3, SECONDS)
                        println("successful")
                    } catch (e: TimeoutException) {
                        checkerThread.cancel(true)
                        println("timed out")
                    } finally {
                        newSingleThreadExecutor().shutdownNow()
                    }
                }
                timeoutAsNull(task, 3)
            }
        }
    }
}
