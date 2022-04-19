package bachelorthesis

import graphlib.algorithms.bipartite.cutSize
import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import org.paukov.combinatorics3.Generator
import util.collections.CombinationIterator

fun <V> solveBruteForce(g: SimpleGraph<V>, k: Int): Solution<V> {
    var sol = Solution(mutableSetOf<V>(), Int.MIN_VALUE)
    val combinations = Generator.combination(g.vertices()).simple(k)

    for (S in combinations) {
        val s = cutSize(g, S)
        if (s > sol.value)
            sol = Solution(S, s)
    }
    return sol
}

// TODO Außenrum Schleife für t hochzählen

// TODO Paper von Algo-AG lesen und schauen was umsetzbar -> Regel, wann Knoten rein / raus
//                                                        -> Fragen aufschreiben
// TODO save some results of brute-force in a file

fun <V> solveFast(g: SimpleGraph<V>, k: Int): Solution<V> {
    var sol = Solution(mutableSetOf<V>(), Int.MIN_VALUE)
    val combinationIterator = CombinationIterator(g.vertices(), k)

    val upperBound = g.degrees.sorted().takeLast(k).sum() // sum of highest k degrees

    while (combinationIterator.hasNext() && sol.value < upperBound) {
        val c = combinationIterator.next()
        val s = cutSize(g, c)
        if (s > sol.value) {
            sol = Solution(c.toMutableSet(), s)
        }
    }
    return sol
}

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

    val upperBound = g.degrees.sorted().takeLast(k).sum() // sum of highest k degrees
    for (t in 0..upperBound) {
        val tmpResult = solveFastDecision(g, k, t)
        if (tmpResult == null)
            break
        else
            sol = tmpResult
    }

    return sol
}
