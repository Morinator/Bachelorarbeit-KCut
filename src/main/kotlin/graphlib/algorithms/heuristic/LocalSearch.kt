package graphlib.algorithms.heuristic

import graphlib.algorithms.bipartite.cutSize
import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution

fun <V> getHeuristic(g: SimpleGraph<V>, k: Int): Solution<V> {

    val randomVertices = g.vertices().toList().shuffled().take(k)
    val sol = Solution(randomVertices, cutSize(g, randomVertices))

    while (true) {
        val oldVal = sol.value
        localSearchStep(g, sol)
        if (oldVal == sol.value)
            break
    }

    return sol
}

/**
 * explores the 1-neighbourhood of [solution] and if it finds a better neighbour, it stops.
 * Note that no new object is returned; only [solution] is modified to represent a new subset.
 */
fun <V> localSearchStep(g: SimpleGraph<V>, solution: Solution<V>) {
    for (dropVertex in solution.vertices.toList()) { // needs a copy to prevent ConcurrentModificationException
        solution.vertices.remove(dropVertex)

        for (newVertex in g.vertices().filter { it !in solution.vertices }) {
            solution.vertices.add(newVertex)

            if (cutSize(g, solution.vertices) > solution.value) {
                solution.value = cutSize(g, solution.vertices)
                return
            }
            solution.vertices.remove(newVertex)
        }
        solution.vertices.add(dropVertex)
    }
}
