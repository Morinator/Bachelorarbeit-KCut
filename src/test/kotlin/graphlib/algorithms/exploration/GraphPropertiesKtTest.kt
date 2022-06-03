package graphlib.algorithms.exploration

import graphlib.constructors.Factory.createBipartite
import graphlib.constructors.Factory.createClique
import graphlib.constructors.Factory.createCycle
import graphlib.constructors.Factory.createPath
import graphlib.constructors.Factory.createStar
import graphlib.datastructures.SimpleGraph
import graphlib.properties.`is c closed`
import graphlib.properties.hIndex
import graphlib.properties.isTree
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class GraphPropertiesTest {

    @Nested
    internal inner class IsTree {
        @Test
        fun basicShapes1() {
            assertTrue(isTree(createStar(10)))
            assertTrue(isTree(createPath(10)))
            assertFalse(isTree(createCycle(10)))
            assertFalse(isTree(createClique(10)))
            assertFalse(isTree(createBipartite(10, 20)))
        }
    }

    @Nested
    internal inner class HIndex {
        @Test
        fun star4() {
            assertEquals(1, hIndex(createStar(4)))
        }

        @Test
        fun path5() {
            assertEquals(2, hIndex(createPath(5)))
        }

        @Test
        fun clique8() {
            assertEquals(7, hIndex(createClique(8)))
        }

        @Test
        fun emptyGraph() {
            assertEquals(0, hIndex(SimpleGraph<Int>()))
        }
    }

    @Nested
    internal inner class `C Closure` {
        @Test
        fun star4() {
            assertFalse(`is c closed`(createStar(4), 1))
        }

        @Test
        fun bipartite_2_2() {
            assertFalse(`is c closed`(createBipartite(2, 2), 2))
        }

        @Test
        fun bipartite_3_3() {
            val g = createBipartite(3, 3)
            g.addEdge(1, 2); g.addEdge(1, 3); g.addEdge(2, 3)
            g.addEdge(4, 5); g.addEdge(4, 6); g.addEdge(5, 6)
            assertTrue(`is c closed`(g, 3))
        }

        @Test
        fun emptyGraph() {
            assertEquals(0, hIndex(SimpleGraph<Int>()))
        }
    }
}
