package bachelorthesis.fixedmaxcut

import bachelorthesis.StackSolver
import bachelorthesis.cutSize
import graphlib.Factory.createClique
import graphlib.Factory.createPath
import graphlib.GraphIO.graphFromPath
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class HandcraftedSolutions {

    @Test
    fun solveA() {
        val g = createPath(5)

        val k = 2
        val S = StackSolver(g, k, false).calc()
        assertEquals(mutableSetOf(2, 4), S )
        assertEquals(4, cutSize(g,S))
    }

    @Test
    fun solveB() {
        val g = createPath(3)
        val S = StackSolver(g, 2, false).calc()
        assertEquals(mutableSetOf(1, 3), S)
        assertEquals(2, cutSize(g,S))
    }

    @Test
    fun solveC() {
        val g = createClique(6)
        assertEquals(9, cutSize(g,StackSolver(g, 3, false).calc())) // solution is not unique
    }

    @Nested
    internal inner class Small1 {

        private val g = graphFromPath("data/graphs/small1.txt")

        @Test
        fun k1() {
            val S = StackSolver(g, 1, false).calc()
            assertEquals(mutableSetOf(3) , S)
            assertEquals(5, cutSize(g,S))
        }

        @Test
        fun k2() {
            val S = StackSolver(g, 2, false).calc()
            assertEquals(mutableSetOf(1, 3), S)
            assertEquals(7, cutSize(g,S))
        }

        @Test
        fun k3() = assertEquals(6, cutSize(g,StackSolver(g, 3, false).calc())) // vertices not unique

        @Test
        fun k4() = assertEquals(5, cutSize(g,StackSolver(g, 4, false).calc()))

        @Disabled
        @Test
        fun k5() = assertEquals(-123, cutSize(g,StackSolver(g, 5, false).calc()))

        @Disabled
        @Test
        fun k6() = assertEquals(-123, cutSize(g,StackSolver(g, 6, false).calc()))

        @Test
        fun k7() = assertEquals(5, cutSize(g,StackSolver(g, 7, false).calc()))

        @Test
        fun k8() {
            val S = StackSolver(g, 8, false).calc()
            assertEquals((1..8).toMutableSet(), S)
            assertEquals(0, cutSize(g,S))
        }
    }
}
