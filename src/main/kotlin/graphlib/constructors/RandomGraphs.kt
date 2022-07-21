package graphlib.constructors

import graphlib.datastructures.SimpleGraph

object RandomGraphs {

    fun fromRandomEdges(n: Int, m: Int): SimpleGraph<Int> {
        val maxNumberOfEdges = n * (n - 1) / 2
        assert(m <= maxNumberOfEdges)

        return SimpleGraph<Int>().apply {
            while (edgeCount < m)
                addEdge((1..n).random(), (1..n).random())
        }
    }
}