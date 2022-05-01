package bachelorthesis

import bachelorthesis.solvers.ValueWrapper
import bachelorthesis.solvers.LibSolver
import graphlib.constructors.GraphIO
import java.io.File


/**
 * This shouldn't have to be run often, because once there results are saved in a file,
 * we can just use them from there.
 */
fun main() {
    val graphFiles = File("data/graphs").walk().filter { it.isFile }
    for (k in 1..3) {
        for (file in graphFiles) {
            val g = GraphIO.graphFromPath(file)
            val sol = ValueWrapper(g, k, LibSolver()).calc()

            val graphAndK = file.toString().padEnd(60) + k.toString().padEnd(10)
            val logFile = File("maxcut_results")

            if (!logFile.readLines().any { it.startsWith(file.toString().padEnd(60) + k.toString().padEnd(10)) }) {
                logFile.appendText(graphAndK + (sol.value.toString().padEnd(20) + sol.vertices + "\n"))
            }
        }
    }
}