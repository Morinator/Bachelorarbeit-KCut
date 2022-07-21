package graphlib.algorithms.heuristic

import graphlib.constructors.Factory.createBipartite
import graphlib.constructors.Factory.createStar
import graphlib.datastructures.Solution
import graphlib.properties.cutSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LocalSearchTest {

    @Test
    fun localSearchStep() { // k := 1
        val star = createStar(10)

        // init a bad solution
        val solution = Solution(mutableSetOf(3), cutSize(star, setOf(3)))
        graphlib.heuristic.localSearchStep(star, solution, ::cutSize)

        assertEquals(Solution(V = mutableSetOf(1), value = 9), solution)
    }

    @Test
    fun getHeuristic() {
        val g = createBipartite(2, 10)
        val sol = graphlib.heuristic.localSearchRun(g, 2)
        assertEquals(Solution(mutableSetOf(1, 2), 20), sol)
    }
}
