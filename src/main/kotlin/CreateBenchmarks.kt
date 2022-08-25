import java.io.File
import java.lang.System.currentTimeMillis
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit.SECONDS

fun main(args: Array<String>) {
    val graphFile = args[0]

        kcounter@ for (k in 1..30) {
            val g = graphFromPath(graphFile)
            var result: Pair<Set<Int>, Int>? = null
            val compFuture = CompletableFuture.supplyAsync {
                result = ILPSolver(g,k).calc()
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
            File("benchmarking_ilp_no_kernel").appendText(
                "ILP" +
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
        }


}

