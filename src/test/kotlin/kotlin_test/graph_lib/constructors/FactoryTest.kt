package kotlin_test.graph_lib.constructors

import graphlib.constructors.Factory.createBipartite
import graphlib.constructors.Factory.createClique
import graphlib.constructors.Factory.createCycle
import graphlib.constructors.Factory.createPath
import graphlib.constructors.Factory.createStar
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class FactoryTest {

    @Test
    fun createPath1() {
        val g = createPath(4)
        assertEquals(4, g.size())
        assertTrue { g.isConnected(1, 2) }
        assertTrue { g.isConnected(2, 3) }
        assertTrue { g.isConnected(3, 4) }
    }

    @Test
    fun createCycle1() {
        val g = createCycle(5)
        assertEquals(5, g.size())
        assertTrue { g.isConnected(1, 2) }
        assertTrue { g.isConnected(2, 3) }
        assertTrue { g.isConnected(3, 4) }
        assertTrue { g.isConnected(4, 5) }
        assertTrue { g.isConnected(5, 1) }
    }

    @Test
    fun createClique1() {
        val g = createClique(5)
        assertEquals(10, g.edgeCount())
        for (i in 1..5)
            for (j in 1..5)
                if (i != j)
                    assertTrue { g.isConnected(i, j) }
    }

    @Test
    fun createStar1() {
        val g = createStar(5)
        assertEquals(4, g.edgeCount())
        for (i in 2..5)
            assertTrue { g.isConnected(1, i) }
    }

    @Nested
    internal inner class BipartiteTest {

        @Test
        fun illegalSizes() {
            assertThrows(IllegalArgumentException::class.java) { createBipartite(0, 0) }
        }

        @Test
        fun size_1_1() {
            val g = createBipartite(1, 1)
            assertEquals(2, g.size())
            assertEquals(1, g.edgeCount())
            assertTrue(g.isConnected(0, 1))
        }

        @Test
        fun size_2_4() {
            val g = createBipartite(2, 4)
            assertEquals(8, g.edgeCount())
            assertTrue(g.isConnected(0, 2))
            assertTrue(g.isConnected(0, 3))
            assertTrue(g.isConnected(0, 4))
            assertTrue(g.isConnected(0, 5))
            assertFalse(g.isConnected(0, 1))
            assertFalse(g.isConnected(0, 6))
            (0 until 2).forEach { assertEquals(4, g.degreeOf(it)) }
            (2 until 6).forEach { assertEquals(2, g.degreeOf(it)) }
        }

        @Test
        fun size_99_100() {
            val g = createBipartite(99, 100)
            assertEquals(9900, g.edgeCount())
            assertEquals(199, g.size())
            assertTrue(g.isConnected(98, 101))
            assertTrue(g.isConnected(54, 179))
            assertTrue(g.isConnected(3, 198))
            assertFalse(g.isConnected(45, 199))
            assertFalse(g.isConnected(136, 199))
        }
    }
}
