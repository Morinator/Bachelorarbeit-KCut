package graphlib.constructors

import graphlib.datastructures.SimpleGraph

object Factory {

    /**
     * @return A [SimpleGraph] of the form:          (1) - (2) - ... - (n-1) - (n)
     */
    fun createPath(n: Int) = SimpleGraph<Int>().apply {
        for (i in 1 until n) addEdge(i, i + 1)
    }

    /**                                               #-------------------------#
     *                                                |                         |
     * @return A [SimpleGraph] of the form:          (1) - (2) - ... - (n-1) - (n)
     */
    fun createCycle(n: Int) = SimpleGraph<Int>().apply {
        for (i in 1 until n)
            addEdge(i, i + 1)
        addEdge(1, n)
    }

    /**
     * @return A [SimpleGraph] of the form:
     */
    fun createClique(n: Int) = SimpleGraph<Int>().apply {
        for (i in 1..n)
            for (j in 1..n)
                if (i != j) addEdge(i, j)
    }

    /**
     * @return A [SimpleGraph] of the form:
     */
    fun createStar(n: Int) = SimpleGraph<Int>().apply {
        for (i in 2..n)
            addEdge(1, i)
    }

    /**@return A bipartite graph, whose vertex partition has sizes [sizeA] and [sizeB]. Any two vertices of from
     * different sides in the graph are connected, thus there are [sizeA] * [sizeB] edges in total.*/
    fun createBipartite(sizeA: Int, sizeB: Int) = SimpleGraph<Int>().apply {
        for (i in 1..sizeA)
            for (j in sizeA + 1..sizeA + sizeB)
                if (i != j) addEdge(i, j)
    }

    fun <V> graphFromEdges(edges: Collection<Pair<V, V>>): SimpleGraph<V> =
        SimpleGraph<V>().apply {
            for ((v, w) in edges)
                addEdge(v, w)
        }
}
