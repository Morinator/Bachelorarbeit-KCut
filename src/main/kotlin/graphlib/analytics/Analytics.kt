package graphlib.analytics

import graphlib.datastructures.SimpleGraph

fun <V> printAnalytics(g: SimpleGraph<V>) {
    val paddingRight = 20

    println("Vertices:".padEnd(paddingRight) + g.size())
    println("Edges:".padEnd(paddingRight) + g.edgeCount())
    println("Max-Degree:".padEnd(paddingRight) + g.vertices().maxOf { g.degreeOf(it) })
}
