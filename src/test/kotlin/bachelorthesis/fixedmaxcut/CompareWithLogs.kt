package bachelorthesis.fixedmaxcut

import bachelorthesis.BruteforceSolver
import bachelorthesis.StackSolver
import bachelorthesis.cut
import graphlib.GraphIO.graphFromPath
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import java.io.File
import kotlin.test.assertEquals

class CompareWithLogs {

    private fun testHelper(logFile: File, lineNr: Int, useHeuristic: Boolean) {
        val line = logFile.readLines()[lineNr - 1]
        val l = line.split("\\s+".toRegex())
        val G = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()

        val S = StackSolver.calc(G, k, useHeuristic = useHeuristic)
        val prediction = cut(G,S)
        assertEquals(objValue, prediction, message = "### graphName=${l[0]}, k=$k###")
    }

    private val logPath = "maxcut_results_with_paths_laplace2"

    @RepeatedTest(3300)
    fun heuristicNo(repNr: RepetitionInfo) {
        testHelper(File(logPath), repNr.currentRepetition, useHeuristic = false)
    }

    @RepeatedTest(3300)
    fun heuristicYes(repNr: RepetitionInfo) {
        testHelper(File(logPath), repNr.currentRepetition, useHeuristic = true)
    }

    @RepeatedTest(3300)
    @Disabled
    fun bruteforceSolver(repNr: RepetitionInfo) {
        val line = File(logPath).readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val G = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()
        val S = BruteforceSolver.calc(G,k)
        val prediction = cut(G, S)
        assertEquals(objValue, prediction, message = "### graphName=${l[0]}, k=$k###")
    }

    /*
    @RepeatedTest(3000)
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
