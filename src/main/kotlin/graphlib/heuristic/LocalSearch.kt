package graphlib.heuristic

import bachelorthesis.solvers.cutSize
import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution

fun <VType> heuristic(G: SimpleGraph<VType>, k: Int, runs: Int) =
    (1..runs).map { localSearchRun(G, k) }.maxByOrNull { it.value }!!

fun <VType> localSearchRun(G: SimpleGraph<VType>, k: Int): Solution<VType> {

    if (G.size < k) return Solution(listOf(), 0) // invalid input

    val randomVertices = G.V.toList().shuffled().take(k)
    val sol = Solution(randomVertices, cutSize(G, randomVertices))

    while (true) {
        val oldVal = sol.value
        localSearchStep(G, sol, ::cutSize)
        if (oldVal == sol.value)
            break
    }

    return sol
}

/**
 * explores the 1-swap-neighbourhood of [s] and if it finds a better neighbour, it stops immediatly (i.e. doesn't
 * search for the best candidate, but stops at the first that is better).
 * Note that no new object is returned; only [s] is modified to represent a new subset.
 */
fun <VType> localSearchStep(G: SimpleGraph<VType>, s: Solution<VType>, f: (SimpleGraph<VType>, Collection<VType>) -> Int) {
    for (v in s.V.toList()) { // needs a copy to prevent ConcurrentModificationException
        s.V.remove(v)

        for (w in G.V.filter { it !in s.V }) {
            s.V.add(w)

            if (f(G, s.V) > s.value) {
                s.value = f(G, s.V)
                return
            }
            s.V.remove(w)
        }
        s.V.add(v)
    }
}
