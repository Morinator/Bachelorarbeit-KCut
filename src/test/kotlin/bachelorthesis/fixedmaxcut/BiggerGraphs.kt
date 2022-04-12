package bachelorthesis.fixedmaxcut

import bachelorthesis.solve
import graphlib.constructors.FileReader.graphFromPath
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BiggerGraphs {

    @Test
    fun solveA() {
        val g = graphFromPath("data/graphs/customGraph1.txt")
        assertEquals(24, solve(g, 5).value)
    }

    @Test   // ~700ms
    fun solveB() {
        val g = graphFromPath("data/graphs/random/100-0.1.edges")
        assertEquals(56, solve(g, 3).value)
    }
}