package bachelorthesis.fixedmaxcut

import bachelorthesis.solveBruteForce
import graphlib.constructors.Factory.createClique
import graphlib.constructors.Factory.createPath
import graphlib.constructors.FileReader.graphFromPath
import graphlib.datastructures.Solution
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class HandcraftedSolutions {

    @Test
    fun solveA() {
        val g = createPath(5)
        assertEquals(Solution(setOf(2, 4), 4), solveBruteForce(g, 2))
    }

    @Test
    fun solveB() {
        val g = createPath(3)
        assertEquals(Solution(setOf(1, 3), 2), solveBruteForce(g, 2))
    }

    @Test
    fun solveC() {
        val g = createClique(6)
        assertEquals(9, solveBruteForce(g, 3).value) // solution is not unique
    }

    @Nested
    internal inner class Small1 {

        private val g = graphFromPath("data/graphs/small1.txt")

        @Test
        fun k1() = assertEquals(Solution(setOf(3), 5), solveBruteForce(g, 1))

        @Test
        fun k2() = assertEquals(Solution(setOf(1, 3), 7), solveBruteForce(g, 2))

        @Test
        fun k3() = assertEquals(6, solveBruteForce(g, 3).value) // vertices not unique

        @Test
        fun k4() = assertEquals(5, solveBruteForce(g, 4).value)

        @Disabled
        @Test
        fun k5() = assertEquals(-123, solveBruteForce(g, 5).value)

        @Disabled
        @Test
        fun k6() = assertEquals(-123, solveBruteForce(g, 6).value)

        @Test
        fun k7() = assertEquals(5, solveBruteForce(g, 7).value)

        @Test
        fun k8() = assertEquals(Solution((1..8).toSet(), 0), solveBruteForce(g, 8))
    }
}
