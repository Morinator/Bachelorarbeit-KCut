package bachelorthesis

import bachelorthesis.solvers.LibSolver
import bachelorthesis.solvers.ValueWrapper
import graphlib.constructors.GraphIO
import java.io.File

/**
 * This shouldn't have to be run often, because once there results are saved in a file,
 * we can just use them from there.
 */
fun main() {
    val graphFiles = File("data/graphs").walk().filter { it.isFile }
    for (k in 1..6) {
        for (file in graphFiles) {
            val timeBefore = System.currentTimeMillis()
            val g = GraphIO.graphFromPath(file)
            val sol = ValueWrapper(g, k, LibSolver()).calc()

            val logFile = File("maxcut_results2")

            logFile.appendText(
                file.toString().padEnd(40) +
                        k.toString().padEnd(4) +
                        (sol.value.toString().padEnd(10) +
                                (System.currentTimeMillis() - timeBefore).toString().padEnd(8) +
                                sol.vertices + "\n")
            )
        }
    }
}
