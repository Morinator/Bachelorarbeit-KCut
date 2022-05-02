package bachelorthesis.fixedmaxcut

import bachelorthesis.solvers.StackSolver
import bachelorthesis.solvers.ValueWrapper
import graphlib.constructors.GraphIO.graphFromPath
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class CompareWithLogs {

    @Test // Takes 23 minutes for all instances as of 27.04.2022
    fun runTest() {
        val nrOfInstances = 17
        for (line in File("maxcut_results").readLines().take(nrOfInstances)) {
            val l = line.split("\\s+".toRegex())
            val g = graphFromPath(l[0])
            val k = l[1].toInt()
            val objValue = l[2].toInt()

            val prediction = ValueWrapper(g, k, StackSolver()).calc().value
            assertEquals(objValue, prediction, message = "### graphName=${l[0]}, k=$k ###")
        }
    }
}
