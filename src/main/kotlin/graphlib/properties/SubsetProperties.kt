package graphlib.properties

import graphlib.datastructures.SimpleGraph
import org.paukov.combinatorics3.Generator
import util.collections.unionAll

/**
 * @Runtime O( sum of degrees in [S] )  which is bounded by O( |S| * [G].maxDegree )
 * @see <a href="https://en.wikipedia.org/wiki/Independent_set_(graph_theory)">Wikipedia page</a>
 * @return True iff [S] is an independent set of [G]
 */
fun <V> isIndependentSet(S: Set<V>, G: SimpleGraph<V>): Boolean {
    for (s in S)
        if (G[s].any { it in S })
            return false

    return true
}

/**
 * @see <a href="https://en.wikipedia.org/wiki/Vertex_cover">Wikipedia page</a>
 * @return True iff [S] is a vertex cover of [G]
 */
fun <VType> isVertexCover(S: Set<VType>, G: SimpleGraph<VType>): Boolean {
    return (S union unionAll(S.map { G[it] })).size == G.size
}

/**
 * @see <a href="https://en.wikipedia.org/wiki/Dominating_set">Wikipedia page</a>
 * @return True iff [S] is a dominating set of [G]
 */
fun <VType> isDominatingSet(S: Set<VType>, G: SimpleGraph<VType>): Boolean =
    HashSet(S).apply {
        for (v in S)
            addAll(G[v])
    }.size == G.size

fun <VType> isClique(S: Set<VType>, G: SimpleGraph<VType>): Boolean {
    for ((v, w) in Generator.combination(S).simple(2)) {
        if (!G.areNB(v, w))
            return false
    }
    return true
}

/**
 * @see <a href="https://tcs.rwth-aachen.de/~langer/pub/partial-vc-wg08.pdf">Definition in paper</a>
 * @return How many edges are covered by [S]
 */
fun <VType> countCoveredEdges(S: Set<VType>, G: SimpleGraph<VType>): Int {
    var counter = 0
    // don't count edges with both ends in S twice
    for (v in S)
        for (w in G[v])
            if (w !in S || (w in S && v.hashCode() < w.hashCode()))
                counter++
    return counter
}

/**
 * Assumes that [S] is a subset of the vertices of [G].
 */
fun <VType> cutSize(G: SimpleGraph<VType>, S: Collection<VType>): Int =
    S.sumOf { v -> G[v].count { it !in S } }
