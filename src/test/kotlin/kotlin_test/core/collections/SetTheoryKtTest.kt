package kotlin_test.core.collections

import core.collections.intersectAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SetTheoryKtTest {

    @Test
    fun intersectAll1() {
        val s1 = (1..100).toSet()
        val s2 = (-100..2).toSet()
        val s3 = setOf(-4, 1, 2, 6, 9)
        assertEquals(setOf(1, 2), intersectAll(listOf(s1, s2, s3)))
    }
}