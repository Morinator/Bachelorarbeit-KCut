@file:Suppress("PrivatePropertyName")

package solvers

import Stats
import V
import cut
import org.jgrapht.graph.SimpleGraph

/**
 * Does not contain sat- and needless-rule
 */
class DContSolver<V, E>(private val G: SimpleGraph<V, E>, private val k: Int, private val doHeuristic: Boolean) {

    private lateinit var S: Set<V>
    private var SVal: Int = 0

    fun opt(): Pair<Set<V>, Int> {

        S = if (doHeuristic)
            (1..30).map { heuristic(G, k) }.maxByOrNull { it.second }!!.first
        else
            G.V().take(k).toMutableSet()
        SVal = cut(G, S)

        val valueStart = SVal

        val upperBound = getUpperBoundOfSortedExt(0, G.V().sortedByDescending { G.degreeOf(it) })

        while (SVal < upperBound) {
            S = runTree(SVal + 1)?.toSet() ?: break
            SVal = cut(G, S)
        }

        if (doHeuristic && valueStart == SVal) Stats.optimalHeuristics++
        if (upperBound == SVal) Stats.optimalUpperBounds++
        Stats.print()
        return Pair(S, SVal)
    }

    private fun runTree(t: Int): List<V>? {
        Stats.numTrees++

        val T = ArrayList<V>()
        var TVal = 0

        val ext = ArrayList<MutableList<V>>()


        fun cont(v: V) = (G.degreeOf(v) ) - (2 * T.count { G.containsEdge(it, v) })



        ext.add(G.V().sortedByDescending { cont(it) }.toMutableList())

        while (ext.isNotEmpty()) {

            val boundHolds = TVal + getUpperBoundOfSortedExt(T.size, ext.last()) <= SVal
            if (boundHolds) Stats.boundRule++

            if (T.size >= k || T.size + ext.last().size < k  || boundHolds) {

                if (T.size == k) {
                    Stats.candidates++

                    if (TVal >= t)
                        return T
                }

                if (T.size < k)
                    ext.removeLast()

                if (T.size > 0) {
                    val v = T.removeLast()
                    TVal -= cont(v)
                }

            } else { // ##### BRANCH #####

                Stats.treeNode++

                val newElem = ext.last().removeFirst()

                if (T.size < k - 1)
                    ext.add(ext.last().sortedByDescending { cont(it) }.toMutableList())

                val cont = cont(newElem)

                T.add(newElem)
                TVal += cont
            }
        } // end while-loop

        return null
    }

    private fun heuristic(G: SimpleGraph<V, E>, k: Int): Pair<Set<V>, Int> {
        val C = G.V().shuffled().take(k).toMutableSet() // Init random Candidate

        run@ while (true) {
            val oldVal = cut(this.G, C)
            for (v in C.toList()) {
                C.remove(v)
                for (w in G.V().filter { it !in C }) {
                    C.add(w)
                    if (cut(this.G, C) > oldVal) continue@run
                    C.remove(w)
                }
                C.add(v)
            }
            break@run // didn't find better neighbour
        }

        return Pair(C, cut(this.G, C))
    }

    private fun getUpperBoundOfSortedExt(currSize: Int, ext: List<V>): Int =
        ext.take(k-currSize).sumOf { G.degreeOf(it) }

    init {
        if (k !in 1..G.V().size) throw IllegalArgumentException("Illegal value for k")
    }

}