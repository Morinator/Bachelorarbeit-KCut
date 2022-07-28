import org.jgrapht.generate.CompleteBipartiteGraphGenerator
import org.jgrapht.generate.StarGraphGenerator
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.jgrapht.util.SupplierUtil.DEFAULT_EDGE_SUPPLIER
import org.jgrapht.util.SupplierUtil.createIntegerSupplier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LocalSearchTest {

    @Test
    fun localSearchStep() {
        val G = SimpleGraph(createIntegerSupplier(), DEFAULT_EDGE_SUPPLIER, false)
        StarGraphGenerator<Int, DefaultEdge>(10).generateGraph(G)

        val S = mutableSetOf(3)
        _localSearchStep(G, S, ::cut)

        assertEquals(setOf(0), S)
        assertEquals(9, cut(G, S))
    }

    @Test
    fun getHeuristic() {
        val G = SimpleGraph(createIntegerSupplier(), DEFAULT_EDGE_SUPPLIER, false)
        CompleteBipartiteGraphGenerator<Int, DefaultEdge>(2, 10).generateGraph(G)

        val S = localSearchRun(G, 2)
        assertEquals(mutableSetOf(0, 1), S)
        assertEquals(20, cut(G, S))
    }
}
