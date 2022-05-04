package bachelorthesis

import bachelorthesis.solvers.StackSolver
import bachelorthesis.solvers.ValueWrapper
import graphlib.constructors.GraphIO

fun main() {
    val g = GraphIO.graphFromPath("data/graphs/soc-brightkite.mtx")
    val k = 1

    val result = ValueWrapper(g, k, StackSolver()).calc()
    println(result)
}
