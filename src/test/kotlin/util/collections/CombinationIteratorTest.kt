package util.collections

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class CombinationIteratorTest {

    @Test
    fun test1() {
        val combinations = CombinationIterator(listOf(1, 2, 3), 1).asSequence().toList()
        val truth = listOf(
            setOf(1),
            setOf(2),
            setOf(3),
        )
        assertEquals(truth, combinations)
    }

    @Test
    fun test2() {
        val combinations = CombinationIterator(listOf(1, 2, 3, 4), 2).asSequence().toList()
        val truth = listOf(
            setOf(1, 2),
            setOf(1, 3),
            setOf(1, 4),
            setOf(2, 3),
            setOf(2, 4),
            setOf(3, 4),
        )
        assertEquals(truth, combinations)
    }

    @Test
    fun test3() {
        val combinations = CombinationIterator(listOf('a', 'b', 'c'), 3).asSequence().toList()
        val truth = listOf(
            setOf('a', 'b', 'c'),
        )
        assertEquals(truth, combinations)
    }

    @Test
    fun test4() {
        val combinations = CombinationIterator(listOf(1, 2, 3, 4), 3).asSequence().toList()
        val truth = listOf(
            setOf(1, 2, 3),
            setOf(1, 2, 4),
            setOf(1, 3, 4),
            setOf(2, 3, 4),
        )
        assertEquals(truth, combinations)
    }
}
