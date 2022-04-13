package kotlin_test.graph_lib.algorithms.exploration

import graphlib.algorithms.exploration.BFS_Level_Iterator
import graphlib.constructors.Factory.createPath
import graphlib.datastructures.SimpleGraph
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class BFS_Level_IteratorTest {

    @Test
    fun test1() {
        val g = createPath(5).apply {
            addEdge(3, 6) //          1  -  2  -  3  -  4  -  5
            addEdge(6, 7) //                      |                       Graph looks like this
            addEdge(7, 8) //                      6  -  7  -  8
        }

        val levels = BFS_Level_Iterator(g, 1).asSequence().toList()
        val trueLevels = listOf(
            listOf(1),
            listOf(2),
            listOf(3),
            listOf(4, 6),
            listOf(5, 7),
            listOf(8)
        )
        assertEquals(trueLevels, levels)
    }

    @Test
    fun test2() {
        val g = SimpleGraph<Int>().apply {
            addEdge(1, 2)
            addEdge(3, 4) //     1  -  2         3  -  4
        }

        assertEquals( // make sure it doesn't reach the other connected component
            listOf(listOf(1), listOf(2)),
            BFS_Level_Iterator(g, 1).asSequence().toList()
        )
    }
}
