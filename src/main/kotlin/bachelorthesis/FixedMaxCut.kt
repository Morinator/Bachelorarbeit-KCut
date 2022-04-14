package bachelorthesis

import core.collections.CombinationIterator
import graphlib.algorithms.bipartite.cutSize
import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import org.paukov.combinatorics3.Generator

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
