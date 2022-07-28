package graphlib

import bachelorthesis.cut

fun <V> heuristic(G: MyGraph<V>, k: Int, runs: Int) = (1..runs).map { localSearchRun(G, k) }.maxByOrNull { cut(G, it) }!!

fun <V> localSearchRun(G: MyGraph<V>, k: Int): Set<V> {

    if (G.size < k) return setOf()

    val S = G.V.toList().shuffled().take(k).toMutableSet()

    while (true) {
        val oldVal = cut(G, S)
        localSearchStep(G, S, ::cut)
        if (oldVal == cut(G,S))
            break
    }

    return S
}

fun <V> localSearchStep(G: MyGraph<V>, S: MutableSet<V>, f: (MyGraph<V>, Collection<V>) -> Int) {
    val oldVal = f(G, S)
    for (v in S.toList()) { // copy prevents ConcurrentModificationException
        S.remove(v)

        for (w in G.V.filter { it !in S }) {
            S.add(w)
            if (f(G, S) > oldVal) return
            S.remove(w)
        }

        S.add(v)
    }
}
