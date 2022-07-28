import java.io.File

fun main() {
    val graphFiles = File("data/graphs").walk().filter { it.isFile }
    for (k in 1..6) {
        for (file in graphFiles) {
            val timeBefore = System.currentTimeMillis()
            val G = GraphIO.graphFromPath(file)
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
