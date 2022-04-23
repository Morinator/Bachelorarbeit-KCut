package kotlin_test.graph_lib.algorithms.exploration

import graphlib.constructors.Factory.createPath
import graphlib.exploration.checkIfConnected
import graphlib.exploration.connectedComponent
import graphlib.exploration.listConnectedComponents
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ConnectedComponentsKtTest {

    @Test
    fun connectedComponentTest() {
        val g = createPath(10)
        assertEquals((1..10).toSet(), connectedComponent(g, 1))
    }

    @Test
    fun checkIfConnectedTest() {
        val g = createPath(10)
        assertTrue { checkIfConnected(g) }
    }

    @Test
    fun listConnectedComponentsTest() {
        val g = createPath(10)
        (30..40).forEach { g.addEdge(it, it + 1) }

        val result = listConnectedComponents(g)
        assertTrue { (1..10).toSet() in result }
        assertTrue { (30..41).toSet() in result }
    }
}
