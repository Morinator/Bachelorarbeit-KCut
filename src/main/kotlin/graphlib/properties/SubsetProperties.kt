package graphlib.properties

import graphlib.datastructures.SimpleGraph

/**
 * Assumes that [S] is a subset of the vertices of [G].
 */
fun <VType> cutSize(G: SimpleGraph<VType>, S: Collection<VType>): Int =
    S.sumOf { v -> G[v].count { it !in S } }
