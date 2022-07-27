import org.jgrapht.Graph
import org.jgrapht.generate.StarGraphGenerator
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.jgrapht.util.SupplierUtil
import org.jgrapht.util.SupplierUtil.createIntegerSupplier


fun main() {
    val g: Graph<Int, DefaultEdge> = SimpleGraph(createIntegerSupplier(), SupplierUtil.DEFAULT_EDGE_SUPPLIER, false)
    StarGraphGenerator<Int, DefaultEdge>(10).generateGraph(g)

    println(g.edgeSet().size)
}