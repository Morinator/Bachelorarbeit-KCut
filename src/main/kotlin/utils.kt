@file:Suppress("FunctionName")

import org.jgrapht.Graphs
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import java.io.File

fun <V, E> SimpleGraph<V, E>.V(): MutableSet<V> = vertexSet()
fun <V, E> SimpleGraph<V, E>.n() = vertexSet().size

fun <T> randomSubset(elements : Collection<T>, size : Int) : MutableSet<T> =
    elements.toList().shuffled().take(size).toMutableSet()

fun graphFromPath(path: String) = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java).apply {
    File(path).readLines()
        .filter { !it.startsWith("%") && !it.startsWith("#") && it.isNotEmpty() }
        .map { it.split(Regex("""\s+""")) } // split vertex-IDs by whitespace between them
        .map { Pair(it[0].toInt(), it[1].toInt()) }
        .filter { it.first != it.second }.forEach { Graphs.addEdgeWithVertices(this, it.first, it.second) }
}

fun <V, E> cut(G: SimpleGraph<V, E>, S: Collection<V>) =
    S.sumOf { v -> Graphs.neighborListOf(G, v).count { it !in S } }
