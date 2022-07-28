package graphlib

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class MyGraphTest {

    private val g = MyGraph<Int>() // looks like:       0-1-2  3

    @BeforeEach
    fun setUp() {
        g.addEdge(0, 1)
        g.addEdge(1, 2)
        g.addVertex(3)
    }

    @Test
    fun addVertex() {
        assertFalse { 4 in g.V }
        g.addVertex(4)
        assertTrue { 4 in g.V }
        g.addVertex(4)
        assertTrue { 4 in g.V }
    }

    @Test
    fun addEdge1() {
        assertFalse { g.areNB(1, 3) }
        g.addEdge(1, 3)
        assertTrue { g.areNB(1, 3) }
    }

    @Test
    fun addEdge2() {
        val g2 = MyGraph<Int>() // is empty
        assertThrows<java.lang.IllegalArgumentException> {g2.addEdge(1, 1)  }
    }

    @Test
    fun get() {
        assertEquals(setOf(0, 2), g[1].toSet())
        g.addEdge(1, 3)
        assertEquals(setOf(0, 2, 3), g[1].toSet())
    }

    @Test
    fun contains() {
        for (i in 0..3) assertTrue { i in g.V }
    }

    @Test
    fun deleteVertex() {
        assertTrue { 2 in g.V }
        g.deleteVertex(2)
        assertFalse { 2 in g.V }
        assertEquals(setOf(0), g[1].toSet()) // it's not a neighbour of vertex 1 anymore
    }

    @Test
    fun isConnected() {
        assertTrue { g.areNB(0, 1) }
        assertTrue { g.areNB(1, 2) }
    }

    @Test
    fun size() {
        assertEquals(4, g.size)
        g.deleteVertex(1)
        assertEquals(3, g.size)
    }

    @Test
    fun deleteEdge() {
        assertTrue { g.areNB(0, 1) }
        g.deleteEdge(0, 1)
        assertFalse { g.areNB(0, 1) }
        assertTrue { 0 in g.V && 1 in g.V }
        g.deleteEdge(0, 1)
        assertFalse { g.areNB(0, 1) }
    }

    @Test
    fun edgeCount() {
        assertEquals(2, g.edgeCount)
        g.addEdge(4, 5)
        assertEquals(3, g.edgeCount)

        for (v in 0..5)
            g.deleteVertex(v)
        assertEquals(0, g.edgeCount) // no vertices, no edges
    }

    @Test
    fun vertices() {
        assertEquals((0..3).toSet(), g.V)
        g.addEdge(4, 5)
        assertEquals((0..5).toSet(), g.V)
    }

    @Test
    fun degreeOf() {
        assertEquals(1, g.degreeOf(0))
        assertEquals(2, g.degreeOf(1))
        assertEquals(1, g.degreeOf(2))
        assertEquals(0, g.degreeOf(3))

        g.addEdge(1, 4)
        assertEquals(3, g.degreeOf(1))
        assertEquals(1, g.degreeOf(4))
    }

}
