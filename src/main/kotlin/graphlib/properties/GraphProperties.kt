package graphlib.properties

import graphlib.exploration.checkIfConnected
import graphlib.datastructures.SimpleGraph
import java.lang.RuntimeException

/**
 * @return True iff [g] is a tree, which is equivalent to being connected and cycle-free.
 */
fun <V> isTree(g: SimpleGraph<V>) = checkIfConnected(g) && g.edgeCount == g.size - 1

/**
 * Runtime: O([g].maxDegree)
 *
 * @return The h-Index of [g], i.e. the biggest natural number *h* so that there are at
 * least *h* vertices with degree of at least *h*
 */
fun <V> hIndex(g: SimpleGraph<V>): Int {
    val arr = IntArray(g.maxDegree + 1) // Indices are: 0, 1, ...,maxDegree-1, maxDegree

    for (v in g.vertices())
        arr[g.degreeOf(v)] += 1 // arr[i] should contain the number of vertices with degree of exactly i

    for (i in 0 until g.maxDegree)
        arr[i] += arr[i + 1] // arr[i] should contain the number of vertices with degree of at least i

    for (i in arr.indices.reversed())
        if (arr[i] >= i) // check if there are at least i vertices with degree of at least i
            return i

    throw RuntimeException("Shouldn't reach this part as the h-index is always at least 0 per definition")
}
