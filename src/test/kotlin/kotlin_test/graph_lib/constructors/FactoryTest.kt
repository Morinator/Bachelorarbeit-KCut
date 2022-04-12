package kotlin_test.graph_lib.constructors

import graphlib.constructors.Factory.createClique
import graphlib.constructors.Factory.createCycle
import graphlib.constructors.Factory.createPath
import graphlib.constructors.Factory.createStar
import org.junit.jupiter.api.Assertions.assertEquals
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
            assertTrue { g.isConnected(1,i) }
    }

}
