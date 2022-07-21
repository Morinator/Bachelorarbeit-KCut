package kotlin_test.core.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.collections.intersectAll
import util.collections.sortedDesc
import util.collections.unionAll

internal class CollectionExtensionsKtTest {

    @Test
    fun intersectAll1() {
        val s1 = (1..100).toSet()
        val s2 = (-100..2).toSet()
        val s3 = setOf(-4, 1, 2, 6, 9)
        assertEquals(setOf(1, 2), intersectAll(listOf(s1, s2, s3)))
    }

    @Test
    fun unionAll1() {
        val sets = listOf(setOf(1, 2), setOf(1,3), setOf(7,8,9))
        assertEquals(setOf(1,2,3,7,8,9),unionAll(sets))
    }

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
