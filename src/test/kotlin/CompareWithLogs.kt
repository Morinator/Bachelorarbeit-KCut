import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import solvers.*
import java.io.File
import kotlin.test.assertEquals

class CompareWithLogs {

    private val logPath = "logs_from_august_02"

    private fun testHelper(logFile: File, lineNr: Int, useHeuristic: Boolean) {
        val line = logFile.readLines()[lineNr - 1]
        val l = line.split("\\s+".toRegex())
        val G = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()

        val (_, value) = FullStackSolver(G, k, doHeuristic = useHeuristic).opt()
        assertEquals(objValue, value, message = "### graphName=${l[0]}, k=$k###")
    }

    @RepeatedTest(2326)
    fun heuristicNo(repNr: RepetitionInfo) {
        testHelper(File(logPath), repNr.currentRepetition, useHeuristic = false)
    }

    @RepeatedTest(2326)
    fun heuristicYes(repNr: RepetitionInfo) {
        testHelper(File(logPath), repNr.currentRepetition, useHeuristic = true)
    }

    @RepeatedTest(2326)
    @Disabled // takes too long by now
    fun bruteforceSolver(repNr: RepetitionInfo) {
        val line = File(logPath).readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val G = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()
        val (_, value) = BruteforceSolver.calc(G, k)
        assertEquals(objValue, value, message = "### graphName=${l[0]}, k=$k###")
    }

    @RepeatedTest(2326)
    fun checkE(repNr: RepetitionInfo) {
        val line = File(logPath).readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val G = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()
        val (_, value) = ESatNeedlessSolver(G, k, doHeuristic = true).opt()
        assertEquals(objValue, value, message = "### graphName=${l[0]}, k=$k###")
    }

    @RepeatedTest(2326)
    fun checkD(repNr: RepetitionInfo) {
        val line = File(logPath).readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val G = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()
        val (_, value) = DContSolver(G, k, doHeuristic = true).opt()
        assertEquals(objValue, value, message = "### graphName=${l[0]}, k=$k###")
    }

    @RepeatedTest(2326)
    fun checkC(repNr: RepetitionInfo) {
        val line = File(logPath).readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val G = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()
        val (_, value) = CHeuristikSolver(G, k, doHeuristic = true).opt()
        assertEquals(objValue, value, message = "### graphName=${l[0]}, k=$k###")
    }

    @RepeatedTest(2326)
    fun checkB(repNr: RepetitionInfo) {
        val line = File(logPath).readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val G = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()
        val (_, value) = BUpperBoundSolver(G, k).opt()
        assertEquals(objValue, value, message = "### graphName=${l[0]}, k=$k###")
    }

    @RepeatedTest(2326)
    fun checkA(repNr: RepetitionInfo) {
        val line = File(logPath).readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val G = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()
        val (_, value) = ATreeSolver(G, k).opt()
        assertEquals(objValue, value, message = "### graphName=${l[0]}, k=$k###")
    }


    @RepeatedTest(2326)
    fun withILP(repNr: RepetitionInfo) {
        val line = File(logPath).readLines()[repNr.currentRepetition - 1]
        val l = line.split("\\s+".toRegex())
        val g = graphFromPath("data/graphs/${l[0]}")
        val k = l[1].toInt()
        val objValue = l[2].toInt()
        val prediction = ILPSolver(g, k).calc().second
        assertEquals(objValue, prediction, message = "### graphName=${l[0]}, k=$k###")
    }

}
