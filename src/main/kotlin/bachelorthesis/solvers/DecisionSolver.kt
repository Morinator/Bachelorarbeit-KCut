package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import util.collections.CombinationIterator

class DecisionSolver<V>(protected val g: SimpleGraph<V>, val k: Int) : Algo<V>(g,k) {

    /**
     * @return A [Solution] of size [k] with a value of at least [t] if possible, or *null* otherwise.
     */
    fun run(t: Int): Solution<V>? {
        for (c in CombinationIterator(vList, k))
            if (cutSize(g, c) >= t) return Solution(c.toMutableSet(), cutSize(g, c))
        return null
    }
}