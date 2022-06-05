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
    var valueOfT = 0
    val T = ArrayList<Int>()
    val sat = ArrayList<Boolean>()

    fun cont(v: Int) = (g.degreeOf(v) + counter[v]) - (2 * intersectionSize(T, g[v]))

    val ext = mutableListOf(g.V.sortedDesc { cont(it) })

    fun kMissing(): Int = k - T.size
    fun tMissing(): Int = t - valueOfT
    fun checkIfSatisfactory(v: Int): Boolean = (cont(v) >= (tMissing() / kMissing()) + 2 * (k - 1))

    fun currentTreeNodeHasSatRule(): Boolean = (sat.size == T.size + 1)

    while (ext.isNotEmpty()) {

        val doSatRule = (currentTreeNodeHasSatRule() && sat.last())
        if (doSatRule) AlgoStats.satRuleCounter++

        val doBacktrack = T.size >= k || T.size + ext.last().size < k || doSatRule

        if (doBacktrack) {

            if (T.size == k) {
                AlgoStats.candidateCounter++

                if (valueOfT >= t)
                    return Solution(T, valueOfT)
            }

            if (T.size < k)
                ext.removeLast()
            if (currentTreeNodeHasSatRule())
                sat.removeLast()

            if (T.size > 0)
                valueOfT -= cont(T.removeLast())

        } else { // ##### BRANCH #####

            AlgoStats.treeNodeCounter++

            val newElem = ext.last().removeFirst()

            if (T.size < k - 1)
                ext.add(ext.last().sortedDesc { cont(it) })

            val isSatisfactory = checkIfSatisfactory(newElem)
            valueOfT += cont(newElem)

            T.add(newElem)

            if (!currentTreeNodeHasSatRule()) {
                sat.add(isSatisfactory)
                AlgoStats.satVerticesCounter++
            }
        }
    } // end while-loop

    return null
}
