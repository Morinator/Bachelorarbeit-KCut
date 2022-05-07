package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize

//TODO Contribution durch suchbaum tracken

//TODO sufficient & needless

class StackSolver : DecisionSolver<Int> {

    override fun calc(t: Int, g: SimpleGraph<Int>, k: Int): Solution<Int>? {


        // stuff for annotation
        // ###############################################################################
        val counter : MutableMap<Int, Int> = g.vertices().associateWithTo(HashMap()) { 0 }

        fun counter(S : Collection<Int>) : Int = S.sumOf { counter[it]!! }

        fun degPlusC(v : Int) = g.degreeOf(v) + counter[v]!!

        /**
         * Contribution of a vertex, as in Definition 3.1
         */
        fun cont(v: Int, T: Collection<Int>): Int = degPlusC(v) - (2* (g[v] intersect T).size)

        // ###############################################################################

        val extension : MutableList<MutableList<Int>> = mutableListOf(g.vertices().toMutableList())
        val T: MutableList<Int> = ArrayList()

        while (extension.isNotEmpty())


            if (T.size < k && // branch
                extension.last().isNotEmpty() &&
                T.size + extension.last().size >= k
            ) {

                // Idee: hier Knoten nach contribution sortieren

                val newElem = extension.last().random()
                extension.last().remove(newElem)

                if (T.size != k-1)
                    extension.add(extension.last().toMutableList()) // duplicate last

                T.add(newElem)


            } else { // backtrack

                if (T.size == k) {
                    val cutSize = cutSize(g, T)
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
