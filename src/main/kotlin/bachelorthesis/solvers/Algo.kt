package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph

abstract class Algo<V>(graph: SimpleGraph<V>, k: Int) {
    val vList = graph.vertices().toList()
}