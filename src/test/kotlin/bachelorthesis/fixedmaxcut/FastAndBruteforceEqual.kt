package bachelorthesis.fixedmaxcut

import bachelorthesis.solveBruteForce
import bachelorthesis.solveFastWithT
import graphlib.constructors.GraphFileReader
import org.junit.jupiter.api.Test
import util.time.timeoutAsNull
import java.io.File
import kotlin.test.assertEquals

class FastAndBruteforceEqual {

    private val timeout = 2L // in seconds

    @Test
    fun withTimeoutSolveFastWithT() {
        val graphFiles = File("data/graphs").walk().filter { it.isFile }
        for (k in 1..3) {
            println("\n## k is $k ##")
            for (file in graphFiles) {
                val g = GraphFileReader.graphFromPath(file)
                timeoutAsNull({ assertEquals(solveBruteForce(g, k), solveFastWithT(g, k)) }, timeout)
            }
        }
    }
}
