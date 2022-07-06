/*
package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import ilog.cplex.IloCplex
import util.mapGraphToRangeIndexedFromZero
import kotlin.math.roundToInt

class ILPSolver(
    private val g: SimpleGraph<Int>,
    private val k: Int,
) {

    fun calc(): Solution<Int> {

        val gNew = mapGraphToRangeIndexedFromZero(g)

        val vertexIDs = gNew.V
        val edgeIDs: List<Pair<Int, Int>> = gNew.edgeList()

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

        return Solution(emptyList(), cplex.bestObjValue.roundToInt())
    }
}

*/
