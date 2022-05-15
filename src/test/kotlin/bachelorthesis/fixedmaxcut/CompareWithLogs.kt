package bachelorthesis.fixedmaxcut

import bachelorthesis.solvers.StackSolver
import graphlib.constructors.GraphIO.graphFromPath
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import java.io.File
import kotlin.test.assertEquals

class CompareWithLogs {

    @RepeatedTest(290) // sadly needs to be set manually to the number of files to compare
    fun allWithHeuristic(repNr: RepetitionInfo) {
        val line = File("maxcut_results_lahnwiese2").readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val g = graphFromPath(l[0])
        val k = l[1].toInt()
        val objValue = l[2].toInt()

        val prediction = StackSolver(g, k, useHeuristic = true).calc().value
        assertEquals(objValue, prediction, message = "### graphName=${l[0]}, k=$k###")
    }

    @RepeatedTest(290) // sadly needs to be set manually to the number of files to compare
    fun someWithoutHeuristic(repNr: RepetitionInfo) {
        val line = File("maxcut_results_lahnwiese2").readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val g = graphFromPath(l[0])
        val k = l[1].toInt()
        val objValue = l[2].toInt()

        val prediction = StackSolver(g, k).calc().value
        assertEquals(objValue, prediction, message = "### graphName=${l[0]}, k=$k###")
    }
}
