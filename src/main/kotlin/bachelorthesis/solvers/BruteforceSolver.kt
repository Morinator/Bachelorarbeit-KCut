package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import org.paukov.combinatorics3.Generator

class BruteforceSolver<V>(protected val g: SimpleGraph<V>, k: Int) : Algo<V>(g, k) {

    fun calcResult(): Solution<V> {
        var sol = Solution(mutableSetOf<V>(), Int.MIN_VALUE)

        for (S in Generator.combination(vertexList).simple(k)) {
            val s = cutSize(g, S)
            if (s > sol.value)
                sol = Solution(S, s)
        }
        return sol
    }
}