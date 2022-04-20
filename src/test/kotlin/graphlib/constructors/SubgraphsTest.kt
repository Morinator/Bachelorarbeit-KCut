package graphlib.constructors

import graphlib.constructors.Factory.createCycle
import graphlib.constructors.Factory.createPath
import graphlib.constructors.Factory.createStar
import graphlib.constructors.Factory.graphFromEdges
import graphlib.datastructures.SimpleGraph
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SubgraphsTest {

    @Test
    fun subPath() {
        val g = createPath(6)
        val sub = inducedSubgraph(g, setOf(2, 3, 4))
        val correct = graphFromEdges(listOf(2 to 3, 3 to 4))
        assertEquals(correct, sub)
    }

    @Test
    fun isolatedVertices() {
        val g = createStar(6)
        val sub = inducedSubgraph(g, setOf(4, 5, 6))
        val correct = SimpleGraph<Int>().apply { addVertex(4); addVertex(5); addVertex(6) }
        assertEquals(correct, sub)
    }

    @Test
    fun emptySubgraph() {
        val g = createCycle(6)
        val sub = inducedSubgraph(g, emptySet())
        val correct = SimpleGraph<Int>()
        assertEquals(correct, sub)
    }
}
