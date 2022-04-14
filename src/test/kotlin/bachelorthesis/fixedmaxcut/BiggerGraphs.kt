package bachelorthesis.fixedmaxcut

import bachelorthesis.solveBruteForce
import graphlib.constructors.GraphFileReader.graphFromPath
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BiggerGraphs {

    @Test
    fun solveA() {
        val g = graphFromPath("data/graphs/customGraph1.txt")
        assertEquals(24, solveBruteForce(g, 5).value)
    }

    @Test // ~700ms
    fun solveB() {
        val g = graphFromPath("data/graphs/random/100-0.1.edges")
        assertEquals(56, solveBruteForce(g, 3).value)
    }
}
