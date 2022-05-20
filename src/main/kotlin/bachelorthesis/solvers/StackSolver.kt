package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.heuristic.runHeuristic

class StackSolver(
    private val g: SimpleGraph<Int>,
    private val k: Int,
    useHeuristic: Boolean = false
) {

    private var bestSolution = if (useHeuristic) runHeuristic(g, k, 10) else Solution()

    private val counter: MutableMap<Int, Int> = g.vertices.associateWithTo(HashMap()) { 0 }

    private fun cont(v: Int, T: Collection<Int>) = g.degreeOf(v) + counter[v]!! - (2 * T.count { it in g[v] })

    fun calc(): Solution<Int> {

        var t = bestSolution.value
        val upperBound = g.degreeSequence.takeLast(k).sum()

        tIncreaseLoop@ while (t <= upperBound) {

            var currValue = 0
            val T = ArrayList<Int>()
            val ext = mutableListOf(g.vertices.toMutableList())

            var tmpSolution: Solution<Int>? = null
            val trackingData = TrackingData()

            searchTree@ while (ext.isNotEmpty()) {

                if (T.size >= k || // ##### BACKTRACK #####
                    T.size + ext.last().size < k
                ) {

                    if (T.size == k) {

                        trackingData.subsets++

                        if (currValue >= t) {
                            tmpSolution = Solution(T, currValue)
                            t = currValue + 1
                            break@searchTree
                        }
                    }

                    if (T.size < k)
                        ext.removeLast()

                    if (T.size > 0) {
                        val lastElem = T.removeLast()
                        currValue -= cont(lastElem, T)
                    }

                } else { // ##### BRANCH #####

                    trackingData.treeNodes++

                    val newElem = ext.last().random()
                    ext.last().remove(newElem)

                    if ((T.size < k - 1)) // you're not adding a leaf to the search tree
                        ext.add(ext.last().toMutableList())

                    currValue += cont(newElem, T)

                    T.add(newElem)
                }
            }

            if (tmpSolution == null)
                break@tIncreaseLoop
            else
                bestSolution = tmpSolution

            println(trackingData)
            println("t: $t")
            println("k: $k")
            println("\n")
        }

        return bestSolution
    }
}
