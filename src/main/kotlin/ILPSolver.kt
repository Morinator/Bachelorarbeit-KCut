
import ilog.cplex.IloCplex
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import kotlin.math.roundToInt

class ILPSolver(
    private val g: SimpleGraph<Int, DefaultEdge>,
    private val k: Int,
) {

    fun calc(): Pair<Set<Int>, Int> {

        val newIDs = HashMap<Int, Int>()
        var ctr = 0
        for (v in g.vertexSet()) {
            newIDs[v] = ctr++
        }

        val vertexIDs = newIDs.values
        val edgeIDs: List<Pair<Int, Int>> = g.edgeSet().map {newIDs[g.getEdgeSource(it)]!! to newIDs[g.getEdgeTarget(it)]!! }

        val cplex = IloCplex()
        cplex.setParam(IloCplex.Param.Threads, 1)

        val vertices = cplex.boolVarArray(vertexIDs.size)
        val edges = cplex.boolVarArray(edgeIDs.size)

        for (i in edgeIDs.indices) {

            cplex.addLe(edges[i],
                cplex.sum(
                    vertices[edgeIDs[i].first],
                    vertices[edgeIDs[i].second]))

            cplex.addLe(edges[i], cplex.sum(2,
                cplex.negative(cplex.sum(
                    vertices[edgeIDs[i].first],
                    vertices[edgeIDs[i].second]))))
        }

        cplex.addEq(cplex.sum(vertices), k.toDouble())

        val intExpr = cplex.linearIntExpr()
        for (i in edgeIDs.indices)
            intExpr.addTerm(1, edges[i])

        cplex.addMaximize(intExpr)

        // solve
        cplex.solve()

        return Pair(emptySet(), cplex.bestObjValue.roundToInt())
    }
}