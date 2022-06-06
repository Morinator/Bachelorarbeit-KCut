package bachelorthesis.solvers

import AlgoStats
import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.heuristic.heuristic
import util.collections.Counter
import util.collections.intersectionSize
import util.collections.sortedDesc

class StackSolver(
    private val g: SimpleGraph<Int>,
    private val k: Int,
    useHeuristic: Boolean
) {

    private var bestSolution = if (useHeuristic) heuristic(g, k, 10) else Solution(listOf(), 0)
    private var t = bestSolution.value
    private val counter = Counter(g.V)

    fun calc(): Solution<Int> {
        while (t <= g.degreeSequence.takeLast(k).sum())
            bestSolution = runTree(g, k, t, counter).also { t++ } ?: break

        AlgoStats.print()
        return bestSolution
    }

}


fun runTree(g: SimpleGraph<Int>, k: Int, t: Int, counter: Counter<Int>): Solution<Int>? {
    val s = State(k = k, t = t)

    fun cont(v: Int) = (g.degreeOf(v) + counter[v]) - (2 * intersectionSize(s.T, g[v]))
    s.ext.add(g.V.sortedDesc { cont(it) })

    fun checkIfSatisfactory(v: Int) = cont(v) >= s.satBorder()

    while (s.ext.isNotEmpty()) {

        val doSatRule = (s.currentTreeNodeHasSatRule() && s.sat.last())
        if (doSatRule) AlgoStats.satRuleCounter++

        val doBacktrack = s.T.size >= k || s.T.size + s.ext.last().size < k || doSatRule

        if (doBacktrack) {

            if (s.T.size == k) {
                AlgoStats.candidateCounter++

                if (s.valueOfT >= t)
                    return Solution(s.T, s.valueOfT)
            }

            if (s.T.size < k)
                s.ext.removeLast()
            if (s.currentTreeNodeHasSatRule())
                s.sat.removeLast()

            if (s.T.size > 0)
                s.valueOfT -= cont(s.T.removeLast())

        } else { // ##### BRANCH #####

            AlgoStats.treeNodeCounter++

            val newElem = s.ext.last().removeFirst()

            if (s.T.size < k - 1)
                s.ext.add(s.ext.last().sortedDesc { cont(it) })

            val isSatisfactory = checkIfSatisfactory(newElem)
            s.valueOfT += cont(newElem)

            s.T.add(newElem)

            if (!s.currentTreeNodeHasSatRule()) {
                s.sat.add(isSatisfactory)
                if (isSatisfactory)
                    AlgoStats.satVerticesCounter++
            }
        }
    } // end while-loop

    return null
}

class State(
    var valueOfT: Int = 0,
    val T: MutableList<Int> = ArrayList(),
    val sat: MutableList<Boolean> = ArrayList(),
    val ext: MutableList<MutableList<Int>> = ArrayList(),
    val k: Int,
    val t: Int
) {

    fun currentTreeNodeHasSatRule(): Boolean = (sat.size == T.size + 1)

    private fun kMissing(): Double = (k - T.size).toDouble()
    private fun tMissing(): Double = (t - valueOfT).toDouble()
    fun satBorder() = (tMissing() / kMissing()) + 2 * (k - 1)
}