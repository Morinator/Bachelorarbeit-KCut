import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

fun <V> localSearchRun(G: SimpleGraph<V, DefaultEdge>, k: Int): Set<V> {

    if (G.n() < k) throw IllegalArgumentException()

    val S = G.vertexSet().shuffled().take(k).toMutableSet()

    while (true) {
        val oldVal = cut(G, S)
        localSearchStep(G, S, ::cut)
        if (oldVal == cut(G, S)) break
    }

    return S
}

fun <V> localSearchStep(
    G: SimpleGraph<V, DefaultEdge>,
    S: MutableSet<V>,
    f: (SimpleGraph<V, DefaultEdge>, Collection<V>) -> Int
) {
    val oldVal = f(G, S)
    for (v in S.toList()) { // copy prevents ConcurrentModificationException
        S.remove(v)
        for (w in G.vertexSet().filter { it !in S }) {
            S.add(w)
            if (f(G, S) > oldVal) return
            S.remove(w)
        }
        S.add(v)
    }
}
