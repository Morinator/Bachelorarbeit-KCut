package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph

abstract class AbstractSolver<V>(graph: SimpleGraph<V>, val k: Int) {
    val vertexList = graph.vertices().toList()

    enum class NextAction { UP, STAY, DOWN }

    lateinit var bestIndices  : IntArray
}