@file:Suppress("FunctionName")

import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.SimpleGraph

class MaxCutInstance<V, E>(private val G: SimpleGraph<V, E>, private val k: Int, private val useHeuristic: Boolean) {

    fun opt(): Set<V> {

        var S = if (useHeuristic) bestRun(G, k, 30) else G.vertexSet().take(k).toSet()
        val startVal = cut(G, S)

        val counter = G.vertexSet().associateWith { 0 }

        val upperBound = G.vertexSet().map { G.degreeOf(it) }.sorted().takeLast(k).sum()

        while (cutWithCtr(G, S, counter) < upperBound)
            S = runTree( cutWithCtr(G, S, counter) + 1, counter)?.toSet() ?: break

        Stats.print()
        if (useHeuristic && startVal == cutWithCtr(G, S, counter)) println("Heuristic was optimal")
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

        ext.add(G.vertexSet().sortedBy { cont(it) }.reversed().toMutableList())
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
                    ext.add(ext.last().sortedBy { cont(it) }.reversed().toMutableList())

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
}

fun <V, E> cut(G: SimpleGraph<V, E>, S: Collection<V>) =
    S.sumOf { v -> neighborListOf(G, v).count { it !in S } }

fun <V, E> cutWithCtr(G: SimpleGraph<V, E>, S: Collection<V>, ctr:Map<V, Int>)  =
    cut(G, S) + S.sumOf { ctr[it]!! }