package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import org.paukov.combinatorics3.Generator

class CompleteLibSolver(
    private val g: SimpleGraph<Int>,
    private val k: Int,
) {

    fun calc(): Solution<Int> {
        var bestSolution = Solution<Int>(listOf(), 0)

        val vertexList = g.V.toList()

        for (S in Generator.combination(vertexList).simple(k)) {
            val cutSize = cutSize(g, S)
            if (cutSize >= bestSolution.value)
                bestSolution = Solution(S, cutSize)
        }

        return bestSolution
    }
}
