package graphlib.algorithms.bipartite

import graphlib.datastructures.SimpleGraph

/**
 * Assumes that [side] is a subset of the vertices of [g].
 */
fun <V> cutSize(g: SimpleGraph<V>, side: Collection<V>) =
    side.sumOf { g[it].filter { nb -> nb !in side }.size }