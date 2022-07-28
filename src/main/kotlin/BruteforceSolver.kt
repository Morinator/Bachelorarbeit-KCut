import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.paukov.combinatorics3.Generator

object BruteforceSolver {

    fun calc(G: SimpleGraph<Int, DefaultEdge>, k: Int): Collection<Int> {

        if (k !in 1..G.n()) throw IllegalArgumentException("Illegal value for k")

        return Generator.combination(G.vertexSet()).simple(k).maxByOrNull { cut(G, it) }!!
    }
}
