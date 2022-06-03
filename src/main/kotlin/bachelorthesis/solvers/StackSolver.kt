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

    private var bestSolution = if (useHeuristic) runHeuristic(g, k, 10) else Solution()
    private val counter: MutableMap<Int, Int> = g.vertices.associateWithTo(HashMap()) { 0 }

    fun calc(): Solution<Int> {

        var t = bestSolution.value

        val upperBound = g.degreeSequence.takeLast(k).sum()
        tIncreaseLoop@ while (t <= upperBound) {

            var currValue = 0
            val T = ArrayList<Int>()
            val sat = ArrayList<Boolean>()
            val ext = mutableListOf(g.vertices.toMutableList())

            var tmpSolution: Solution<Int>? = null // is null <=> no fitting subset found yet

            fun cont(v: Int) = (g.degreeOf(v) + counter[v]!!) - (2 * T.count { it in g[v] })

            fun kMissing(): Int = k - T.size
            fun tMissing(): Int = t - currValue

            fun checkForSatisfactory(v: Int): Boolean = cont(v) >= (tMissing() / kMissing()) + 2 * (k - 1)

            fun currentTreeNodeHasSatRule(): Boolean = sat.size == T.size + 1

            searchTree@ while (ext.isNotEmpty()) {

                val doSatRule = (currentTreeNodeHasSatRule() && sat.last())
                if (doSatRule)
                    AlgoStats.numSatRule++

                val doBacktrack = T.size >= k ||
                        T.size + ext.last().size < k ||
                        doSatRule

                if (doBacktrack) {

                    if (T.size == k) {

                        AlgoStats.numCandidates++

                        if (currValue >= t) {
                            tmpSolution = Solution(T, currValue)
                            t = currValue + 1
                            break@searchTree
                        }
                    }

                    if (T.size < k) {
                        ext.removeLast()

                        if (sat.isNotEmpty()) sat.removeLast()
                    }

                    if (T.size > 0) // check needed to no throw an exception if algo is finished
                        currValue -= cont(T.removeLast())
                } else { // ##### BRANCH #####

                    AlgoStats.numTreeNodes++

                    if (currentTreeNodeHasSatRule()) sat.removeLast()

                    val newElem = ext.last().removeFirst()

                    if (T.size < k - 1) { // you're not adding a leaf to the search tree  (just for faster runtime)
                        ext.add(ext.last().toMutableList())
                        ext.last().sortByDescending { cont(it) }
                    }
                    val isSatisfactory = checkForSatisfactory(newElem)
                    currValue += cont(newElem)

                    T.add(newElem)
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
