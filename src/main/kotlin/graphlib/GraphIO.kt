package graphlib

import java.io.File

object GraphIO {

    /**Line is not commented out (doesn't start with % or #) and is not empty.*/
    fun validateLine(line: String) = !line.startsWith("%") && !line.startsWith("#") && line.isNotEmpty()

    fun graphFromPath(path: String) = graphFromEdges(edgesFromPath(path))

    fun graphFromPath(path: File) = graphFromPath(path.toString()) // cast wrapper

    fun graphFromEdges(edges: List<Pair<Int, Int>>) = SimpleGraph<Int>().apply {
        edges.forEach { addEdge(it.first, it.second) }
    }

    /**Reads in the graph at [file] and returns a list of edges, where each edge is in the format <VertexID, VertexID, Weight>.*/
    fun edgesFromPath(file: String): List<Pair<Int, Int>> =
        File(file).readLines()
            .filter { validateLine(it) }
            .map { it.split(Regex("""\s+""")) } // split vertex-IDs by whitespace between them
            .map { Pair(it[0].toInt(), it[1].toInt()) }
            .filter { it.first != it.second }
}
