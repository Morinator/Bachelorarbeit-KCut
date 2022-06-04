package graphlib.constructors

import graphlib.datastructures.SimpleGraph

object RandomGraphs {

    fun fromRandomEdges(vertexCount: Int, edgeCount: Int): SimpleGraph<Int> {
        val maxNumberOfEdges = vertexCount * (vertexCount - 1) / 2
        assert(edgeCount <= maxNumberOfEdges)

        val g = SimpleGraph<Int>()
        while (g.edgeCount < edgeCount)
            g.addEdge((1..vertexCount).random(), (1..vertexCount).random())

        return g
    }
}