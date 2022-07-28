@file:Suppress("FunctionName", "PrivatePropertyName")

import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.SimpleGraph

class StackSolver<V, E>(private val G: SimpleGraph<V, E>, private val k: Int, private val useHeuristic: Boolean) {

    init {
        if (k !in 1..G.n()) throw IllegalArgumentException("Illegal value for k")
    }

    private val ctr = G.V().associateWith { 0 }

    private var S = if (useHeuristic) (1..30).map { heuristic(G, k) }.maxByOrNull { cut(G, it) }!! else randomSubset(G.V(), k)

    private val valueOfStartingSolution = cutWithCtr()

    private val upperBound = G.V().map { G.degreeOf(it) }.sorted().takeLast(k).sum()

    fun opt(): Set<V> {
        while (cutWithCtr() < upperBound)
            S = runTree(cutWithCtr() + 1, ctr)?.toSet() ?: break

        if (useHeuristic && valueOfStartingSolution == cutWithCtr()) Stats.optimalHeuristics++

        Stats.print()
        return S
    }

    private fun runTree(t: Int, counter: Map<V, Int>): List<V>? {

        val T: MutableList<V> = ArrayList()
        var `val` = 0

        val ext: MutableList<MutableList<V>> = ArrayList()
        val sat: MutableList<Boolean> = ArrayList()

        fun satExists(): Boolean = (sat.size == T.size + 1)
        fun doSatRule() = satExists() && sat.last()
        fun satBorder(): Double = ((t - `val`).toDouble() / (k - T.size).toDouble()) + 2 * (k - 1)

        fun cont(v: V) = (G.degreeOf(v) + counter[v]!!) - (2 * T.count { it in neighborSetOf(G, v) })

        fun _trimNeedlessExt() {
            val e = ext.last()
            if (T.size >= k || cont(e.first()) >= satBorder()) return
            val border = (t - `val`).toDouble() / (k - T.size).toDouble() - 2 * (k - 1) * (k - 1)
            for (child in e.reversed()) {
                if (cont(child) >= border) break
                Stats.needlessRule++
                e.removeLast()
            }
        }

        ext.add(G.V().sortedByDescending { cont(it) }.toMutableList())
        _trimNeedlessExt()

        while (ext.isNotEmpty()) {

            if (doSatRule()) Stats.satRule++
            if (T.size >= k || T.size + ext.last().size < k || doSatRule()) {

                if (T.size == k) {
                    Stats.candidates++

                    if (`val` >= t)
                        return T
                }

                if (T.size < k)
                    ext.removeLast()
                if (satExists())
                    sat.removeLast()

                if (T.size > 0)
                    `val` -= cont(T.removeLast())

            } else { // ##### BRANCH #####

                Stats.treeNode++

                val newElem = ext.last().removeFirst()

                if (T.size < k - 1)
                    ext.add(ext.last().sortedByDescending { cont(it) }.toMutableList())

                val cont = cont(newElem)
                val isSatisfactory = cont >= satBorder()
                `val` += cont

                T.add(newElem)

                if (!satExists()) {
                    sat.add(isSatisfactory)
                    if (isSatisfactory)
                        Stats.satVertices++
                }
                _trimNeedlessExt()
            }
        } // end while-loop

        return null
    }

    private fun <V, E> heuristic(G: SimpleGraph<V, E>, k: Int): Set<V> {
        val S = randomSubset(G.V(), k)

        stepLoop@ while (true) {
            val oldVal = cut(G, S)
            for (v in S.toList()) {
                S.remove(v)
                for (w in G.V().filter { it !in S }) {
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

    private fun cutWithCtr() =
        cut(G, S) + S.sumOf { ctr[it]!! }
}