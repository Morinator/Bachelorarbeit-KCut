@file:Suppress("FunctionName", "PrivatePropertyName")

import org.jgrapht.graph.SimpleGraph

class StackSolver<V, E>(private val G: SimpleGraph<V, E>, private val k: Int, private val doHeuristic: Boolean) {

    private val ctr = G.V().associateWith { 0 }

    fun opt(): Set<V> {
        var S = if (doHeuristic) (1..30).map { heuristic(G, k) }.maxBy { cut(G, it) }!! else randSubset(G.V(), k)
        val valueStart = cut(G, S) + ctr[S]

        val upperBound = G.V().map { G.degreeOf(it) }.sorted().takeLast(k).sum()
        while (cut(G, S) + ctr[S] < upperBound)
            S = runTree(cut(G, S) + ctr[S] + 1)?.toSet() ?: break

        if (doHeuristic && valueStart == cut(G, S) + ctr[S]) Stats.optimalHeuristics++

        Stats.print()
        return S
    }

    private fun runTree(t: Int): List<V>? {

        val T = ArrayList<V>()
        var `val` = 0

        val ext = ArrayList<MutableList<V>>()
        val sat = ArrayList<Boolean>()

        fun satExists(): Boolean = (sat.size == T.size + 1)
        fun satBorder(): Double = ((t - `val`).toDouble() / (k - T.size).toDouble()) + 2 * (k - 1)

        fun cont(v: V) = (G.degreeOf(v) + ctr[v]!!) - (2 * T.count { G.containsEdge(it, v) })

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

            if (satExists() && sat.last()) Stats.satRule++
            if (T.size >= k || T.size + ext.last().size < k || satExists() && sat.last()) {

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

    private fun heuristic(G: SimpleGraph<V, E>, k: Int): Set<V> {
        val C = randSubset(G.V(), k) // Candidate

        stepLoop@ while (true) {
            val oldVal = cut(G, C) + ctr[C]
            for (v in C.toList()) {
                C.remove(v)
                for (w in G.V().filter { it !in C }) {
                    C.add(w)
                    if (cut(G, C) + ctr[C] > oldVal) continue@stepLoop
                    C.remove(w)
                }
                C.add(v)
            }
            break@stepLoop
        }

        return C
    }

    init {
        if (k !in 1..G.n()) throw IllegalArgumentException("Illegal value for k")
    }

}