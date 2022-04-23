package graphlib

import graphlib.constructors.Factory.createCycle
import graphlib.constructors.Factory.createPath
import graphlib.constructors.Factory.createStar
import graphlib.datastructures.SimpleGraph
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class IsomorphismCheckTest {

    @Test
    fun twoIsolatedVertices_vs_p2() {
        assertFalse(
            checkIfIsomorphic(
                SimpleGraph<Int>().addVertices(setOf(1, 2)),
                createPath(2)
            )
        )
    }

    @Test
    fun cycle3_vs_path3() {
        assertFalse(checkIfIsomorphic(createCycle(3), createPath(3)))
    }

    @Test
    fun circle6_vs_two_triangles() {
        val twoTriangles = createCycle(3).addEdge(4, 5).addEdge(4, 6).addEdge(5, 6)
        assertFalse(checkIfIsomorphic(createCycle(6), twoTriangles))
    }

    @Test
    fun star8_both_times() {
        assertTrue(checkIfIsomorphic(createStar(8), createStar(8)))
    }
}
