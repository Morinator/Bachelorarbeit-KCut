package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize

//TODO Contribution durch suchbaum tracken

//TODO sufficient & needless

class StackSolver : DecisionSolver<Int> {

    override fun calc(t: Int, g: SimpleGraph<Int>, k: Int): Solution<Int>? {

        val extension = mutableListOf(HashSet(g.vertices()))
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
                    extension.add(HashSet(extension.last())) // duplicate last

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
