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
     *
     * @return A [SimpleGraph] of the form:
     */
    fun createClique(n: Int) = SimpleGraph<Int>().apply {
        for (i in 1..n)
            for (j in 1..n)
                if (i != j) addEdge(i, j)
    }

    /**
     *
     * @return A [SimpleGraph] of the form:
     */
    fun createStar(n: Int) = SimpleGraph<Int>().apply {
        for (i in 2..n)
            addEdge(1, i)
    }


}