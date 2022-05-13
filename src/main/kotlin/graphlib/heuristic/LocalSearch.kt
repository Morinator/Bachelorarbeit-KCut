package graphlib.heuristic

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize

fun <V> runHeuristic(g: SimpleGraph<V>, k: Int, runs : Int) =
    (1..runs).map { localSearchRun(g, k) }.maxByOrNull { it.value }!!

fun <V> localSearchRun(g: SimpleGraph<V>, k: Int): Solution<V> {

    if (g.size < k) return Solution() // invalid input

    val randomVertices = g.vertices().toList().shuffled().take(k)
    val sol = Solution(randomVertices, cutSize(g, randomVertices))

    while (true) {
        val oldVal = sol.value
        localSearchStep(g, sol, ::cutSize)
        if (oldVal == sol.value)
            break
    }

    return sol
}

/**
 * explores the 1-neighbourhood of [solution] and if it finds a better neighbour, it stops.
 * Note that no new object is returned; only [solution] is modified to represent a new subset.
 */
fun <V> localSearchStep(g: SimpleGraph<V>, solution: Solution<V>, objective: (SimpleGraph<V>, Collection<V>) -> Int) {
    for (dropVertex in solution.vertices.toList()) { // needs a copy to prevent ConcurrentModificationException
        solution.vertices.remove(dropVertex)

        for (newVertex in g.vertices().filter { it !in solution.vertices }) {
            solution.vertices.add(newVertex)

            if (cutSize(g, solution.vertices) > solution.value) {
                solution.value = objective(g, solution.vertices)
                return
            }
            solution.vertices.remove(newVertex)
        }
        solution.vertices.add(dropVertex)
    }
}
