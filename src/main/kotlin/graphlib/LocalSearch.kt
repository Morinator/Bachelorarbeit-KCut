package graphlib

import bachelorthesis.cutSize

fun <V> heuristic(G: SimpleGraph<V>, k: Int, runs: Int) = (1..runs).map { localSearchRun(G, k) }.maxByOrNull { cutSize(G, it) }!!

fun <V> localSearchRun(G: SimpleGraph<V>, k: Int): Set<V> {

    if (G.size < k) return setOf()

    val S = G.V.toList().shuffled().take(k).toMutableSet()

    while (true) {
        val oldVal = cutSize(G, S)
        localSearchStep(G, S, ::cutSize)
        if (oldVal == cutSize(G,S))
            break
    }

    return S
}

fun <V> localSearchStep(G: SimpleGraph<V>, S: MutableSet<V>, f: (SimpleGraph<V>, Collection<V>) -> Int) {
    val oldVal = f(G, S)
    for (v in S.toList()) { // copy prevents ConcurrentModificationException
        S.remove(v)

        for (w in G.V.filter { it !in S }) {
            S.add(w)
            if (f(G, S) > oldVal) return // better S found!
            S.remove(w)
        }

        S.add(v)
    }
}
