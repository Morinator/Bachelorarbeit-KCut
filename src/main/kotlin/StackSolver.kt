import org.jgrapht.graph.SimpleGraph

class StackSolver<V, E>(private val G: SimpleGraph<V, E>, private val k: Int, private val doHeuristic: Boolean) {

    private val ctr = G.V().associateWith { 0 }.toMutableMap()

    fun opt(): Pair<Set<V>, Int> {

        // Kernel 4.1 ##################################################
        /*
        val vSorted = G.V().sortedBy { G.degreeOf(it) + ctr[it]!! }

        if (vSorted.size > G.V().maxOf { G.degreeOf(it) } * k + 1) {
            val v = vSorted.first()
            for (w in neighborSetOf(G, v))
                ctr[w] = ctr[w]!! + 1
            G.removeVertex(v)
            Stats.kTimesDeltaRule++
        }
        */
        // Kernel 4.1 ##################################################

        var S: Set<V> = if (doHeuristic)
            (1..30).map { heuristic(G, k) }.maxByOrNull { it.second }!!.first
        else
            G.V().shuffled().take(k).toMutableSet()

        val valueStart = cut(G, S) + S.sumOf { ctr[it]!! }

        val upperBound = G.V().map { G.degreeOf(it) }.sorted().takeLast(k).sum()



        while (cut(G, S) + S.sumOf { ctr[it]!! } < upperBound)
            S = runTree(cut(G, S) + S.sumOf { ctr[it]!! } + 1)?.toSet() ?: break


        if (doHeuristic && valueStart == cut(G, S) + S.sumOf { ctr[it]!! }) Stats.optimalHeuristics++

        Stats.print()
        return S to cut(G, S) + S.sumOf { ctr[it]!! }
    }

    private fun runTree(t: Int): List<V>? {

        val T = ArrayList<V>()
        var `val` = 0

        val ext = ArrayList<MutableList<V>>()
        val sat = ArrayList<Boolean>()

        fun satExists(): Boolean = (sat.size == T.size + 1)

        fun cont(v: V) = (G.degreeOf(v) + ctr[v]!!) - (2 * T.count { G.containsEdge(it, v) })

        fun trimNeedlessExt() {
            if (T.size >= k || cont(
                    ext.last().first()
                ) >= ((t - `val`).toDouble() / (k - T.size).toDouble()) + 2 * (k - 1)
            ) return
            val border = (t - `val`).toDouble() / (k - T.size).toDouble() - 2 * (k - 1) * (k - 1)
            for (child in ext.last().reversed()) {
                if (cont(child) >= border) break
                Stats.needlessRule++
                ext.last().removeLast()
            }
        }

        ext.add(G.V().sortedByDescending { cont(it) }.toMutableList())
        trimNeedlessExt()

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

                if (T.size > 0){
                    val v = T.removeLast()
                    `val` -= cont(v)
                }

            } else { // ##### BRANCH #####

                Stats.treeNode++

                val newElem = ext.last().removeFirst()

                if (T.size < k - 1)
                    ext.add(ext.last().sortedByDescending { cont(it) }.toMutableList())

                val cont = cont(newElem)
                val isSatisfactory = cont >= ((t - `val`).toDouble() / (k - T.size).toDouble()) + 2 * (k - 1)
                `val` += cont

                T.add(newElem)

                if (!satExists()) {
                    sat.add(isSatisfactory)
                    if (isSatisfactory)
                        Stats.satVertices++
                }
                trimNeedlessExt()
            }
        } // end while-loop

        return null
    }

    private fun heuristic(G: SimpleGraph<V, E>, k: Int): Pair<Set<V>, Int> {
        val C = G.V().shuffled().take(k).toMutableSet() // Init random Candidate

        run@ while (true) {
            val oldVal = cut(G, C) + C.sumOf { ctr[it]!! }
            for (v in C.toList()) {
                C.remove(v)
                for (w in G.V().filter { it !in C }) {
                    C.add(w)
                    if (cut(G, C) + C.sumOf { ctr[it]!! } > oldVal) continue@run
                    C.remove(w)
                }
                C.add(v)
            }
            break@run // didn't find better neighbour
        }

        return Pair(C, cut(G, C) + C.sumOf { ctr[it]!! } )
    }

    init {
        if (k !in 1..G.V().size) throw IllegalArgumentException("Illegal value for k")
    }

}