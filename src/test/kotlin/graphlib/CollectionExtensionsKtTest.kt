package graphlib

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.collections.sortedDesc

internal class CollectionExtensionsKtTest {
    @Test
    fun sortedDesc() {
        val s1 = setOf("aaaaaaaaaaaaa", "ho", "moritz", "i")
        assertEquals(listOf("aaaaaaaaaaaaa", "moritz", "ho", "i"), s1.sortedDesc { it.length })
    }
}
