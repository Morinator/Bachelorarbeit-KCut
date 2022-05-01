package bachelorthesis.solvers.with_lib

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import org.paukov.combinatorics3.Generator

class BruteforceSolver<V>(protected val g: SimpleGraph<V>, val k: Int) {

    private val vertexList = g.vertices().toList()

    var bestSolution = Solution(emptyList<V>(), Int.MIN_VALUE)

    fun calc(): Solution<V> {

        for (S in Generator.combination(vertexList).simple(k)) {
            val s = cutSize(g, S)
            if (s > bestSolution.value)
                bestSolution = Solution(S, s)
        }

        return bestSolution
    }
}