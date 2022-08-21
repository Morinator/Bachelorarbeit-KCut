import java.io.File
import java.lang.System.currentTimeMillis
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit.SECONDS

fun main() {
    val file = File("benchmarkGraphs/ENZYMES_g295.edges")
    for (k in 1..30) {

        val g = graphFromPath(file.toString())
        var result: Pair<Set<Int>, Int>? = null
        val compFuture = CompletableFuture.supplyAsync {
            result = StackSolver(g, k, doHeuristic = true).opt()
            true
        }
        val timer = Timer()
        val tBefore = currentTimeMillis()
        compFuture.completeOnTimeout(false, 60, SECONDS)
        val completed: Boolean = compFuture.get()
        timer.cancel()
        val (S, value) = result ?: Pair(emptySet(), 0)
        val usedSeconds =
            if (completed) ((currentTimeMillis() - tBefore) / 1000.0).toString() else "TIMEOUT"
        File("benchmarking_august_21").appendText(
            file.name.toString().padEnd(60) +
                    k.toString().padEnd(10) +
                    value.toString().padEnd(10) +
                    usedSeconds.padEnd(10) +
                    S.toString() +
                    "\n"
        )
        println("added one line")
        if (!completed) break
    }
}

