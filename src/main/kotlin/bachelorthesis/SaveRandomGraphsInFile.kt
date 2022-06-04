package bachelorthesis

import graphlib.constructors.RandomGraphs
import java.io.File

fun main() {
    for (n in listOf(20, 25, 30, 35)) {
        for (m in listOf(50, 70, 90, 110, 130, 150)) {
            for (i in 1..10) {

                val f = File("data/graphs/laplace_random_n${n}_m${m}_i$i")
                val g = RandomGraphs.fromRandomEdges(vertexCount = n, edgeCount = m)

                for (edge in g.edgeList())
                    f.appendText("${edge.first} ${edge.second} \n")
            }
        }
    }
}