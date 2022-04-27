package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize

class LucaSolver<V>(protected val g: SimpleGraph<V>, k: Int) : AbstractSolver<V>(g, k)  {

    fun calcResult(): Solution<V> {

        // variables for keeping track of the best result
        var bestCut = -1
        var bestIndices = IntArray(0)

        // variables for iterating over the subsets
        var nextAction = NextAction.STAY
        val setIndices = IntArray(k) { -1 }
        var nextI = 0
        var spacesLeft = k - nextI

        // variables for dynamically saving important values while iterating over the subsets
        var currVI: Int
        var currCut = 0


        while (nextI > 0 || nextAction != NextAction.UP) {
            when (nextAction) {
                NextAction.UP -> {
                    nextI--
                    spacesLeft = k - nextI
                    nextAction = NextAction.STAY
                }
                
                NextAction.STAY -> {
                    if (setIndices[nextI] < g.size - spacesLeft) {
                        // modify indices
                        currVI = ++setIndices[nextI]

                        currCut = cutSize(g, setIndices.map { vertexList[it] })


                        if (nextI == k - 1) {
                            // check subset
                            if (bestCut == -1 || currCut < bestCut) {
                                bestCut = currCut
                                bestIndices = setIndices.clone()
                            }
                        } else {
                            nextAction = NextAction.DOWN
                        }
                    } else {
                        nextAction = NextAction.UP
                    }
                }

                NextAction.DOWN -> {
                    nextI++
                    spacesLeft = k - nextI
                    setIndices[nextI] = setIndices[nextI - 1]
                    nextAction = NextAction.STAY
                }
            }
        }

        val bestSet = HashSet<V>()
        bestIndices.forEach { bestSet.add(vertexList[it]) }

        return Solution(bestSet, bestCut)
    }


}