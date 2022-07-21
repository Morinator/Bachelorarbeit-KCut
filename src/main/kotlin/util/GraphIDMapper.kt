package util

import graphlib.constructors.Factory.graphFromEdges
import graphlib.datastructures.SimpleGraph

fun mapGraphToRangeIndexedFromZero(G: SimpleGraph<Int>): SimpleGraph<Int> {

    val vSorted = G.V.sorted()
    val gNew = graphFromEdges(G.edgeList().map {
        vSorted.indexOf(it.first) to vSorted.indexOf(it.second)
    })

    return gNew
}


fun main() {
    val G = graphFromEdges(listOf(4 to 7, 5 to 9))
    println(G)
    println("\n\n\n")
    println(mapGraphToRangeIndexedFromZero(G))
}