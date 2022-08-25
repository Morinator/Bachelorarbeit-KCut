@file:Suppress("PrivatePropertyName")

package solvers

import Stats
import V
import cut
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.SimpleGraph

class FullStackSolver<V, E>(private val G: SimpleGraph<V, E>, private val k: Int, private val doHeuristic: Boolean) {

    private val ctr = G.V().associateWith { 0 }.toMutableMap()
    private fun degPlusCtr(v: V) = G.degreeOf(v) + ctr[v]!!

    private lateinit var S: Set<V>
    private var SVal: Int = 0

    fun opt(): Pair<Set<V>, Int> {

        while (doKernel())
            Stats.kernelRuns++

        S = if (doHeuristic)
            (1..30).map { heuristic(G, k) }.maxByOrNull { it.second }!!.first
        else
            G.V().take(k).toMutableSet()
        SVal = cutPlusCtr(S)

        val valueStart = SVal

        val upperBound = getUpperBoundOfSortedExt(0, G.V().sortedByDescending { degPlusCtr(it) })

        while (SVal < upperBound) {

            S = runTree(SVal + 1)?.toSet() ?: break
            SVal = cutPlusCtr(S)
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
        val sat = ArrayList<Boolean>()

        fun satExists(): Boolean = (sat.size == T.size + 1)

        fun cont(v: V) = (G.degreeOf(v) + ctr[v]!!) - (2 * T.count { G.containsEdge(it, v) })

        fun trimNeedlessExt() {
            if (T.size >= k ||
                cont(ext.last().first()) >= ((t - TVal).toDouble() / (k - T.size).toDouble()) + 2 * (k - 1)
            ) return
            val needlessBorder = (t - TVal).toDouble() / (k - T.size).toDouble() - 2 * (k - 1) * (k - 1)
            for (child in ext.last().reversed()) {
                if (cont(child) >= needlessBorder) break
                ext.last().removeLast()
                Stats.needlessRule++
            }
        }

        ext.add(G.V().sortedByDescending { cont(it) }.toMutableList())
        trimNeedlessExt()

        while (ext.isNotEmpty()) {

            if (satExists() && sat.last()) Stats.satRule++

            val boundHolds = TVal + getUpperBoundOfSortedExt(T.size, ext.last()) <= SVal
            if (boundHolds) Stats.boundRule++

            if (T.size >= k || T.size + ext.last().size < k || satExists() && sat.last() || boundHolds) {

                if (T.size == k) {
                    Stats.candidates++

                    if (TVal >= t)
                        return T
                }

                if (T.size < k)
                    ext.removeLast()
                if (satExists())
                    sat.removeLast()

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

                if (!satExists()) {
                    sat.add(cont >= ((t - TVal).toDouble() / (k - T.size).toDouble()) + 2 * (k - 1))
                    if (sat.last())
                        Stats.satVertices++
                }

                T.add(newElem)
                TVal += cont

                trimNeedlessExt()
            }
        } // end while-loop

        return null
    }

    private fun heuristic(G: SimpleGraph<V, E>, k: Int): Pair<Set<V>, Int> {
        val C = G.V().shuffled().take(k).toMutableSet() // Init random Candidate

        run@ while (true) {
            val oldVal = cutPlusCtr(C)
            for (v in C.toList()) {
                C.remove(v)
                for (w in G.V().filter { it !in C }) {
                    C.add(w)
                    if (cutPlusCtr(C) > oldVal) continue@run
                    C.remove(w)
                }
                C.add(v)
            }
            break@run // didn't find better neighbour
        }

        return Pair(C, cutPlusCtr(C))
    }

    private fun doKernel(): Boolean { // return true if it had an effect
        val vSorted = G.V().sortedByDescending { degPlusCtr(it) }
        val delta = G.V().maxOf { G.degreeOf(it) }
        val numToKeep = (delta + 1) * (k - 1) + 1
        val hasRemovedVertex = G.V().size > numToKeep

        for (v in vSorted.drop(numToKeep)) {
            applyExclusionRule(v)
            Stats.kernelDeletions++
        }

        return hasRemovedVertex
    }

    private fun getUpperBoundOfSortedExt(currSize: Int, ext: List<V>): Int =
        ext.take(k - currSize).sumOf { degPlusCtr(it) }

    private fun cutPlusCtr(X: Set<V>) = cut(G, X) + X.sumOf { ctr[it]!! }

    private fun applyExclusionRule(v: V) {
        neighborSetOf(G, v).forEach { ctr[it] = ctr[it]!! + 1 } // exclusion rule
        G.removeVertex(v)
    }

    init {
        if (k !in 1..G.V().size) throw IllegalArgumentException("Illegal value for k")
    }

}