package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize

/**
 * Satisfactory: Da absteigend sortiert nach contribution, muss entweder der erste Knoten satisfactory sein oder keiner
 * -> wenn er es ist und trotzdm. keine Lösung gefunden wurde, kann man dierekt nochaml backtracken.
 *
 */

class StackSolver(
    protected val g: SimpleGraph<Int>,
    private val k: Int
) {

    fun calc(): Solution<Int> {
        var bestSolution = Solution<Int>()

        val upperBound = g.degreeSequence.takeLast(k).sum()

        val counter: MutableMap<Int, Int> = g.vertices().associateWithTo(HashMap()) { 0 }

        // Reduction Rule 4.1

        val verticessss = g.vertices().sortedByDescending { g.degreeOf(it) + counter[it]!! }
        val restOfList = verticessss.drop((g.maxDegree * k) + 1)
        println("size of rule 4.1: ${restOfList.size}")
        restOfList.forEach { v ->
            g[v].forEach { counter[it] = counter[it]!! + 1 }
            g.deleteVertex(v)
        }

        // ############################  stuff for annotation  ############################
        fun counter1(S: Collection<Int>): Int = S.sumOf { counter[it]!! }
        fun degPlusC(v: Int) = g.degreeOf(v) + counter[v]!!
        fun valG(S: Collection<Int>): Int = cutSize(g, S) + counter1(S)

        /**
         * Contribution of a vertex, as in Definition 3.1
         */
        fun cont(v: Int, T: Collection<Int>): Int = degPlusC(v) - (2 * (g[v] intersect T).size)
        // ############################  stuff for annotation  ############################

        tCounter@ for (t in 0..upperBound) {

            val extension: MutableList<MutableList<Int>> = mutableListOf(g.vertices().toMutableList())
            val T: MutableList<Int> = ArrayList()

            var result1: Solution<Int>? = null

            searchTree@ while (extension.isNotEmpty())

                if (T.size < k && // branch
                    extension.last().isNotEmpty() &&
                    T.size + extension.last().size >= k
                ) {

                    val newElem = extension.last().first()
                    extension.last().remove(newElem)

                    if (T.size != k - 1) {
                        extension.add(extension.last().toMutableList()) // duplicate last
                        extension.last().sortByDescending { cont(it, T) } // try vertices with high contribution first
                        // needless rauschmeißen
                    }

                    T.add(newElem)
                } else { // backtrack

                    if (T.size == k) {
                        val cutSize = valG(T)
                        if (cutSize >= t) {
                            result1 = Solution(T, cutSize)
                            break@searchTree
                        }
                    }

                    if (T.size != k)
                        extension.removeLast()

                    if (T.size > 0)
                        T.removeLast()
                }
            val result = result1

            if (result == null)
                break@tCounter
            else
                bestSolution = result
        }

        return bestSolution
    }
}
