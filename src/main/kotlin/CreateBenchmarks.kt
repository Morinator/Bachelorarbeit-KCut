import solvers.*
import java.io.File
import java.lang.System.currentTimeMillis
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit.SECONDS

fun main(args: Array<String>) {
    val graphFile = args[0]
    for (solverID in listOf("a", "b", "c", "d", "e", "f")) {

        kcounter@ for (k in 1..30) {
            val g = graphFromPath(graphFile)
            var result: Pair<Set<Int>, Int>? = null
            val compFuture = CompletableFuture.supplyAsync {
                result = when (solverID) {
                    "a" -> ATreeSolver(g, k).opt()
                    "b" -> BUpperBoundSolver(g, k).opt()
                    "c" -> CHeuristikSolver(g, k, true).opt()
                    "d" -> DContSolver(g, k, true).opt()
                    "e" -> ESatNeedlessSolver(g, k, true).opt()
                    "f" -> FullStackSolver(g, k, true).opt()
                    else -> throw IllegalStateException("not a valid solverID")
                }
                true
            }
            val timer = Timer()
            val tBefore = currentTimeMillis()
            compFuture.completeOnTimeout(false, 60 * 60, SECONDS) // 1 hour
            val completed: Boolean = compFuture.get()
            timer.cancel()
            val (S, value) = result ?: Pair(emptySet(), 0)
            val usedSeconds =
                if (completed) ((currentTimeMillis() - tBefore) / 1000.0).toString() else "TIMEOUT"
            File("benchmarking_all_solvers").appendText(
                solverID.padEnd(3) +
                        graphFile.padEnd(60) +
                        g.vertexSet().size.toString().padEnd(10) +
                        g.edgeSet().size.toString().padEnd(10) +
                        k.toString().padEnd(10) +
                        value.toString().padEnd(10) +
                        usedSeconds.padEnd(10) +
                        S.toString() +
                        "\n"
            )
            println("added one line")
            if (!completed) break@kcounter
        }
    }

}

