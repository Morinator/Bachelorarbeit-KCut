package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.heuristic.runHeuristic
import util.collections.increment

// TODO Regeln finden, ob man Suchbaum frühzeitig abbrechen kann

class StackSolver(private val g: SimpleGraph<Int>, private val k: Int) {

    private var bestSolution = runHeuristic(g, k, 10)

    private val counter: MutableMap<Int, Int> = g.vertices.associateWithTo(HashMap()) { 0 }

    private fun cont(v: Int, T: Collection<Int>) = g.degreeOf(v) + counter[v]!! - (2 * T.count { it in g[v] })

    fun calc(): Solution<Int> {
        val verticesToRemove = g.vertices.sortedByDescending { cont(it, emptySet()) }.drop(g.maxDegree * k + 1)
        verticesToRemove.forEach { v ->
            g[v].forEach { counter.increment(it) }
            g.deleteVertex(v)
        }

        var t = bestSolution.value
        val upperBound = g.degreeSequence.takeLast(k).sum()

        tIncreaseLoop@ while (t <= upperBound) {

            var curCutValue = 0
            val T = ArrayList<Int>()
            val ext = mutableListOf(g.vertices.toMutableList())

            var tmpSolution: Solution<Int>? = null

            searchTree@ while (ext.isNotEmpty()) {

                if (T.size >= k || // ##### BACKTRACK #####
                    T.size + ext.last().size < k
                ) {

                    if (T.size == k) {
                        val valWithCounter = curCutValue + T.sumOf { counter[it]!! }

                        if (valWithCounter >= t) {
                            tmpSolution = Solution(T, valWithCounter)
                            t = valWithCounter + 1
                            break@searchTree
                        }
                    }

                    if (T.size < k)
                        ext.removeLast()

                    if (T.size > 0) {

                        curCutValue -= g.degreeOf(T.last()) - (2 * T.count { g.areNB(it, T.last()) })

                        T.removeLast()
                    }

                } else { // ##### BRANCH #####

                    val newElem = ext.last().random()
                    ext.last().remove(newElem)

                    if ((T.size < k - 1)) // you're not adding a leaf to the search tree
                        ext.add(ext.last().toMutableList())

                    curCutValue += g.degreeOf(newElem) - (2 * T.count { g.areNB(it, newElem) })

                    T.add(newElem)
                }
            }

            if (tmpSolution == null)
                break@tIncreaseLoop
            else
                bestSolution = tmpSolution
        }

        return bestSolution
    }
}
