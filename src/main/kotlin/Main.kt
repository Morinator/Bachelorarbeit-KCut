import solvers.FullStackSolver
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timerTask
import kotlin.math.max

fun main() {

    val g = graphFromPath("data/graphs/laplace_random_n32_m150_i3")
    val timeoutMS = (1000 * 3).toLong()
    var bla :  Pair<Set<Int>, Int>? = null

    val compFuture = CompletableFuture.supplyAsync {
        bla = FullStackSolver(g, k = 9, doHeuristic = false).opt()
        true
    }
    val runtime = Runtime.getRuntime()
    var memory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
    val timer = Timer()
    timer.scheduleAtFixedRate(timerTask {
        memory = max(memory, (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024))
    }, 0, 1000)
    compFuture.completeOnTimeout(false, timeoutMS, TimeUnit.MILLISECONDS)
    val completed = compFuture.get()
    timer.cancel()
    memory = max(memory, (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024))

    if (completed) {
        println(bla!!.first)
        println(bla!!.second)
    } else {
        println("Timeout")
    }
}