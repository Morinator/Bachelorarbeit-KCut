import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import java.io.File

object GraphIO {

    fun graphFromPath(path: String) = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java).apply {
        File(path).readLines()
            .filter { !it.startsWith("%") && !it.startsWith("#") && it.isNotEmpty() }
            .map { it.split(Regex("""\s+""")) } // split vertex-IDs by whitespace between them
            .map { Pair(it[0].toInt(), it[1].toInt()) }
            .filter { it.first != it.second }.forEach { addEdgeWithVertices(this, it.first, it.second) }
    }

    fun graphFromPath(path: File) = graphFromPath(path.toString()) // cast wrapper
}
