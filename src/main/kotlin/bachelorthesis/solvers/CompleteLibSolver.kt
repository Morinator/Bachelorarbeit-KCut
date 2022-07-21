package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import org.paukov.combinatorics3.Generator

class CompleteLibSolver(
    private val G: SimpleGraph<Int>,
    private val k: Int,
) {

    fun calc(): Solution<Int> {
        var sol = Solution<Int>(listOf(), 0) // tracker for best solution

        for (S in Generator.combination(G.V.toList()).simple(k)) {
            val cutSize = cutSize(G, S)
            if (cutSize >= sol.value)
                sol = Solution(S, cutSize) // update
        }

        return sol
    }
}
