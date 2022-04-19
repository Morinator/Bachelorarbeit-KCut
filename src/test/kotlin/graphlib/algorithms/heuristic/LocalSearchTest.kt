package graphlib.algorithms.heuristic

import graphlib.algorithms.bipartite.cutSize
import graphlib.constructors.Factory.createBipartite
import graphlib.constructors.Factory.createStar
import graphlib.datastructures.Solution
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LocalSearchTest {

    @Test
    fun localSearchStep() { // k := 1
        val star = createStar(10)

        // init a bad solution
        val solution = Solution(mutableSetOf(3), cutSize(star, setOf(3)))
        localSearchStep(star, solution, ::cutSize)

        assertEquals(Solution(vertices = mutableSetOf(1), value = 9), solution)
    }

    @Test
    fun getHeuristic() {
        val g = createBipartite(2, 10)
        val sol = getHeuristic(g, 2)
        assertEquals(Solution(mutableSetOf(1, 2), 20), sol)
    }
}
