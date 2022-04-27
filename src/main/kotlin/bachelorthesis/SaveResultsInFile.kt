package bachelorthesis

import graphlib.constructors.GraphIO
import java.io.File

// TODO don't append if filepath+k combo already exists
fun main() {
    val graphFiles = File("data/graphs").walk().filter { it.isFile }
    for (k in 1..3) {
        for (file in graphFiles) {
            val g = GraphIO.graphFromPath(file)
            val sol = solveFastWithT(g, k)

            File("maxcut_results").appendText(
                file.toString().padEnd(60) + k.toString().padEnd(10) + sol.value.toString()
                    .padEnd(20) + sol.vertices + "\n"
            )
        }
    }
}