package graphlib

import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

object Factory {

    fun createPath(n: Int) = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java).apply {
        for (i in 1 until n) addEdgeWithVertices(this, i, i+1)
    }

    fun createStar(n: Int) = createBipartite(1, n-1)

    fun createBipartite(sizeA: Int, sizeB: Int) = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java).apply {
        for (i in 1..sizeA)
            for (j in sizeA + 1..sizeA + sizeB)
                if (i != j) addEdgeWithVertices(this, i, j)
    }
}
