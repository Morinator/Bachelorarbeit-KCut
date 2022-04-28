package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import org.paukov.combinatorics3.Generator

class BruteforceSolver<V>(protected val g: SimpleGraph<V>, k: Int) : AbstractSolver<V>(g, k) {

    fun calcResult(): Solution<V> {

        for (S in Generator.combination(vertexList).simple(k)) {
            val s = cutSize(g, S)
            if (s > bestSolution.value)
                bestSolution = Solution(S, s)
        }
        return bestSolution
    }
}