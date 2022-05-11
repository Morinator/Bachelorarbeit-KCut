package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize

//TODO sufficient & needless


/**
 * Satisfactory: Da absteigend sortiert nach contribution, muss entweder der erste Knoten satisfactory sein oder keiner
 * -> wenn er es ist und trotzdm. keine Lösung gefunden wurde, kann man dierekt nochaml backtracken.
 *
 */
class StackSolver : DecisionSolver<Int> {

    override fun calc(t: Int, g: SimpleGraph<Int>, k: Int, counter: MutableMap<Int, Int>): Solution<Int>? {

        // ############################  stuff for annotation  ############################

        fun counter(S: Collection<Int>): Int = S.sumOf { counter[it]!! }

        fun degPlusC(v: Int) = g.degreeOf(v) + counter[v]!!

        fun valG(S: Collection<Int>): Int = cutSize(g, S) + counter(S)

        /**
         * Contribution of a vertex, as in Definition 3.1
         */
        fun cont(v: Int, T: Collection<Int>): Int = degPlusC(v) - (2 * (g[v] intersect T).size)

        // ############################  stuff for annotation  ############################

        val extension: MutableList<MutableList<Int>> = mutableListOf(g.vertices().toMutableList())
        val T: MutableList<Int> = ArrayList()

        while (extension.isNotEmpty())


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
                    if (cutSize >= t)
                        return Solution(T, cutSize)
                }

                if (T.size != k)
                    extension.removeLast()

                if (T.size > 0)
                    T.removeLast()
            }
        return null
    }
}
