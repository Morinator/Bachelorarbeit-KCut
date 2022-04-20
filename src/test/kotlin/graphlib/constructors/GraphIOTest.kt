package graphlib.constructors

import graphlib.constructors.GraphIO.edgesFromPath
import graphlib.constructors.GraphIO.graphFromEdges
import graphlib.constructors.GraphIO.graphFromPath
import graphlib.constructors.GraphIO.validateLine
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GraphIOTest {

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

    @Test
    fun ignoresWeightsInFile() {
        val gRead = graphFromPath("data/graphs/small-weighted-graph.txt")
        val gTruth = graphFromEdges(listOf(1 to 2, 1 to 3, 8 to 9))
        assertEquals(gTruth, gRead)
    }
}
