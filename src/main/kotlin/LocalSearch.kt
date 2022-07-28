import org.jgrapht.graph.SimpleGraph

fun <V, E> bestRun(G: SimpleGraph<V, E>, k: Int, runs: Int) =  (1..runs).map { localSearchRun(G, k) }.maxByOrNull { cut(G, it) }!!

fun <V, E> localSearchRun(G: SimpleGraph<V, E>, k: Int): Set<V> {

    if (G.n() < k) throw IllegalArgumentException()
    val S = G.vertexSet().shuffled().take(k).toMutableSet()

    stepLoop@ while (true) {
        val oldVal = cut(G, S)
        for (v in S.toList()) {
            S.remove(v)
            for (w in G.vertexSet().filter { it !in S }) {
                S.add(w)
                if (cut(G, S) > oldVal) continue@stepLoop
                S.remove(w)
            }
            S.add(v)
        }
        break@stepLoop
    }

    return S
}
