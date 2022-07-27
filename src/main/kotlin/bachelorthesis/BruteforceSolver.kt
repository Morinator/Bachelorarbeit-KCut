package bachelorthesis

import graphlib.SimpleGraph
import org.paukov.combinatorics3.Generator.combination

class BruteforceSolver(private val G: SimpleGraph<Int>, private val k: Int) {

    fun calc(): Collection<Int> {

        if (k !in 1..G.size) throw IllegalArgumentException("Illegal value for k")

        return combination(G.V).simple(k).maxByOrNull { cutSize(G, it) }!!
    }
}
