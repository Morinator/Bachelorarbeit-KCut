package util.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CounterTest {

    @Test
    fun f1() {
        val s = listOf('a', 'b', 'x')
        val ctr = Counter(s)

        s.forEach { assertEquals(0, ctr[it]) }

        ctr.inc('b')
        assertEquals(1, ctr['b'])

        ctr.inc('b')
        assertEquals(2, ctr['b'])
    }
}
