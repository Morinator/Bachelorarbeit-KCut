package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import util.collections.CombinationIterator

class DecisionSolver<V>(protected val g: SimpleGraph<V>,  k: Int) : Algo<V>(g, k) {

    /**
     * @return A [Solution] of size [k] with a value of at least [t] if possible, or *null* otherwise.
     */
    fun calcResult(t: Int): Solution<V>? {
        for (c in CombinationIterator(vertexList, k))
            if (cutSize(g, c) >= t) return Solution(c.toMutableSet(), cutSize(g, c))
        return null
    }


    // TODO doesn't work yet sadly
    fun calcResultLuca(): Solution<V> {

        // variables for keeping track of the best result
        var bestCut = -1
        var bestIndices = IntArray(0)

        // variables for iterating over the subsets
        var nextAction = NextAction.STAY
        val setIndices = IntArray(k) { -1 }
        var nextI = 0
        var spacesLeft = k - nextI

        // variables for dynamically saving important values while iterating over the subsets
        var curVI: Int
        var curCut = 0


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
                        curVI = ++setIndices[nextI]

                        curCut = cutSize(g, setIndices.map { vertexList[it] })


                        if (nextI == k - 1) {
                            // check subset
                            if (bestCut == -1 || curCut < bestCut) {
                                bestCut = curCut
                                bestIndices = setIndices.clone()
                            }
                            checkedSubsets++
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