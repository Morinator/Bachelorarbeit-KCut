package bachelorthesis

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import util.collections.CombinationIterator

/**
 * @return A [Solution] of size [k] with a value of at least [t] if possible, or *null* otherwise.
 */
fun <V> solveFastDecision(g: SimpleGraph<V>, k: Int, t: Int): Solution<V>? {
    for (c in CombinationIterator(g.vertices(), k))
        if (cutSize(g, c) >= t) return Solution(c.toMutableSet(), cutSize(g, c))
    return null
}

fun <V> solveFastWithT(g: SimpleGraph<V>, k: Int): Solution<V> {
    var sol = Solution(mutableSetOf<V>(), Int.MIN_VALUE)

    val upperBound = g.degreeSequence.sorted().takeLast(k).sum() // sum of highest k degrees

    // TODO let t start at heuristic-value
    for (t in 1..upperBound) {

        val tmpResult = solveFastDecision(g, k, t)
        if (tmpResult == null)
            break
        else
            sol = tmpResult
    }

    return sol
}
