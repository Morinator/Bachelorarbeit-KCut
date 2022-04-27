package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph

abstract class Algo<V>(graph: SimpleGraph<V>, val k: Int) {
    val vertexList = graph.vertices().toList()

    enum class NextAction { UP, STAY, DOWN }

    var checkedSubsets = 0

    var bestIndices = IntArray(0)
}