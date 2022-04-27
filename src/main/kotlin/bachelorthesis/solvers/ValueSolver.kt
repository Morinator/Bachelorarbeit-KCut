package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution

class ValueSolver {

    fun <V> run(g: SimpleGraph<V>, k: Int): Solution<V> {
        var sol = Solution(mutableSetOf<V>(), Int.MIN_VALUE)

        val upperBound = g.degreeSequence.sorted().takeLast(k).sum() // sum of highest k degrees

        for (t in 1..upperBound) {

            val tmpResult = DecisionSolver(g, k).run(t)
            if (tmpResult == null)
                break
            else
                sol = tmpResult
        }

        return sol
    }

}