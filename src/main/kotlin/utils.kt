@file:Suppress("FunctionName")

import org.jgrapht.Graphs
import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import java.io.File
import java.util.*

fun <V, E> SimpleGraph<V, E>.V(): MutableSet<V> = vertexSet()

fun graphFromPath(path: String) = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java).apply {
    File(path).readLines()
        .filter { !it.startsWith("%") && !it.startsWith("#") && it.isNotEmpty() }
        .map { it.split(Regex("""\s+""")) } // split vertex-IDs by whitespace between them
        .map { Pair(it[0].toInt(), it[1].toInt()) }
        .filter { it.first != it.second }.forEach { Graphs.addEdgeWithVertices(this, it.first, it.second) }
}

fun <V, E> cut(G: SimpleGraph<V, E>, S: Collection<V>) =
    S.sumOf { v -> neighborListOf(G, v).count { it !in S } }

fun topSelect(col: Collection<Int>, num: Int): List<Int> {
    if (num < 0) throw IllegalArgumentException("Illegal value for k")
    if (num == 0) return emptyList()
    if (num >= col.size) return col.toList()

    val heap = PriorityQueue { a: Int, b: Int -> b - a }
    heap.addAll(col)

    return ArrayList<Int>().apply {
        repeat(num) { add(heap.remove()) }
    }
}