import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TopKTest {

    @Test
    fun takeAll() {
        val l = (1..10).toSet()
        assertEquals(l, topKWithPivot(l, 10))
    }

    @Test
    fun kTooBig() {
        val l = (1..10).toList()
        assertThrows<IllegalArgumentException> { topKWithPivot(l, 11) }
    }

    @Test
    fun takeKFrom1to100() {
        val oneToHundred = (1..100).toList()
        for (k in 1..100) {
            val res = (100-k+1 .. 100).toSet()
            assertEquals(res, topKWithPivot(oneToHundred, k))
        }
    }

    @Test
    fun abcde() {
        val abcde = listOf('a', 'b', 'c', 'd', 'e')
        assertEquals(setOf('c', 'd', 'e'), topKWithPivot(abcde, 3))
    }

    @Test
    fun kIs0() {
        val l = (1..10).toSet()
        assertEquals(emptySet<Int>(), topKWithPivot(l, 0))
    }
}