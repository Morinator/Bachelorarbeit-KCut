package bachelorthesis.solvers.indexbased

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import util.collections.incrementLast

class DecisionSolver<V>(protected val g: SimpleGraph<V>, private val k: Int) {

    enum class NextAction { UP, STAY, DOWN }

    private val vertexList = g.vertices().toList()

    /**
     * @return The solution if there is one with a value of at least t and null otherwise.
     */
    fun run(t: Int): Solution<V>? {

        // variables for iterating over the subsets
        var nextAction = NextAction.STAY
        val indices = ArrayList<Int>().apply { add(-1) }

        val counter: MutableMap<Int, Int> = HashMap()
        indices.forEach { counter[it] = 0 }

        fun spacesLeft() = k - indices.size + 1

        while (indices.isNotEmpty())
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
                    if (indices.last() >= g.size - spacesLeft())
                        nextAction = NextAction.UP
                    else {
                        indices.incrementLast()
                        if (indices.size < k)
                            nextAction = NextAction.DOWN
                        else { // size == k, so subset is full
                            val cutValue = cutSize(g, indices.map { vertexList[it] })
                            if (cutValue >= t)
                                return Solution(indices.mapTo(HashSet()) { vertexList[it] }, cutValue)
                        }
                    }
                }
            }

        return null
    }
}