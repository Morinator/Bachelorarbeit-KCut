package bachelorthesis.fixedmaxcut

import bachelorthesis.solvers.BruteforceSolver
import bachelorthesis.solvers.StackSolver
import bachelorthesis.solvers.cutSize
import graphlib.constructors.GraphIO.graphFromPath
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import java.io.File
import kotlin.test.assertEquals

class CompareWithLogs {

    private fun testHelper(logFile: File, lineNr: Int, useHeuristic: Boolean) {
        val line = logFile.readLines()[lineNr - 1]
        val l = line.split("\\s+".toRegex())
        val g = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()

        val prediction = StackSolver(g, k, useHeuristic = useHeuristic).calc().value
        assertEquals(objValue, prediction, message = "### graphName=${l[0]}, k=$k###")
    }

    private val logPath = "maxcut_results_with_paths_laplace2"

    @RepeatedTest(4306)
    fun heuristicNo(repNr: RepetitionInfo) {
        testHelper(File(logPath), repNr.currentRepetition, useHeuristic = false)
    }

    @RepeatedTest(4306)
    fun heuristicYes(repNr: RepetitionInfo) {
        testHelper(File(logPath), repNr.currentRepetition, useHeuristic = true)
    }

    @RepeatedTest(4306)
    @Disabled
    fun bruteforceSolver(repNr: RepetitionInfo) {
        val line = File(logPath).readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val G = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()
        val S = BruteforceSolver(G, k).calc()
        val prediction = cutSize(G, S)
        assertEquals(objValue, prediction, message = "### graphName=${l[0]}, k=$k###")
    }


    /*
    @RepeatedTest(4306)
    fun withILP(repNr: RepetitionInfo) {
        val line = File(logPath).readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val g = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()
        val prediction = ILPSolver(g, k).calc().value
        assertEquals(objValue, prediction, message = "### graphName=${l[0]}, k=$k###")
    }
     */
}
