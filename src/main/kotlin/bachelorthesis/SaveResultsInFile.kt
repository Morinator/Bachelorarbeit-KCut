package bachelorthesis

import bachelorthesis.solvers.CompleteLibSolver
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
            val sol = CompleteLibSolver(g,k).calc()

            val logFile = File("maxcut_results_40graphs")

            logFile.appendText(
                file.toString().padEnd(55) +
                        k.toString().padEnd(10) +
                        (sol.value.toString().padEnd(13) +
                                (System.currentTimeMillis() - timeBefore).toString().padEnd(8) +
                                sol.vertices + "\n")
            )
        }
    }
}
