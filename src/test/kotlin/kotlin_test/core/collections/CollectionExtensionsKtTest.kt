package kotlin_test.core.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.collections.sortedDesc

internal class CollectionExtensionsKtTest {
    @Test
    fun sortedDesc() {
        val s1 = setOf("aaaaaaaaaaaaa", "ho", "moritz", "i")
        assertEquals(listOf("aaaaaaaaaaaaa", "moritz", "ho", "i"), s1.sortedDesc { it.length })
    }

    @Test
    fun intersectionSize() {
        assertEquals(2, util.collections.intersectionSize(setOf(1, 3, 5, 8), setOf(1, 4, 5, 9)))
    }
}
