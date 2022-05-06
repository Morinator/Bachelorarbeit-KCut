package bachelorthesis.fixedmaxcut

import bachelorthesis.solvers.StackSolver
import bachelorthesis.solvers.ValueWrapper
import graphlib.constructors.GraphIO.graphFromPath
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import java.io.File
import kotlin.test.assertEquals

class CompareWithLogs {

    @RepeatedTest(130) // sadly needs to be set manually to the number of files to compare
    fun runTest(repNr: RepetitionInfo) {
        val line = File("maxcut_results_newgraphs").readLines()[repNr.currentRepetition-1]
        val l = line.split("\\s+".toRegex())
        val g = graphFromPath(l[0])
        val k = l[1].toInt()
        val objValue = l[2].toInt()

        val prediction = ValueWrapper(g, k, StackSolver()).calc().value
        assertEquals(objValue, prediction, message = "### graphName=${l[0]}, k=$k###")
    }
}
