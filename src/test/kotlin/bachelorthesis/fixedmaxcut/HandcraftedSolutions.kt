package bachelorthesis.fixedmaxcut

import bachelorthesis.StackSolver
import bachelorthesis.cut
import graphlib.Factory.createPath
import graphlib.GraphIO.graphFromPath
import org.jgrapht.generate.CompleteGraphGenerator
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.jgrapht.util.SupplierUtil.DEFAULT_EDGE_SUPPLIER
import org.jgrapht.util.SupplierUtil.createIntegerSupplier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class HandcraftedSolutions {

    @Test
    fun solveA() {
        val g = createPath(5)

        val k = 2
        val S = StackSolver.calc(g, k, false)
        assertEquals(mutableSetOf(2, 4), S )
        assertEquals(4, cut(g,S))
    }

    @Test
    fun solveB() {
        val g = createPath(3)
        val S = StackSolver.calc(g, 2, false)
        assertEquals(mutableSetOf(1, 3), S)
        assertEquals(2, cut(g,S))
    }

    @Test
    fun solveC() {
        val g = SimpleGraph(createIntegerSupplier(), DEFAULT_EDGE_SUPPLIER, false)
        CompleteGraphGenerator<Int, DefaultEdge>(6).generateGraph(g)

        assertEquals(9, cut(g,StackSolver.calc(g, 3, false))) // solution is not unique
    }

    @Nested
    internal inner class Small1 {

        private val g = graphFromPath("data/graphs/small1.txt")

        @Test
        fun k1() {
            val S = StackSolver.calc(g, 1, false)
            assertEquals(mutableSetOf(3) , S)
            assertEquals(5, cut(g,S))
        }

        @Test
        fun k2() {
            val S = StackSolver.calc(g, 2, false)
            assertEquals(mutableSetOf(1, 3), S)
            assertEquals(7, cut(g,S))
        }

        @Test
        fun k3() = assertEquals(6, cut(g,StackSolver.calc(g, 3, false))) // vertices not unique

        @Test
        fun k4() = assertEquals(5, cut(g,StackSolver.calc(g, 4, false)))

        @Disabled
        @Test
        fun k5() = assertEquals(-123, cut(g,StackSolver.calc(g, 5, false)))

        @Disabled
        @Test
        fun k6() = assertEquals(-123, cut(g,StackSolver.calc(g, 6, false)))

        @Test
        fun k7() = assertEquals(5, cut(g,StackSolver.calc(g, 7, false)))

        @Test
        fun k8() {
            val S = StackSolver.calc(g, 8, false)
            assertEquals((1..8).toMutableSet(), S)
            assertEquals(0, cut(g,S))
        }
    }
}
