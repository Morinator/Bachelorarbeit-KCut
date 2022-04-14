package graphlib.algorithms.heuristic

import graphlib.algorithms.bipartite.cutSize
import graphlib.constructors.Factory.createStar
import graphlib.datastructures.Solution
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LocalSearchTest {

    @Test
    fun localSearchStep() { // k := 1
        val star = createStar(10)

        // init a bad solution
        val solution = Solution(mutableSetOf(3), cutSize(star, setOf(3)))
        localSearchStep(star, solution)

        assertEquals(Solution(vertices = mutableSetOf(1), value = 9), solution)
    }
}
