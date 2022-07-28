import org.jgrapht.graph.SimpleGraph

fun <V, E> localSearchRun(G: SimpleGraph<V, E>, k: Int): Set<V> {

    if (G.n() < k) throw IllegalArgumentException()

    val S = G.vertexSet().shuffled().take(k).toMutableSet()

    while (true) {
        val oldVal = cut(G, S)
        localSearchStep(G, S, ::cut)
        if (oldVal == cut(G, S)) break
    }

    return S
}

fun <V, E> localSearchStep(G: SimpleGraph<V, E>, S: MutableSet<V>, f: (SimpleGraph<V, E>, Collection<V>) -> Int) {
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
