package graphlib.algorithms.bipartite

import graphlib.constructors.Factory.createPath
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CutSizeKtTest {

    @Test
    fun cutSize1() {
        assertEquals(2, cutSize(createPath(4), setOf(2)))
    }

}