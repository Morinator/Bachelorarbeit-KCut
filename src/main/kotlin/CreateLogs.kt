import java.io.File

fun main() {
    val graphFiles = File("data/graphs").walk().filter { it.isFile }
    for (k in 1..7) {
        for (file in graphFiles) {
            val g = graphFromPath(file.toString())
            val tBefore = System.currentTimeMillis()
            val (S, value) = BruteforceSolver.calc(g, k)

            File("logs_from_august_02").appendText(
                file.name.toString().padEnd(60) +
                        k.toString().padEnd(10) +
                        value.toString().padEnd(20) +
                        (System.currentTimeMillis() - tBefore).toString().padEnd(10) +
                        S.toString() +
                        "\n"
            )
        }
    }
}