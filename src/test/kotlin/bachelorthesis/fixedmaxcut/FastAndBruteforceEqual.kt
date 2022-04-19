package bachelorthesis.fixedmaxcut

import bachelorthesis.solveBruteForce
import bachelorthesis.solveFast
import graphlib.constructors.GraphFileReader
import org.junit.jupiter.api.Test
import util.time.timeoutAsNull
import java.io.File
import kotlin.test.assertEquals

class FastAndBruteforceEqual {

    @Test
    fun allGraphFilesWithTimeout() {
        val graphFiles = File("data/graphs").walk().filter { it.isFile }
        val timeout = 1L // in seconds
        for (k in 1..3) {
            println("\n## k is $k ##")

            for (file in graphFiles) {
                val g = GraphFileReader.graphFromPath(file)
                timeoutAsNull({ assertEquals(solveBruteForce(g, k), solveFast(g, k)) }, timeout)
            }
        }
    }
}
