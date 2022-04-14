package graphlib.algorithms.bipartite

import graphlib.datastructures.SimpleGraph

/**
 * Assumes that [S] is a subset of the vertices of [g].
 */
fun <V> cutSize(g: SimpleGraph<V>, S: Collection<V>) =
    S.sumOf { g[it].filter { nb -> nb !in S }.size }
