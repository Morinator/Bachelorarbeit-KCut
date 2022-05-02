package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize

class RecursiveSolver : DecisionSolver<Int> {

    override fun calc(t: Int, g: SimpleGraph<Int>, k: Int): Solution<Int>? =
        subsetTreeRecursive(mutableListOf(), g.vertices().toMutableList(), k, g, t)

    private fun subsetTreeRecursive(
        curr: MutableList<Int>,
        free: MutableCollection<Int>,
        k: Int,
        g: SimpleGraph<Int>,
        t: Int
    ): Solution<Int>? {

        if (curr.size == k) {
            val cutSize = cutSize(g, curr)
            if (cutSize >= t)
                return Solution(curr, cutSize)
        } else {
            for (e in free.toList().sorted()) {
                free.remove(e)

                curr.add(e)

                val newFree = if (curr.size + 1 == k) free else HashSet(free)

                val result = subsetTreeRecursive(curr, newFree, k, g, t)
                if (result != null)
                    return result
            }
        }
        if (curr.size > 0)
            curr.removeLast()

        return null
    }

}