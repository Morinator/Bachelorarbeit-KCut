package bachelorthesis

import bachelorthesis.solvers.BruteforceSolver
import bachelorthesis.solvers.cutSize
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
            val G = GraphIO.graphFromPath(file)
            val sol = BruteforceSolver(G, k).calc()

            val logFile = File("maxcut_results_with_paths_laplace2")

            logFile.appendText(
                file.name.padEnd(60) +
                        k.toString().padEnd(10) +
                        cutSize(G,sol).toString().padEnd(13) +
                        (System.currentTimeMillis() - timeBefore).toString().padEnd(10) +
                        sol + "\n"

            )
        }
    }
}
