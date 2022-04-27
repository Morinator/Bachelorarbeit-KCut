package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize

class LucaSolver<V>(protected val g: SimpleGraph<V>, k: Int) : AbstractSolver<V>(g, k) {

    fun calcResult(): Solution<V> {

        // variables for keeping track of the best result
        var bestCutValue = -1
        var bestIndices = IntArray(0)

        // variables for iterating over the subsets
        var nextAction = NextAction.STAY
        val setIndices = IntArray(k) { -1 }
        var nextI = 0
        var spacesLeft = k


        while (nextI > 0 || nextAction != NextAction.UP) {
            when (nextAction) {
                NextAction.UP -> {
                    nextI--
                    spacesLeft++
                    nextAction = NextAction.STAY
                }

                NextAction.DOWN -> {
                    nextI++
                    spacesLeft--
                    setIndices[nextI] = setIndices[nextI - 1]
                    nextAction = NextAction.STAY
                }

                NextAction.STAY -> {
                    if (setIndices[nextI] < g.size - spacesLeft) {      // enough space left
                        // modify indices
                        setIndices[nextI]++

                        if (nextI == k - 1) {   // has full size
                            val currCutValue = cutSize(g, setIndices.map { vertexList[it] })

                            // check subset
                            if (bestCutValue == -1 || currCutValue > bestCutValue) {
                                bestCutValue = currCutValue
                                bestIndices = setIndices.clone()
                            }
                        } else
                            nextAction = NextAction.DOWN
                    } else
                        nextAction = NextAction.UP
                }

            }
        }

        val bestSet = HashSet<V>()
        bestIndices.forEach { bestSet.add(vertexList[it]) }

        return Solution(bestSet, bestCutValue)
    }

}