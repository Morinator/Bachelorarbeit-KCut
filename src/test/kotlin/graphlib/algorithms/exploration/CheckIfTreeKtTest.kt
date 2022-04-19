package graphlib.algorithms.exploration

import graphlib.constructors.Factory.createBipartite
import graphlib.constructors.Factory.createClique
import graphlib.constructors.Factory.createCycle
import graphlib.constructors.Factory.createPath
import graphlib.constructors.Factory.createStar
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CheckIfTreeKtTest {

    @Test
    fun basicShapes1() {
        assertTrue(checkIfTree(createStar(10)))
        assertTrue(checkIfTree(createPath(10)))
        assertFalse(checkIfTree(createCycle(10)))
        assertFalse(checkIfTree(createClique(10)))
        assertFalse(checkIfTree(createBipartite(10, 20)))
    }
}