package bachelorthesis

import graphlib.MyGraph
import org.paukov.combinatorics3.Generator.combination

object BruteforceSolver {

    fun calc(G: MyGraph<Int>, k: Int): Collection<Int> {

        if (k !in 1..G.size) throw IllegalArgumentException("Illegal value for k")

        return combination(G.V).simple(k).maxByOrNull { cut(G, it) }!!
    }
}
