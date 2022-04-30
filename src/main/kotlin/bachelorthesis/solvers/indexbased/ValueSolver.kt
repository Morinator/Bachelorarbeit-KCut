package bachelorthesis.solvers.indexbased

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution

/**
 * Uses [DecisionSolver] for increasing values of *t* to calculate the optimal value.
 */
class ValueSolver<V>(protected val g: SimpleGraph<V>, private val k: Int) {

    fun run(): Solution<V> {
        var bestSolution = Solution<V>()

        val upperBound = g.degreeSequence.takeLast(k).sum()

        for (t in 0..upperBound) {
            val result = DecisionSolver(g, k).run(t)
            if (result == null)
                break
            else
                bestSolution = result
        }


        return bestSolution
    }
}