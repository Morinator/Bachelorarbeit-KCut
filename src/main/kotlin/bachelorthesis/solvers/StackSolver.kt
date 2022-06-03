package bachelorthesis.solvers

import AlgoStats
import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.heuristic.runHeuristic

class StackSolver(
    private val g: SimpleGraph<Int>,
    private val k: Int,

    useHeuristic: Boolean
) {

    private var bestSolution = if (useHeuristic) runHeuristic(g, k, 10) else Solution(listOf(), 0)
    private val counter: MutableMap<Int, Int> = g.vertices.associateWithTo(HashMap()) { 0 }

    fun calc(): Solution<Int> {

        var t = bestSolution.value

        val upperBound = g.degreeSequence.takeLast(k).sum()
        tIncreaseLoop@ while (t <= upperBound) {

            var valueOfT = 0
            val T = ArrayList<Int>()

            fun cont(v: Int) = (g.degreeOf(v) + counter[v]!!) - (2 * T.count { it in g[v] })

            val sat = ArrayList<Boolean>()
            val ext = mutableListOf(g.vertices.sortedByDescending { cont(it) }.toMutableList())

            var tmpSolution: Solution<Int>? = null // is null <=> no fitting subset found yet

            fun kMissing(): Int = k - T.size
            fun tMissing(): Int = t - valueOfT
            fun checkIfSatisfactory(v: Int): Boolean = (cont(v) >= (tMissing() / kMissing()) + 2 * (k - 1))
            fun currentTreeNodeHasSatRule(): Boolean = (sat.size == T.size + 1)

            searchTree@ while (ext.isNotEmpty()) {

                val doSatRule = (currentTreeNodeHasSatRule() && sat.last())
                if (doSatRule) AlgoStats.satRuleCounter++

                val doBacktrack = T.size >= k || T.size + ext.last().size < k || doSatRule

                if (doBacktrack) {

                    if (T.size == k) {
                        AlgoStats.candidateCounter++

                        if (valueOfT >= t) {
                            tmpSolution = Solution(T, valueOfT)
                            t = valueOfT + 1
                            break@searchTree
                        }
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

                    if (T.size < k - 1) // you're not adding a leaf to the search tree  (just for faster runtime)
                        ext.add(ext.last().sortedByDescending { cont(it) }.toMutableList())

                    val isSatisfactory = checkIfSatisfactory(newElem) // TODO dont calc it useless
                    valueOfT += cont(newElem)

                    T.add(newElem)
                    if (!currentTreeNodeHasSatRule())
                        sat.add(isSatisfactory)
                }
            }

            if (tmpSolution == null)
                break@tIncreaseLoop
            else
                bestSolution = tmpSolution
        }

        AlgoStats.print()

        return bestSolution
    }
}
