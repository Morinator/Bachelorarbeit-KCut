package kotlin_test.graph_lib.datastructures

import graphlib.Factory.createClique
import graphlib.Factory.createCycle
import graphlib.Factory.createPath
import graphlib.Factory.createStar
import graphlib.SimpleGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class SimpleGraphTest {

    private val g = SimpleGraph<Int>() // looks like:       0-1-2  3

    @BeforeEach
    fun setUp() {
        g.addEdge(0, 1)
        g.addEdge(1, 2)
        g.addVertex(3)
    }

    @Test
    fun addVertex() {
        assertFalse { 4 in g }
        g.addVertex(4)
        assertTrue { 4 in g }
        g.addVertex(4)
        assertTrue { 4 in g }
    }

    @Test
    fun addVertices() {
        assertFalse { 4 in g }
        g.addVertices(listOf(5, 6))
        assertTrue { 5 in g && 6 in g }
        assertEquals(6, g.size)
        assertEquals(2, g.edgeCount)
    }

    @Test
    fun addEdge1() {
        assertFalse { g.areNB(1, 3) }
        g.addEdge(1, 3)
        assertTrue { g.areNB(1, 3) }
    }

    @Test
    fun addEdge2() {
        val g2 = SimpleGraph<Int>() // is empty
        g2.addEdge(1, 1)
        assertEquals(0, g2.size)
    }

    @Test
    fun get() {
        assertEquals(setOf(0, 2), g[1])
        g.addEdge(1, 3)
        assertEquals(setOf(0, 2, 3), g[1])
    }

    @Test
    fun contains() {
        for (i in 0..3) assertTrue { i in g }
    }

    @Test
    fun deleteVertex() {
        assertTrue { 2 in g }
        g.deleteVertex(2)
        assertFalse { 2 in g }
        assertEquals(setOf(0), g[1]) // it's not a neighbour of vertex 1 anymore
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
        assertTrue { 0 in g && 1 in g }
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

    @Test
    fun copy() {
        val g1 = g.copy()

        for (v in g1.V) assertEquals(g1[v], g[v]) // make sure content is equal

        g1.deleteVertex(2) // check if old graph is unaffected
        assertFalse { 2 in g1 }
        assertTrue { 2 in g }
    }

    @Test
    fun equalsTest() {
        assertEquals(createPath(100), createPath(100))
        assertTrue { createPath(42) == createPath(42) } // check if "==" Operator works
    }

    @Test
    fun hashCodeTest() {
        for (i in 1..20) {
            assertEquals(createPath(i).hashCode(), createPath(i).hashCode())
            assertEquals(createCycle(i).hashCode(), createCycle(i).hashCode())
        }
    }

    @Test
    fun maxDegreeTest() {
        assertEquals(2, createPath(4).maxDegree)
        assertEquals(1, createPath(2).maxDegree)
        assertEquals(2, createCycle(5).maxDegree)
        assertEquals(5, createClique(6).maxDegree)
    }

    @Test
    fun neighboursA() {
        assertEquals(setOf(3, 4), createClique(5)[setOf(1, 2, 5)])
        assertEquals(setOf(4, 8), createPath(10)[setOf(5, 6, 7)])
        assertEquals(setOf(1, 3, 4, 6), createPath(10)[setOf(2, 5)])
        assertEquals(setOf(4, 8), createCycle(10)[setOf(5, 6, 7)])
        assertEquals(setOf(2, 10, 5, 7), createCycle(10)[setOf(1, 6)])
        assertEquals(setOf(3, 5), createStar(5)[setOf(1, 2, 4)])
        assertEquals(setOf(1), createStar(5)[setOf(2, 3, 4, 5)])
    }
}
