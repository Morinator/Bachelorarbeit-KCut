package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution

class ValueWrapper(
    protected val g: SimpleGraph<Int>,
    private val k: Int,
    private val decider: DecisionSolver<Int>
) {

    fun calc(): Solution<Int> {
        var bestSolution = Solution<Int>()

        val upperBound = g.degreeSequence.takeLast(k).sum()



        val counter: MutableMap<Int, Int> = g.vertices().associateWithTo(HashMap()) { 0 }

        // Reduction Rule 4.1

        val verticessss = g.vertices().sortedByDescending { g.degreeOf(it) + counter[it]!! }
        val restOfList = verticessss.drop((g.maxDegree*k)+1)
        println("size of rule 4.1: ${restOfList.size}")
        restOfList.forEach {  v->
            g[v].forEach {  counter[it] = counter[it]!! + 1}
            g.deleteVertex(v)
        }


        for (t in 0..upperBound) {




            val result = decider.calc(t, g, k, counter)





            if (result == null)
                break
            else
                bestSolution = result
        }

        return bestSolution
    }
}
