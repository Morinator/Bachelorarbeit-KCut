package graphlib.algorithms.heuristic

import graphlib.algorithms.bipartite.cutSize
import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution

// TODO isnt used yet :/
fun <V> getMaxcutLocalSearch(g: SimpleGraph<V>, k: Int): Solution<V> {

    // get random initial solution
    val randomVertices = g.vertices().toList().shuffled().take(k)
    var curr = Solution(randomVertices, cutSize(g, randomVertices))

    repeat(k) { // we need maximally *k* updates, maybe less. More strict check coming sometime :)

        for (dropVertex in g.vertices()) {
            curr.vertices.remove(dropVertex)

            for (vertexToAdd in g.vertices()) {
                // todo logic
            }

            curr.vertices.add(dropVertex)
        }
    }

    return curr
}

fun <V> localSearchStep(g: SimpleGraph<V>, solution: Solution<V>) {
    for (dropVertex in solution.vertices) {
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
