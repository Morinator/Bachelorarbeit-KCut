package graphlib.properties

import graphlib.datastructures.SimpleGraph

fun <V> isIndependentSet(S: Set<V>, g: SimpleGraph<V>): Boolean {
    for (s in S)
        if (g[s].any { it in S })
            return false

    return true
}

fun <V> isVertexCover(S: Set<V>, g: SimpleGraph<V>): Boolean {
    val x = HashSet(S) // copy-constructor
    for (s in S) { // add neighbours for all
        x.addAll(g[s])
    }

    return x.size == g.size
}
