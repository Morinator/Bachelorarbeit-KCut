package bachelorthesis

import core.collections.CombinationIterator
import graphlib.algorithms.bipartite.cutSize
import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import org.paukov.combinatorics3.Generator

fun <V> solve(g: SimpleGraph<V>, k: Int): Solution<V> {
    var sol = Solution(emptySet<V>(), Int.MIN_VALUE)
    val combs = Generator.combination(g.vertices()).simple(k)

    for (c in combs) {
        val s = cutSize(g, c)
        if (s > sol.value)
            sol = Solution(c, s)
    }
    return sol
}

// TODO S als Name für Teilmenge Größe k verwenden

// TODO Außenrum Schleife für t hochzählen

// TODO solveBruteForce & solveClever auftrennen

// TODO Paper von Algo-AG lesen und schauen was umsetzbar -> Regel, wann Knoten rein / raus
//                                                        -> Fragen aufschreiben
// TODO save some results of brute-force in a file
fun <V> solveFast(g: SimpleGraph<V>, k: Int): Solution<V> {
    var sol = Solution(emptySet<V>(), Int.MIN_VALUE)
    val combs = CombinationIterator(g.vertices(), k)

    // TODO: Upper bound = sum of k highest degrees
    for (c in combs) {
        val s = cutSize(g, c)
        if (s > sol.value)
            sol = Solution(c, s)
    }
    return sol
}
