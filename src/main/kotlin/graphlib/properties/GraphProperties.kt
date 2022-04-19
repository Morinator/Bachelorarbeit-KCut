package graphlib.properties

import graphlib.algorithms.exploration.checkIfConnected
import graphlib.datastructures.SimpleGraph
import java.lang.RuntimeException

/**
 * @return True iff [g] is a tree, which is equivalent to being connected and cycle-free.
 */
fun <V> checkIfTree(g: SimpleGraph<V>) = checkIfConnected(g) && g.edgeCount == g.size - 1

fun <V> hIndex(g: SimpleGraph<V>): Int {
    val arr = IntArray(g.delta + 1) // Indices are: 0, 1, ...,delta-1, delta

    for (v in g.vertices())
        arr[g.degreeOf(v)] += 1 // arr[i] should contain the number of vertices with degree of exactly i

    for (i in 0 until g.delta)
        arr[i] += arr[i + 1] // arr[i] should contain the number of vertices with degree of at least i

    for (i in arr.indices.reversed())
        if (arr[i] >= i) // check if there are at least i vertices with degree of at least i
            return i

    throw RuntimeException("Shouldn't reach this part as the h-index is always at least 0 per definition")
}
