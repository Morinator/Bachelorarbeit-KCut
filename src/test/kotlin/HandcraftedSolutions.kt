import org.jgrapht.generate.CompleteGraphGenerator
import org.jgrapht.generate.LinearGraphGenerator
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
    fun optA() {
        val G = SimpleGraph(createIntegerSupplier(), DEFAULT_EDGE_SUPPLIER, false)
        LinearGraphGenerator<Int, DefaultEdge>(5).generateGraph(G) // 0 - 1 - 2 - 3 - 4

        val k = 2
        val S = StackSolver(G, k, false).opt()
        assertEquals(mutableSetOf(1, 3), S)
        assertEquals(4, cut(G, S))
    }

    @Test
    fun optB() {
        val G = SimpleGraph(createIntegerSupplier(), DEFAULT_EDGE_SUPPLIER, false)
        LinearGraphGenerator<Int, DefaultEdge>(3).generateGraph(G) // 0 - 1 - 2

        val S = StackSolver(G, 2, false).opt()
        assertEquals(mutableSetOf(0, 2), S)
        assertEquals(2, cut(G, S))
    }

    @Test
    fun optC() {
        val G = SimpleGraph(createIntegerSupplier(), DEFAULT_EDGE_SUPPLIER, false)
        CompleteGraphGenerator<Int, DefaultEdge>(6).generateGraph(G)

        assertEquals(9, cut(G, StackSolver(G, 3, false).opt())) // solution is not unique
    }

    @Nested
    internal inner class Small1 {

        private val G = graphFromPath("data/graphs/small1.txt")

        @Test
        fun k1() {
            val S = StackSolver(G, 1, false).opt()
            assertEquals(mutableSetOf(3), S)
            assertEquals(5, cut(G, S))
        }

        @Test
        fun k2() {
            val S = StackSolver(G, 2, false).opt()
            assertEquals(mutableSetOf(1, 3), S)
            assertEquals(7, cut(G, S))
        }

        @Test
        fun k3() = assertEquals(6, cut(G, StackSolver(G, 3, false).opt())) // vertices not unique

        @Test
        fun k4() = assertEquals(5, cut(G, StackSolver(G, 4, false).opt()))

        @Disabled
        @Test
        fun k5() = assertEquals(-123, cut(G, StackSolver(G, 5, false).opt()))

        @Disabled
        @Test
        fun k6() = assertEquals(-123, cut(G, StackSolver(G, 6, false).opt()))

        @Test
        fun k7() = assertEquals(5, cut(G, StackSolver(G, 7, false).opt()))

        @Test
        fun k8() {
            val S = StackSolver(G, 8, false).opt()
            assertEquals((1..8).toMutableSet(), S)
            assertEquals(0, cut(G, S))
        }
    }

    @Test
    fun cutA() {
        val G = SimpleGraph(createIntegerSupplier(), DEFAULT_EDGE_SUPPLIER, false)
        LinearGraphGenerator<Int, DefaultEdge>(4).generateGraph(G)

        assertEquals(2, cut(G, setOf(2)))
    }
}
