package graphlib

object Factory {

    fun createPath(n: Int) = MyGraph<Int>().apply {
        for (i in 1 until n) addEdge(i, i + 1)
    }

    fun createClique(n: Int) = MyGraph<Int>().apply {
        for (i in 1..n)
            for (j in 1..n)
                if (i != j) addEdge(i, j)
    }

    fun createStar(n: Int) = createBipartite(1, n-1)

    fun createBipartite(sizeA: Int, sizeB: Int) = MyGraph<Int>().apply {
        for (i in 1..sizeA)
            for (j in sizeA + 1..sizeA + sizeB)
                if (i != j) addEdge(i, j)
    }
}
