@file:Suppress("FunctionName")

import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.SimpleGraph

object StackSolver {

    fun <V,E> calc(G: SimpleGraph<V, E>, k: Int, useHeuristic: Boolean): Set<V> {

        var S = if (useHeuristic) {
            (1..10).map { localSearchRun(G, k) }.maxByOrNull { cut(G, it) }!!
        } else
            G.vertexSet().take(k).toSet()

        val counter = G.vertexSet().associateWith { 0 }

        val upperBound = G.vertexSet().map { G.degreeOf(it) }.sorted().takeLast(k).sum()

        while (cut(G, S) <= upperBound)
            S = runTree(G, k, cut(G, S) + 1, counter)?.toSet() ?: break

        AlgoStats.print()
        return S
    }

    private fun <V, E> runTree(G: SimpleGraph<V, E>, k: Int, t: Int, counter: Map<V, Int>): List<V>? {

        val T: MutableList<V> = ArrayList()
        var cut = 0

        val sat: MutableList<Boolean> = ArrayList()
        val ext: MutableList<MutableList<V>> = ArrayList()

        fun satExists(): Boolean = (sat.size == T.size + 1)
        fun doSatRule() = satExists() && sat.last()
        fun satBorder(): Double = ((t - cut).toDouble() / (k - T.size).toDouble()) + 2 * (k - 1)

        fun cont(v: V) = (G.degreeOf(v) + counter[v]!!) - (2 * T.count { it in neighborSetOf(G, v) })

        fun _trimNeedlessExt() {
            if (T.size < k) { //TODO Think about what is the fitting condition here
                val x = ext.last()
                if (cont(x.first()) < satBorder()) {
                    val border = (t - cut).toDouble() / (k - T.size).toDouble() - 2 * (k - 1) * (k - 1)
                    for (i in x.indices.reversed())
                        if (cont(x[i]) < border) {
                            AlgoStats.needlessRule++
                            x.removeLast()
                        } else
                            break
                }
            }
        }


        ext.add(G.vertexSet().sortedBy { cont(it) }.reversed().toMutableList())
        _trimNeedlessExt()

        while (ext.isNotEmpty()) {

            if (doSatRule()) AlgoStats.satRule++
            if (T.size >= k || T.size + ext.last().size < k || doSatRule()) {

                if (T.size == k) {
                    AlgoStats.candidates++

                    if (cut >= t)
                        return T
                }

                if (T.size < k)
                    ext.removeLast()
                if (satExists())
                    sat.removeLast()

                if (T.size > 0)
                    cut -= cont(T.removeLast())

            } else { // ##### BRANCH #####

                AlgoStats.treeNode++

                val newElem = ext.last().removeFirst()

                if (T.size < k - 1)
                    ext.add(ext.last().sortedBy { cont(it) }.reversed().toMutableList())

                val cont = cont(newElem)
                val isSatisfactory = cont >= satBorder()
                cut += cont

                T.add(newElem)

                if (!satExists()) {
                    sat.add(isSatisfactory)
                    if (isSatisfactory)
                        AlgoStats.satVertices++
                }
                _trimNeedlessExt()
            }
        } // end while-loop

        return null
    }
}

fun <V, E> cut(G: SimpleGraph<V, E>, S: Collection<V>): Int =
    S.sumOf { v -> neighborListOf(G, v).count { it !in S } }