package graphlib.constructors

import graphlib.constructors.Factory.createClique
import graphlib.constructors.Factory.createCycle
import graphlib.constructors.Factory.createPath
import graphlib.datastructures.SimpleGraph
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class KCoreTest {

    @Test
    fun cycle5() {
        val g = createCycle(5)
        for (k in 0..2)
            assertEquals(g, kCore(g, k))
        assertEquals(SimpleGraph<Int>(), kCore(g, 3))
    }

    @Test
    fun path7() {
        val g = createPath(7)
        for (k in 0..1)
            assertEquals(g, kCore(g, k))
        assertEquals(SimpleGraph<Int>(), kCore(g, 2))
    }

    @Test
    fun clique5() {
        val g = createClique(5)
        for (k in 0..4)
            assertEquals(g, kCore(g, k))
        assertEquals(SimpleGraph<Int>(), kCore(g, 5))
    }
}
