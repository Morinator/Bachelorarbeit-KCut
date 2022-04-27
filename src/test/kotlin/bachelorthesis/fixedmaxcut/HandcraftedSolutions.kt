package bachelorthesis.fixedmaxcut

import bachelorthesis.solvers.BruteforceSolver
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
        assertEquals(Solution(mutableSetOf(2, 4), 4), BruteforceSolver().run(g, 2))
    }

    @Test
    fun solveB() {
        val g = createPath(3)
        assertEquals(Solution(mutableSetOf(1, 3), 2), BruteforceSolver().run(g, 2))
    }

    @Test
    fun solveC() {
        val g = createClique(6)
        assertEquals(9, BruteforceSolver().run(g, 3).value) // solution is not unique
    }

    @Nested
    internal inner class Small1 {

        private val g = graphFromPath("data/graphs/small1.txt")

        @Test
        fun k1() = assertEquals(Solution(mutableSetOf(3), 5), BruteforceSolver().run(g, 1))

        @Test
        fun k2() = assertEquals(Solution(mutableSetOf(1, 3), 7), BruteforceSolver().run(g, 2))

        @Test
        fun k3() = assertEquals(6, BruteforceSolver().run(g, 3).value) // vertices not unique

        @Test
        fun k4() = assertEquals(5, BruteforceSolver().run(g, 4).value)

        @Disabled
        @Test
        fun k5() = assertEquals(-123, BruteforceSolver().run(g, 5).value)

        @Disabled
        @Test
        fun k6() = assertEquals(-123, BruteforceSolver().run(g, 6).value)

        @Test
        fun k7() = assertEquals(5, BruteforceSolver().run(g, 7).value)

        @Test
        fun k8() = assertEquals(Solution((1..8).toMutableSet(), 0), BruteforceSolver().run(g, 8))
    }
}
