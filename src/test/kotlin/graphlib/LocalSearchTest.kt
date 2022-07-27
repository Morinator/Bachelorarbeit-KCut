package graphlib

import bachelorthesis.cutSize
import graphlib.Factory.createBipartite
import graphlib.Factory.createStar
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LocalSearchTest {

    @Test
    fun localSearchStep() { // k := 1
        val star = createStar(10)
        val S = mutableSetOf(3)  // init a bad solution
        localSearchStep(star, S, ::cutSize)

        assertEquals(mutableSetOf(1) to 9, S to cutSize(star, S))
    }

    @Test
    fun getHeuristic() {
        val G = createBipartite(2, 10)
        val S = localSearchRun(G, 2)
        assertEquals(mutableSetOf(1, 2) to 20, S to cutSize(G, S))
    }
}
