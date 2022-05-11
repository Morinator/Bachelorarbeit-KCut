package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import org.paukov.combinatorics3.Generator

class LibSolver<V> : DecisionSolver<V> {

    override fun calc(t: Int, g: SimpleGraph<V>, k: Int, counter: MutableMap<Int, Int>): Solution<V>? {

        val vertexList = g.vertices().toList()

        for (S in Generator.combination(vertexList).simple(k)) {
            val cutSize = cutSize(g, S)
            if (cutSize >= t)
                return Solution(S, cutSize)
        }

        return null
    }
}
