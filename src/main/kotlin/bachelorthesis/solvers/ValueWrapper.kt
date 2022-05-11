package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution

/**
 * Uses [IndexSolver] for increasing values of *t* to calculate the optimal value.
 */
class ValueWrapper<V>(
    protected val g: SimpleGraph<V>,
    private val k: Int,
    private val decider: DecisionSolver<V>
) {

    fun calc(): Solution<V> {
        var bestSolution = Solution<V>()

        val upperBound = g.degreeSequence.takeLast(k).sum()

        // TODO Reduction Rule 4.1

        for (t in 0..upperBound) {
            val result = decider.calc(t, g, k)
            if (result == null)
                break
            else
                bestSolution = result
        }

        return bestSolution
    }
}
