package bachelorthesis.fixedmaxcut

import bachelorthesis.solvers.StackSolver
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

        val k = 2
        assertEquals(Solution(mutableSetOf(2, 4), 4), StackSolver(g, k, false).calc())
    }

    @Test
    fun solveB() {
        val g = createPath(3)
        assertEquals(Solution(mutableSetOf(1, 3), 2), StackSolver(g, 2, false).calc())
    }

    @Test
    fun solveC() {
        val g = createClique(6)
        assertEquals(9, StackSolver(g, 3, false).calc().value) // solution is not unique
    }

    @Nested
    internal inner class Small1 {

        private val g = graphFromPath("data/graphs/small1.txt")

        @Test
        fun k1() = assertEquals(Solution(mutableSetOf(3), 5), StackSolver(g, 1, false).calc())

        @Test
        fun k2() = assertEquals(Solution(mutableSetOf(1, 3), 7), StackSolver(g, 2, false).calc())

        @Test
        fun k3() = assertEquals(6, StackSolver(g, 3, false).calc().value) // vertices not unique

        @Test
        fun k4() = assertEquals(5, StackSolver(g, 4, false).calc().value)

        @Disabled
        @Test
        fun k5() = assertEquals(-123, StackSolver(g, 5, false).calc().value)

        @Disabled
        @Test
        fun k6() = assertEquals(-123, StackSolver(g, 6, false).calc().value)

        @Test
        fun k7() = assertEquals(5, StackSolver(g, 7, false).calc().value)

        @Test
        fun k8() = assertEquals(Solution((1..8).toMutableSet(), 0), StackSolver(g, 8, false).calc())
    }
}
