import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TopKTest {

    @Test
    fun takeAll() {
        val l = (1..10).toSet()
        assertEquals(l, topKWithHeap(l, 10))
    }

    @Test
    fun kTooBig() {
        val l = (1..10).toSet()
        assertEquals(l, topKWithHeap(l, 10))
    }

    @Test
    fun takeKFrom1to100() {
        val oneToHundred = (1..100).toSet()
        for (k in 1..100) {
            val res = (100-k+1 .. 100).toSet()
            assertEquals(res, topKWithHeap(oneToHundred, k))
        }
    }

    @Test
    fun kIs0() {
        val l = (1..10).toSet()
        assertEquals(emptySet<Int>(), topKWithHeap(l, 0))
    }
}