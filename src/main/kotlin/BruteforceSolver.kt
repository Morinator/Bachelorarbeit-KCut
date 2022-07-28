import org.jgrapht.graph.SimpleGraph
import org.paukov.combinatorics3.Generator

object BruteforceSolver {

    fun <V, E> calc(G: SimpleGraph<V, E>, k: Int): Collection<V> {

        if (k !in 1..G.n()) throw IllegalArgumentException("Illegal value for k")

        return Generator.combination(G.vertexSet()).simple(k).maxByOrNull { cut(G, it) }!!
    }
}
