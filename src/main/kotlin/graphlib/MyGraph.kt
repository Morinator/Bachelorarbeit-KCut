package graphlib

import org.jgrapht.Graphs
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

class MyGraph<V> {

    private val m = SimpleGraph<V, DefaultEdge>(DefaultEdge::class.java)

    fun addVertex(v: V) {
        m.addVertex(v)
    }

    fun addEdge(a: V, b: V) {
Graphs.addEdgeWithVertices(m, a,b)    }

    operator fun get(v: V): List<V> = Graphs.neighborListOf(m,v)

    fun deleteVertex(v: V) {
        m.removeVertex(v)
    }

    fun deleteEdge(a: V, b: V) {
        m.removeEdge(a,b)
    }

    fun areNB(a: V, b: V): Boolean = m.containsEdge(a,b)

    val size get() = m.vertexSet().size

    fun degreeOf(v: V): Int = m.degreeOf(v)

    val edgeCount get() = m.edgeSet().size

    @Suppress("PropertyName")
    val V: Set<V>
        get() = m.vertexSet()
}
