package bachelorthesis.solvers

import AlgoStats
import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.heuristic.heuristic
import util.collections.Counter
import util.collections.intersectionSize
import util.collections.sortedDesc

class StackSolver(
    private val G: SimpleGraph<Int>,
    private val k: Int,
    useHeuristic: Boolean
) {

    private var bestSolution = if (useHeuristic) heuristic(G, k, 10) else Solution(listOf(), 0)
    private var t = bestSolution.value
    private val counter = Counter(G.V)

    fun calc(): Solution<Int> {

        while (t <= G.degreeSequence.takeLast(k).sum()) {
            bestSolution = runTree(G, k, t, counter)?: break
            t++
        }

        AlgoStats.print()
        return bestSolution
    }

}


fun runTree(G: SimpleGraph<Int>, k: Int, t: Int, counter: Counter<Int>): Solution<Int>? {
    val s = State(k = k, t = t)

    fun cont(v: Int) = (G.degreeOf(v) + counter[v]) - (2 * intersectionSize(s.T, G[v]))

    fun needlessRule() {
        if (s.T.size < k) { //TODO Think about what is the fitting condition here
            val x = s.ext.last()
            if (cont(x.first()) < s.satBorder()) {
                val border = s.needlessBorder()
                n@ for (i in x.indices.reversed()) {
                    if (cont(x[i]) < border) {
                        AlgoStats.needlessRule++
                        x.removeLast()
                    } else
                        break@n
                }
            }
        }
    }

    fun kTimesDeltaRule() {
        val verticesToRemove = G.V.sortedByDescending { cont(it) }.drop(G.maxDegree * k + 1)
        verticesToRemove.forEach { v ->
            G[v].forEach { counter.inc(it) }
            G.deleteVertex(v)
            AlgoStats.kTimesDeltaRule++
        }
    }

    // kTimesDeltaRule()

    s.ext.add(G.V.sortedDesc { cont(it) })
    needlessRule()

    while (s.ext.isNotEmpty()) {

        if (s.doSatRule()) AlgoStats.satRule++
        if (s.doBacktrack()) {

            if (s.T.size == k) {
                AlgoStats.candidates++

                if (s.valueOfT >= t)
                    return Solution(s.T, s.valueOfT)
            }

            if (s.T.size < k)
                s.ext.removeLast()
            if (s.satExists())
                s.sat.removeLast()

            if (s.T.size > 0)
                s.valueOfT -= cont(s.T.removeLast())

        } else { // ##### BRANCH #####

            AlgoStats.treeNode++

            val newElem = s.ext.last().removeFirst()

            if (s.T.size < k - 1)
                s.ext.add(s.ext.last().sortedDesc { cont(it) })

            val cont = cont(newElem)
            val isSatisfactory = cont >= s.satBorder()
            s.valueOfT += cont

            s.T.add(newElem)

            if (!s.satExists()) {
                s.sat.add(isSatisfactory)
                if (isSatisfactory)
                    AlgoStats.satVertices++
            }

            // NEEDLESS-RULE
            needlessRule()
        }
    } // end while-loop

    return null
}

/**
 * Saves the current state of the algorithm.
 *
 * Only contains query-methods, no command (i.e. no method in this class will change anything).
 */
class State(
    var valueOfT: Int = 0,
    val T: MutableList<Int> = ArrayList(),
    val sat: MutableList<Boolean> = ArrayList(),
    val ext: MutableList<MutableList<Int>> = ArrayList(),
    val k: Int,
    val t: Int
) {

    fun doBacktrack() = T.size >= k || T.size + ext.last().size < k || doSatRule()

    internal fun doSatRule() = satExists() && sat.last()

    fun satExists(): Boolean = (sat.size == T.size + 1)

    private fun kMissing(): Double = (k - T.size).toDouble()
    private fun tMissing(): Double = (t - valueOfT).toDouble()

    fun satBorder(): Double = (tMissing() / kMissing()) + 2 * (k - 1)

    fun needlessBorder(): Double = tMissing() / kMissing() - 2 * (k - 1) * (k - 1)
}