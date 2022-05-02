package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize


class StackSolver : DecisionSolver<Int> {

    override fun calc(t: Int, g: SimpleGraph<Int>, k: Int): Solution<Int>?{

        val free = mutableListOf(HashSet(g.vertices()))
        val curr: MutableList<Int> = ArrayList()

        while (free.isNotEmpty())
            if (curr.size < k && free.last().isNotEmpty()) { //branch

                val newElem = free.last().first()
                free.last().remove(newElem)

                //if (curr.size != k-1)
                free.add(HashSet(free.last())) // duplicate last

                curr.add(newElem)

            } else { // backtrack

                if (curr.size == k) {
                    val cutSize = cutSize(g, curr)
                    if (cutSize >= t)
                        return Solution(curr, cutSize)
                }

                if (curr.size > 0)
                    curr.removeLast()

                //if (curr.size != k)
                    free.removeLast()
            }

        return null
    }

}