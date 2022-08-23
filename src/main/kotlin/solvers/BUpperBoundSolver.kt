@file:Suppress("PrivatePropertyName")

package solvers

import Stats
import V
import cut
import org.jgrapht.graph.SimpleGraph

/**
 * Does not contain heuristic
 */
class BUpperBoundSolver<V, E>(private val G: SimpleGraph<V, E>, private val k: Int) {

    private lateinit var S: Set<V>
    private var SVal: Int = 0

    fun opt(): Pair<Set<V>, Int> {

        S = G.V().take(k).toMutableSet()
        SVal = cut(G, S)

        val upperBound = G.V().sortedByDescending { G.degreeOf(it) }.take(k - 0).sumOf { G.degreeOf(it) }

        while (SVal < upperBound) {
            S = runTree(SVal + 1)?.toSet() ?: break
            SVal = cut(G, S)
        }

        if (upperBound == SVal) Stats.optimalUpperBounds++
        Stats.print()
        return Pair(S, SVal)
    }

    private fun runTree(t: Int): List<V>? {
        Stats.numTrees++

        val T = ArrayList<V>()
        val ext = ArrayList<MutableList<V>>()

        ext.add(G.V().toMutableList())

        while (ext.isNotEmpty()) {

            if (T.size >= k || T.size + ext.last().size < k) {

                if (T.size == k) {
                    Stats.candidates++

                    if (cut(G, T) >= t)
                        return T
                }

                if (T.size < k)
                    ext.removeLast()
                if (T.size > 0)
                    T.removeLast()

            } else { // ##### BRANCH #####
                Stats.treeNode++

                val newElem = ext.last().removeFirst()

                if (T.size < k - 1)
                    ext.add(ext.last().toMutableList())

                T.add(newElem)
            }
        } // end while-loop

        return null
    }

    init {
        if (k !in 1..G.V().size) throw IllegalArgumentException("Illegal value for k")
    }

}