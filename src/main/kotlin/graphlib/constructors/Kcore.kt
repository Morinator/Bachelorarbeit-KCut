package graphlib.constructors

import graphlib.datastructures.SimpleGraph
import util.collections.popRandom

fun <VType> kCore(G: SimpleGraph<VType>, k: Int): SimpleGraph<VType> = G.copy().apply {
    val q = HashSet(V) // process-queue

    while (q.isNotEmpty()) {
        val v = q.popRandom()

        if (degreeOf(v) < k) {
            q.addAll(G[v]) // neighbours get affected
            deleteVertex(v)
        }
    }
}
