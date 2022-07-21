package graphlib.exploration

import graphlib.datastructures.SimpleGraph

fun <VType> connectedComponent(G: SimpleGraph<VType>, v: VType): Set<VType> =
    BFS_Level_Iterator(G, v).asSequence().flatMapTo(HashSet()) { it }

fun <VType> listConnectedComponents(G: SimpleGraph<VType>): List<Set<VType>> {
    val explored = HashSet<VType>()
    return ArrayList<Set<VType>>().apply {
        for (v in G.V)
            if (v !in explored) {
                val c: Set<VType> = connectedComponent(G, v)
                explored.addAll(c)
                add(c)
            }
    }
}

fun <V> checkIfConnected(G: SimpleGraph<V>): Boolean = connectedComponent(G, G.V.first()) == G.V
