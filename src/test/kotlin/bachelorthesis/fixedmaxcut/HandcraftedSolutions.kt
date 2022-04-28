package bachelorthesis.fixedmaxcut

import bachelorthesis.solvers.ValueSolver
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
        assertEquals(Solution(mutableSetOf(2, 4), 4), ValueSolver(g, 2).calcResult())
    }

    @Test
    fun solveB() {
        val g = createPath(3)
        assertEquals(Solution(mutableSetOf(1, 3), 2), ValueSolver(g, 2).calcResult())
    }

    @Test
    fun solveC() {
        val g = createClique(6)
        assertEquals(9, ValueSolver(g, 3).calcResult().value) // solution is not unique
    }

    @Nested
    internal inner class Small1 {

        private val g = graphFromPath("data/graphs/small1.txt")

        @Test
        fun k1() = assertEquals(Solution(mutableSetOf(3), 5), ValueSolver(g, 1).calcResult())

        @Test
        fun k2() = assertEquals(Solution(mutableSetOf(1, 3), 7), ValueSolver(g, 2).calcResult())

        @Test
        fun k3() = assertEquals(6, ValueSolver(g, 3).calcResult().value) // vertices not unique

        @Test
        fun k4() = assertEquals(5, ValueSolver(g, 4).calcResult().value)

        @Disabled
        @Test
        fun k5() = assertEquals(-123, ValueSolver(g, 5).calcResult().value)

        @Disabled
        @Test
        fun k6() = assertEquals(-123, ValueSolver(g, 6).calcResult().value)

        @Test
        fun k7() = assertEquals(5, ValueSolver(g, 7).calcResult().value)

        @Test
        fun k8() = assertEquals(Solution((1..8).toMutableSet(), 0), ValueSolver(g, 8).calcResult())
    }
}
