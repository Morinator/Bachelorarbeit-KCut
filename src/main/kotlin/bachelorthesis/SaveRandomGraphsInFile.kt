package bachelorthesis

import graphlib.constructors.RandomGraphs
import java.io.File

fun main() {
    for (n in 20..33 step 3) {
        for (m in 50..150 step 10) {
            for (i in 1..10) {

                val file = File("data/graphs/laplace_random_n${n}_m${m}_i$i")
                val G = RandomGraphs.fromRandomEdges(n = n, m = m)

                for (edge in G.edgeList())
                    file.appendText("${edge.first} ${edge.second} \n")
            }
        }
    }
}