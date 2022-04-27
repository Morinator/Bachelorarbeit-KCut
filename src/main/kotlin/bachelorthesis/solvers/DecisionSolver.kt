package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import util.collections.MyCombinationIterator

class DecisionSolver<V>(protected val g: SimpleGraph<V>, k: Int) : AbstractSolver<V>(g, k) {

    /**
     * @return A [Solution] of size [k] with a value of at least [t] if possible, or *null* otherwise.
     */
    fun calcResult(t: Int): Solution<V>? {
        for (c in MyCombinationIterator(vertexList, k))
            if (cutSize(g, c) >= t) return Solution(c.toMutableSet(), cutSize(g, c))
        return null
    }
}