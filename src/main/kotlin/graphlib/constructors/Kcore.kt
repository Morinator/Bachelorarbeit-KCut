package graphlib.constructors

import graphlib.datastructures.SimpleGraph
import util.collections.popRandom

fun <V> kCore(g: SimpleGraph<V>, k: Int): SimpleGraph<V> = g.copy().apply {
    val q= HashSet(vertices()) // process-queue

    while (q.isNotEmpty()) {
        val v = q.popRandom()

        if (degreeOf(v) < k) {
            q.addAll(g[v]) // neighbours get affected
            deleteVertex(v)
        }
    }
}
