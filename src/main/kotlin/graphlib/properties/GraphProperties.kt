package graphlib.properties

import graphlib.datastructures.SimpleGraph
import graphlib.exploration.checkIfConnected
import org.paukov.combinatorics3.Generator

/**
 * @return True iff [G] is a tree, which is equivalent to being connected and cycle-free.
 */
fun <VType> isTree(G: SimpleGraph<VType>) = checkIfConnected(G) && G.edgeCount == G.size - 1

/**
 * Runtime: O([G].maxDegree)
 *
 * @return The h-Index of [G], i.e. the biggest natural number *h* so that there are at
 * least *h* vertices with degree of at least *h*
 */
fun <VType> hIndex(G: SimpleGraph<VType>): Int {
    val arr = IntArray(G.maxDegree + 1) // Indices are: 0, 1, ...,maxDegree-1, maxDegree

    for (v in G.V)
        arr[G.degreeOf(v)] += 1 // arr[i] should contain the number of vertices with degree of exactly i

    for (i in 0 until G.maxDegree)
        arr[i] += arr[i + 1] // arr[i] should contain the number of vertices with degree of at least i

    for (i in arr.indices.reversed())
        if (arr[i] >= i) // check if there are at least i vertices with degree of at least i
            return i

    throw RuntimeException("Shouldn't reach this part as the h-index is always at least 0 per definition")
}

/**
 * @see <a href="https://arxiv.org/pdf/1804.07431.pdf">Introductory paper</a>
 * Reminder/Note: The higher [c] is, the more likely graphs are to be [c].closed.
 * Note: "Triadic closure" is the special case of c=1.
 * @return True iff for any two distinct vertices v, w with at least c common neighbours, (v, w) is an edge
 */
fun <VType> `is c closed`(G: SimpleGraph<VType>, c: Int): Boolean {
    for (pair in Generator.combination(G.V).simple(2)) {
        val (v, w) = pair.toList()

        if ((G[v] intersect G[w]).size >= c && !G.areNB(v, w)) // just the definition of c-closure
            return false // found a bad pair
    }
    return true
}
