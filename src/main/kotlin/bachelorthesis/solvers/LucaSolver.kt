package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import util.collections.incrementLast

class LucaSolver<V>(protected val g: SimpleGraph<V>, k: Int) : AbstractSolver<V>(g, k) {

    fun calcResult(): Solution<V> {

        // variables for keeping track of the best result
        var bestCutValue = -1
        lateinit var bestIndices: List<Int>

        // variables for iterating over the subsets
        var nextAction = NextAction.STAY
        val indices = ArrayList<Int>().apply { add(-1) }

        fun spacesLeft() = k - indices.size + 1


        while (indices.isNotEmpty()) {
            when (nextAction) {
                NextAction.UP -> {
                    indices.removeLast()
                    nextAction = NextAction.STAY
                }

                NextAction.DOWN -> {
                    indices.add(indices.last())
                    nextAction = NextAction.STAY
                }

                NextAction.STAY -> {
                    if (indices.last() < g.size - spacesLeft()) {      // enough space left
                        // modify indices
                        indices.incrementLast()
                        if (indices.size < k) {   // has full size
                            nextAction = NextAction.DOWN
                        } else {
                            val currCutValue = cutSize(g, indices.map { vertexList[it] })

                            // check subset
                            if (bestCutValue == -1 || currCutValue > bestCutValue) {
                                bestCutValue = currCutValue
                                bestIndices = indices.toList() // copy
                            }
                        }
                    } else
                        nextAction = NextAction.UP
                }

            }
        }

        val bestSet = bestIndices.mapTo(HashSet()) { vertexList[it] }
        return Solution(bestSet, bestCutValue)
    }

}