package graphlib.algorithms.bipartite

import bachelorthesis.solvers.cutSize
import graphlib.constructors.Factory.createPath
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CutSizeKtTest {

    @Test
    fun cutSize1() {
        assertEquals(2, cutSize(createPath(4), setOf(2)))
    }
}
