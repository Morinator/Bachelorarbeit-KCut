package graphlib.properties

import graphlib.constructors.Factory.createPath
import graphlib.constructors.Factory.createStar
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class SubsetPropertiesTest {

    @Test
    fun star() {
        val g = createStar(10)
        println(g)
        assertTrue(isIndependentSet(setOf(2, 3, 4, 5, 6), g))
        assertFalse(isIndependentSet(setOf(1, 4), g))
        assertFalse(isIndependentSet(setOf(1, 2, 5,7), g))
    }

    @Test
    fun path() {
        val g = createPath(10)
        println(g)
        assertTrue(isIndependentSet(setOf(1,3,6,8), g))
        assertFalse(isIndependentSet(setOf(1, 2), g))
        assertFalse(isIndependentSet(setOf(1,4,5,7), g))
    }
}