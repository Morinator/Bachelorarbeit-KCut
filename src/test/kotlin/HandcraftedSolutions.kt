import org.jgrapht.generate.LinearGraphGenerator
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.jgrapht.util.SupplierUtil.DEFAULT_EDGE_SUPPLIER
import org.jgrapht.util.SupplierUtil.createIntegerSupplier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import solvers.FullStackSolver

internal class HandcraftedSolutions {

    @Test
    fun optA() {
        val G = SimpleGraph(createIntegerSupplier(), DEFAULT_EDGE_SUPPLIER, false)
        LinearGraphGenerator<Int, DefaultEdge>(5).generateGraph(G) // 0 - 1 - 2 - 3 - 4

        val k = 2
        val (S, value) = FullStackSolver(G, k, false).opt()
        assertEquals(mutableSetOf(1, 3), S)
        assertEquals(4, value)
    }

    @Test
    fun optB() {
        val G = SimpleGraph(createIntegerSupplier(), DEFAULT_EDGE_SUPPLIER, false)
        LinearGraphGenerator<Int, DefaultEdge>(3).generateGraph(G) // 0 - 1 - 2

        val (S, value) = FullStackSolver(G, 2, false).opt()
        assertEquals(mutableSetOf(0, 2), S)
        assertEquals(2, value)
    }

    @Nested
    internal inner class Small1 {

        private val G = graphFromPath("data/graphs/small1.txt")

        @Test
        fun k1() {
            val (S, value) = FullStackSolver(G, 1, false).opt()
            assertEquals(mutableSetOf(3), S)
            assertEquals(5, value)
        }

        @Test
        fun k2() {
            val (S, value) = FullStackSolver(G, 2, false).opt()
            assertEquals(mutableSetOf(1, 3), S)
            assertEquals(7, value)
        }

        @Test
        fun k3() = assertEquals(6, FullStackSolver(G, 3, false).opt().second)

        @Test
        fun k4() = assertEquals(5, FullStackSolver(G, 4, false).opt().second)

        @Test
        fun k7() = assertEquals(5, FullStackSolver(G, 7, false).opt().second)

        @Test
        fun k8() {
            val (S, value) = FullStackSolver(G, 8, false).opt()
            assertEquals((1..8).toMutableSet(), S)
            assertEquals(0, value)
        }
    }

    @Test
    fun cutA() {
        val G = SimpleGraph(createIntegerSupplier(), DEFAULT_EDGE_SUPPLIER, false)
        LinearGraphGenerator<Int, DefaultEdge>(4).generateGraph(G)

        assertEquals(2, cut(G, setOf(2)))
    }
}
