package bachelorthesis

import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.paukov.combinatorics3.Generator.combination

object BruteforceSolver {

    fun calc(G: SimpleGraph<Int, DefaultEdge>, k: Int): Collection<Int> {

        if (k !in 1..G.vertexSet().size) throw IllegalArgumentException("Illegal value for k")

        return combination(G.vertexSet()).simple(k).maxByOrNull { cut(G, it) }!!
    }
}
