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

        val verticesToDrop = g.vertices()
            .sortedByDescending { g.degreeOf(it) + counter[it]!! }
            .drop((g.maxDegree * k) + 1)
        println("size of rule 4.1: ${verticesToDrop.size}")
        verticesToDrop.forEach { v ->
            g[v].forEach { counter[it] = counter[it]!! + 1 }
            g.deleteVertex(v)
        }

        // ############################  stuff for annotation  ############################
        fun counterMany(S: Collection<Int>): Int = S.sumOf { counter[it]!! }
        fun degPlusC(v: Int) = g.degreeOf(v) + counter[v]!!
        fun valWithCounter(S: Collection<Int>): Int = cutSize(g, S) + counterMany(S)
        fun cont(v: Int, T: Collection<Int>): Int = degPlusC(v) - (2 * (g[v] intersect T).size)

        // ############################  stuff for annotation  ############################

        tCounter@ for (t in 0..upperBound) {

            val extension: MutableList<MutableList<Int>> = mutableListOf(g.vertices().toMutableList())

            val T: MutableList<Int> = ArrayList()

            fun kMissing(): Int = k - T.size
            fun tMissing(): Int = t - valWithCounter(T)

            fun checkForSatisfactory(vertices: Collection<Int>) = vertices.any { v ->
                cont(v, T) >= tMissing() / kMissing() + 2 * (k - 1)
            }

            var tmp: Solution<Int>? = null

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
                        val cutSize = valWithCounter(T)
                        if (cutSize >= t) {
                            tmp = Solution(T, cutSize)
                            break@searchTree
                        }
                    }

                    if (T.size != k)
                        extension.removeLast()

                    if (T.size > 0)
                        T.removeLast()
                }
            val result = tmp

            if (result == null)
                break@tCounter
            else
                bestSolution = result
        }

        return bestSolution
    }
}
