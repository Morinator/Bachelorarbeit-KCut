import org.jgrapht.graph.SimpleGraph
import org.paukov.combinatorics3.Generator

object BruteforceSolver {

    fun <V, E> calc(G: SimpleGraph<V, E>, k: Int): Pair<Set<V>, Int> {

        if (k !in 1..G.V().size) throw IllegalArgumentException("Illegal value for k")

        val S = Generator.combination(G.V()).simple(k).maxByOrNull { cut(G, it) }!!.toSet()
        return Pair(S, cut(G, S))
    }
}
