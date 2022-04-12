package bachelorthesis

import core.collections.CombinationIterator
import graphlib.algorithms.bipartite.cutSize
import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution


fun <V> solve(g: SimpleGraph<V>, k: Int): Solution<V> {
    var sol = Solution(emptySet<V>(), Int.MIN_VALUE)
    val combs = CombinationIterator(g.vertices(), k)

    for (c in combs) {
        val s = cutSize(g, c)
        if (s > sol.value)
            sol = Solution(c, s)
    }
    return sol
}
