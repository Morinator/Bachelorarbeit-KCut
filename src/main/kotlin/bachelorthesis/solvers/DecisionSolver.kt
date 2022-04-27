package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import util.collections.CombinationIterator

class DecisionSolver {

    /**
     * @return A [Solution] of size [k] with a value of at least [t] if possible, or *null* otherwise.
     */
    fun <V> run(g: SimpleGraph<V>, k: Int, t: Int): Solution<V>? {
        for (c in CombinationIterator(g.vertices(), k))
            if (cutSize(g, c) >= t) return Solution(c.toMutableSet(), cutSize(g, c))
        return null
    }
}