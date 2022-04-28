package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import util.collections.incrementLast

class TreeSolver<V>(protected val g: SimpleGraph<V>, private val k: Int) {

    enum class NextAction { UP, STAY, DOWN }

    private val vertexList = g.vertices().toList()

    var bestSolution = Solution(emptyList<V>(), Int.MIN_VALUE)

    fun calcResult(): Solution<V> {

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
                            if (bestSolution.value == Int.MIN_VALUE || cutValue > bestSolution.value)
                                bestSolution = Solution(indices.mapTo(HashSet()) { vertexList[it] }, cutValue)
                        }
                    }
                }
            }

        return bestSolution
    }
}