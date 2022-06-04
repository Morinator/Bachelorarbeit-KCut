package graphlib.constructors

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RandomGraphsTest {

    @Test
    fun fromRandomEdges1() {
        val g1 = RandomGraphs.fromRandomEdges(5, 6)
        assertEquals(5, g1.size)
        assertEquals(6, g1.edgeCount)
    }
}