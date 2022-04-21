package graphlib.properties

import graphlib.datastructures.SimpleGraph

/**
 * @Runtime O( sum of degrees in [S] )  which is bounded by O( |S| * [g].delta )
 * @see <a href="https://en.wikipedia.org/wiki/Independent_set_(graph_theory)">Wikipedia page</a>
 * @return True iff [S] is an independent set of [g]
 */
fun <V> isIndependentSet(S: Set<V>, g: SimpleGraph<V>): Boolean {
    for (s in S)
        if (g[s].any { it in S })
            return false

    return true
}

/**
 * @see <a href="https://en.wikipedia.org/wiki/Vertex_cover">Wikipedia page</a>
 * @return True iff [S] is a vertex cover of [g]
 */
fun <V> isVertexCover(S: Set<V>, g: SimpleGraph<V>): Boolean {
    val x = HashSet(S) // copy-constructor
    for (s in S) { // add neighbours for all
        x.addAll(g[s])
    }

    return x.size == g.size
}

/**
 * Assumes that [S] is a subset of the vertices of [g].
 */
fun <V> cutSize(g: SimpleGraph<V>, S: Collection<V>): Int =
    S.sumOf { g[it].filter { nb -> nb !in S }.size }
