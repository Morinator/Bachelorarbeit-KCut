package graphlib

import graphlib.Factory.createBipartite
import graphlib.Factory.createClique
import graphlib.Factory.createPath
import graphlib.Factory.createStar
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class FactoryTest {

    @Test
    fun createPath1() {
        val g = createPath(4)
        assertEquals(4, g.size)
        assertTrue { g.areNB(1, 2) }
        assertTrue { g.areNB(2, 3) }
        assertTrue { g.areNB(3, 4) }
    }


    @Test
    fun createClique1() {
        val g = createClique(5)
        assertEquals(10, g.edgeCount)
        for (i in 1..5)
            for (j in 1..5)
                if (i != j)
                    assertTrue { g.areNB(i, j) }
    }

    @Test
    fun createStar1() {
        val g = createStar(5)
        assertEquals(4, g.edgeCount)
        for (i in 2..5)
            assertTrue { g.areNB(1, i) }
    }

    @Nested
    internal inner class BipartiteTest {

        @Test
        fun size_1_1() {
            val g = createBipartite(1, 1)
            assertEquals(2, g.size)
            assertEquals(1, g.edgeCount)
            assertTrue(g.areNB(1, 2))
        }

        @Test
        fun size_2_4() {
            val g = createBipartite(2, 4)
            assertEquals(8, g.edgeCount)
            assertTrue(g.areNB(1, 3))
            assertTrue(g.areNB(1, 4))
            assertTrue(g.areNB(1, 5))
            assertTrue(g.areNB(1, 6))
            assertFalse(g.areNB(1, 2))
            (1..2).forEach { assertEquals(4, g.degreeOf(it)) }
            (3..6).forEach { assertEquals(2, g.degreeOf(it)) }
        }

        @Test
        fun size_99_100() {
            val g = createBipartite(99, 100)
            assertEquals(9900, g.edgeCount)
            assertEquals(199, g.size)
            assertTrue(g.areNB(98, 101))
            assertTrue(g.areNB(54, 179))
            assertTrue(g.areNB(3, 198))
            assertFalse(g.areNB(136, 199))
        }
    }
}
