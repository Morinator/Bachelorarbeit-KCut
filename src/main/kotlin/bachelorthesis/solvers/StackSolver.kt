package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize

class StackSolver(private val g: SimpleGraph<Int>, private val k: Int) {

    fun calc(): Solution<Int> {

        var bestSolution = Solution<Int>()
        val counter: MutableMap<Int, Int> = g.vertices().associateWithTo(HashMap()) { 0 }

        fun valWithCounter(S: Collection<Int>): Int = cutSize(g, S) + S.sumOf { counter[it]!! }
        fun cont(v: Int, T: Collection<Int>) = g.degreeOf(v) + counter[v]!! - (2 * (g[v] intersect T.toSet()).size)

        fun kTimesDeltaRule() {
            val verticesWhereRuleApplies = g.vertices()
                .sortedByDescending { g.degreeOf(it) + counter[it]!! }
                .drop((g.maxDegree * k) + 1)
            println("size of rule 4.1: ${verticesWhereRuleApplies.size}")
            for (v in verticesWhereRuleApplies) {
                g[v].forEach { counter[it] = counter[it]!! + 1 }
                g.deleteVertex(v)
            }
        }

        kTimesDeltaRule()


        println("aaaaaaaa")
        for (v in g.vertices()) {
            println("${valWithCounter(setOf(v))}   ${g.degreeOf(v) + counter[v]!!}")
        }

        tIncreaseLoop@ for (t in 0..g.degreeSequence.takeLast(k).sum()) {

            val T = ArrayList<Int>()
            val extension = mutableListOf(g.vertices().toMutableList())
            //val branchedStack = mutableListOf(false) // used for satisfactory vertices

            //fun kLeft(): Int = k - T.size
            //fun tLeft(): Int = t - valWithCounter(T)
            // fun checkSatisfactory() = extension.last().any { v -> cont(v, T) >= tLeft() / kLeft() + 2 * (k - 1) }

            //val satisfactoryStack = mutableListOf(checkSatisfactory())

            var tmpSolution: Solution<Int>? = null

            searchTree@ while (extension.isNotEmpty()) {

                //val applySatisfactoryRule = satisfactoryStack.last() && branchedStack.last()

                if (T.size < k && // ### branch ###
                    T.size + extension.last().size >= k
                ) {

                    //branchedStack[branchedStack.lastIndex] = true
                    //branchedStack.add(false)

                    val newElem = extension.last().first()
                    extension.last().remove(newElem)

                    val updateBranches = (T.size < k - 1)
                    if (updateBranches) {
                        extension.add(extension.last().toMutableList())
                        extension.last().sortByDescending { cont(it, T) }
                    }

                    T.add(newElem)

                    //if (updateBranches)
                       // satisfactoryStack.add(checkSatisfactory())

                } else { // ### backtrack ###

                    if (T.size == k) {
                        val cutSize = valWithCounter(T)
                        if (cutSize >= t) {
                            tmpSolution = Solution(T, cutSize)
                            break@searchTree
                        }
                    }

                    if (T.size != k) {
                        extension.removeLast()
                       // satisfactoryStack.removeLast()
                    }

                    if (T.size > 0) {
                        T.removeLast()
                    }

                    //branchedStack.removeLast()
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
