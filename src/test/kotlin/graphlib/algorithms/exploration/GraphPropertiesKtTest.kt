package graphlib.algorithms.exploration

import graphlib.constructors.Factory.createBipartite
import graphlib.constructors.Factory.createClique
import graphlib.constructors.Factory.createCycle
import graphlib.constructors.Factory.createPath
import graphlib.constructors.Factory.createStar
import graphlib.datastructures.SimpleGraph
import graphlib.properties.checkIfTree
import graphlib.properties.hIndex
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
            assertTrue(checkIfTree(createStar(10)))
            assertTrue(checkIfTree(createPath(10)))
            assertFalse(checkIfTree(createCycle(10)))
            assertFalse(checkIfTree(createClique(10)))
            assertFalse(checkIfTree(createBipartite(10, 20)))
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
}
