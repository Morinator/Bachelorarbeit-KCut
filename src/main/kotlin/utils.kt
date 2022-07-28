import org.jgrapht.Graphs
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import java.io.File

fun graphFromPath(path: String) = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java).apply {
    File(path).readLines()
        .filter { !it.startsWith("%") && !it.startsWith("#") && it.isNotEmpty() }
        .map { it.split(Regex("""\s+""")) } // split vertex-IDs by whitespace between them
        .map { Pair(it[0].toInt(), it[1].toInt()) }
        .filter { it.first != it.second }.forEach { Graphs.addEdgeWithVertices(this, it.first, it.second) }
}

fun saveResultsInFile() {
    val graphFiles = File("data/graphs").walk().filter { it.isFile }
    for (k in 1..6) {
        for (file in graphFiles) {
            val timeBefore = System.currentTimeMillis()
            val G = graphFromPath(file.toString())
            val sol = BruteforceSolver.calc(G, k)

            val logFile = File("maxcut_results")

            logFile.appendText(
                file.name.padEnd(60) +
                        k.toString().padEnd(10) +
                        cut(G, sol).toString().padEnd(13) +
                        (System.currentTimeMillis() - timeBefore).toString().padEnd(10) +
                        sol + "\n"

            )
        }
    }
}