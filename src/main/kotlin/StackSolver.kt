import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.SimpleGraph
import org.paukov.combinatorics3.Generator

class StackSolver<V, E>(private val G: SimpleGraph<V, E>, private val k: Int, private val doHeuristic: Boolean) {

    private val ctr = G.V().associateWith { 0 }.toMutableMap()
    private fun degPlusCtr(v: V) = G.degreeOf(v) + ctr[v]!!

    fun opt(): Pair<Set<V>, Int> {

        while (doKernel())
            Stats.kernelRuns++

        var currS: Set<V> = if (doHeuristic)
            (1..30).map { heuristic(G, k) }.maxByOrNull { it.second }!!.first
        else
            G.V().take(k).toMutableSet()
        var currVal = cutPlusCtr(currS)

        val valueStart = currVal

        val upperBound = getUpperBound()

        while (currVal < upperBound) {

            newExclusionRule(currVal)


            currS = runTree(currVal + 1)?.toSet() ?: break
            currVal = cutPlusCtr(currS)
        }

        if (doHeuristic && valueStart == currVal) Stats.optimalHeuristics++
        if (upperBound == currVal) Stats.optimalUpperBounds++
        Stats.print()
        return Pair(currS, currVal)
    }

    private fun newExclusionRule(currVal: Int) {
        for (v in G.V().toList()) {
            val delta = G.V().maxOf { degPlusCtr(it) }
            if (degPlusCtr(v) + (k - 1) * delta <= currVal) {
                applyExclusionRule(v)
                Stats.newExclusionRule++
            }
        }
    }


    private fun runTree(t: Int): List<V>? {
        Stats.numTrees++

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
            val needlessBorder = (t - `val`).toDouble() / (k - T.size).toDouble() - 2 * (k - 1) * (k - 1)
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

                if (T.size > 0) {
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

    private fun getUpperBound(): Int {
        val vSorted: List<V> = G.V().sortedByDescending { degPlusCtr(it) }
        val sum = vSorted.take(k).sumOf { degPlusCtr(it) }
        val myVert = vSorted.takeWhile { degPlusCtr(it) >= degPlusCtr(vSorted.take(k).last()) }
        if (myVert.size > k +4) return sum // would take too long, so we don't do extended check
        val x: Int = Generator.combination(myVert).simple(k).maxOf { cutPlusCtr(it.toSet()) }

        return if (x == sum) sum else sum-1
    }

    private fun cutPlusCtr(X: Set<V>) = cut(G, X) + X.sumOf { ctr[it]!! }

    private fun applyExclusionRule(v: V) {
        neighborSetOf(G, v).forEach { ctr[it] = ctr[it]!! + 1 } // exclusion rule
        G.removeVertex(v)
    }

    init {
        if (k !in 1..G.V().size) throw IllegalArgumentException("Illegal value for k")
    }

}