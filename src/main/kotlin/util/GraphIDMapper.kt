package util

import graphlib.constructors.Factory.graphFromEdges
import graphlib.datastructures.SimpleGraph

fun mapGraphToRangeIndexedFromZero(g: SimpleGraph<Int>): SimpleGraph<Int> {

    val vSorted = g.V.sorted()
    val gNew = graphFromEdges(g.edgeList().map {
        vSorted.indexOf(it.first) to vSorted.indexOf(it.second)
    })

    return gNew
}


fun main() {
    val g = graphFromEdges(listOf(4 to 7, 5 to 9))
    println(g)
    println("\n\n\n")
    println(mapGraphToRangeIndexedFromZero(g))
}