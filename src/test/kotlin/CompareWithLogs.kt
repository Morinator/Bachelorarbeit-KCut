import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import java.io.File
import kotlin.test.assertEquals

class CompareWithLogs {

    private val logPath = "logs_from_july_31"

    private fun testHelper(logFile: File, lineNr: Int, useHeuristic: Boolean) {
        val line = logFile.readLines()[lineNr - 1]
        val l = line.split("\\s+".toRegex())
        val G = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()

        val (_, value) = StackSolver(G, k, doHeuristic = useHeuristic).opt()
        assertEquals(objValue, value, message = "### graphName=${l[0]}, k=$k###")
    }

    @RepeatedTest(3_800)
    fun heuristicNo(repNr: RepetitionInfo) {
        testHelper(File(logPath), repNr.currentRepetition, useHeuristic = false)
    }

    @RepeatedTest(3_800)
    fun heuristicYes(repNr: RepetitionInfo) {
        testHelper(File(logPath), repNr.currentRepetition, useHeuristic = true)
    }

    @RepeatedTest(3_800)
    fun bruteforceSolver(repNr: RepetitionInfo) {
        val line = File(logPath).readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val G = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()
        val (_, value) = BruteforceSolver.calc(G, k)
        assertEquals(objValue, value, message = "### graphName=${l[0]}, k=$k###")
    }

    /*
    @RepeatedTest(3_800)
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
