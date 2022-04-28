package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution

abstract class AbstractSolver<V>(graph: SimpleGraph<V>, val k: Int) {
    val vertexList = graph.vertices().toList()

    enum class NextAction { UP, STAY, DOWN }

    var bestSolution = Solution(emptyList<V>(), -1)
}