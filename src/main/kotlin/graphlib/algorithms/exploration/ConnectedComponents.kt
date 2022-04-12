package graphlib.algorithms.exploration

import graphlib.datastructures.SimpleGraph

fun <V> connectedComponent(g: SimpleGraph<V>, v: V): Set<V> =
    BFS_Level_Iterator(g, v).asSequence().flatMapTo(HashSet()) { it }

fun <V> listConnectedComponents(g: SimpleGraph<V>): List<Set<V>> {
    val explored = HashSet<V>()
    return ArrayList<Set<V>>().apply {
        for (v in g.vertices())
            if (v !in explored) {
                val c: Set<V> = connectedComponent(g, v)
                explored.addAll(c)
                add(c)
            }
    }
}

fun <V> checkIfConnected(g: SimpleGraph<V>): Boolean = connectedComponent(g, g.vertices().first()) == g.vertices()