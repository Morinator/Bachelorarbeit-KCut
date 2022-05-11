package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution

/**
 * Uses [IndexSolver] for increasing values of *t* to calculate the optimal value.
 */
class ValueWrapper(
    protected val g: SimpleGraph<Int>,
    private val k: Int,
    private val decider: DecisionSolver<Int>
) {

    fun calc(): Solution<Int> {
        var bestSolution = Solution<Int>()

        val upperBound = g.degreeSequence.takeLast(k).sum()


        // TODO Reduction Rule 4.1

        val counter: MutableMap<Int, Int> = g.vertices().associateWithTo(HashMap()) { 0 }

        for (t in 0..upperBound) {
            val result = decider.calc(t, g, k, counter)
            if (result == null)
                break
            else
                bestSolution = result
        }

        return bestSolution
    }
}
