package graphlib.constructors

import graphlib.constructors.GraphFileReader.edgesFromPath
import graphlib.constructors.GraphFileReader.graphFromEdges
import graphlib.constructors.GraphFileReader.graphFromPath
import graphlib.constructors.GraphFileReader.validateLine
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GraphFileReaderTest {

    private val edgesSmall1 = listOf(Pair(1, 2), Pair(1, 8), Pair(2, 3), Pair(3, 4), Pair(3, 5), Pair(3, 6), Pair(3, 7))

    @Test
    fun validateLine1() {
        assertFalse(validateLine("#bla"))
        assertFalse(validateLine("%bla"))
        assertTrue(validateLine("1"))
        assertTrue(validateLine("a"))
    }

    @Test
    fun edgesFromPath1() {
        assertEquals(
            edgesSmall1,
            edgesFromPath("data/graphs/small1.txt")
        )
    }

    @Test
    fun graphFromFile1() {
        val gRead = graphFromPath("data/graphs/small1.txt")
        val gTruth = graphFromEdges(edgesSmall1)
        assertEquals(gTruth, gRead)
    }
}
