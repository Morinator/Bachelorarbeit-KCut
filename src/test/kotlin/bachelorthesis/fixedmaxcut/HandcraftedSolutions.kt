package bachelorthesis.fixedmaxcut

import bachelorthesis.solvers.ValueWrapper
import graphlib.constructors.Factory.createClique
import graphlib.constructors.Factory.createPath
import graphlib.constructors.GraphIO.graphFromPath
import graphlib.datastructures.Solution
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class HandcraftedSolutions {

    @Test
    fun solveA() {
        val g = createPath(5)
        assertEquals(Solution(mutableSetOf(2, 4), 4), ValueWrapper(g, 2).calc())
    }

    @Test
    fun solveB() {
        val g = createPath(3)
        assertEquals(Solution(mutableSetOf(1, 3), 2), ValueWrapper(g, 2).calc())
    }

    @Test
    fun solveC() {
        val g = createClique(6)
        assertEquals(9, ValueWrapper(g, 3).calc().value) // solution is not unique
    }

    @Nested
    internal inner class Small1 {

        private val g = graphFromPath("data/graphs/small1.txt")

        @Test
        fun k1() = assertEquals(Solution(mutableSetOf(3), 5), ValueWrapper(g, 1).calc())

        @Test
        fun k2() = assertEquals(Solution(mutableSetOf(1, 3), 7), ValueWrapper(g, 2).calc())

        @Test
        fun k3() = assertEquals(6, ValueWrapper(g, 3).calc().value) // vertices not unique

        @Test
        fun k4() = assertEquals(5, ValueWrapper(g, 4).calc().value)

        @Disabled
        @Test
        fun k5() = assertEquals(-123, ValueWrapper(g, 5).calc().value)

        @Disabled
        @Test
        fun k6() = assertEquals(-123, ValueWrapper(g, 6).calc().value)

        @Test
        fun k7() = assertEquals(5, ValueWrapper(g, 7).calc().value)

        @Test
        fun k8() = assertEquals(Solution((1..8).toMutableSet(), 0), ValueWrapper(g, 8).calc())
    }
}
