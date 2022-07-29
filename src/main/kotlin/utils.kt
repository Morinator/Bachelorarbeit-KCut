@file:Suppress("FunctionName")

import org.jgrapht.Graphs
import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import java.io.File

fun <V, E> SimpleGraph<V, E>.V(): MutableSet<V> = vertexSet()
fun <V, E> SimpleGraph<V, E>.n() = V().size

fun <T> randSubset(elements: Collection<T>, size: Int): MutableSet<T> =
    elements.shuffled().take(size).toMutableSet()

fun <T, R : Comparable<R>> Iterable<T>.maxBy(selector: (T) -> R): T? = maxByOrNull { selector(it) }!!

fun graphFromPath(path: String) = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java).apply {
    File(path).readLines()
        .filter { !it.startsWith("%") && !it.startsWith("#") && it.isNotEmpty() }
        .map { it.split(Regex("""\s+""")) } // split vertex-IDs by whitespace between them
        .map { Pair(it[0].toInt(), it[1].toInt()) }
        .filter { it.first != it.second }.forEach { Graphs.addEdgeWithVertices(this, it.first, it.second) }
}

fun <V, E> cut(G: SimpleGraph<V, E>, S: Collection<V>) =
    S.sumOf { v -> neighborListOf(G, v).count { it !in S } }

operator fun <T> Map<T, Int>.get(S: Set<T>) = S.sumOf { get(it)!! }