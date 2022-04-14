package graphlib.constructors

import graphlib.datastructures.SimpleGraph
import java.io.File

object GraphFileReader { // TODO handle weights, ignore if wanted

    /**Line is not commented out (doesn't start with % or #) and is not empty.*/
    fun validateLine(l: String) = !l.startsWith("%") && !l.startsWith("#") && l.isNotEmpty()

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
            .mapTo(ArrayList()) { Pair(Integer.parseInt(it[0]), Integer.parseInt(it[1])) }
}
