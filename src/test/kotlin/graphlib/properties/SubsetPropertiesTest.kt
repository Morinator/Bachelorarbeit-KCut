package graphlib.properties

import graphlib.constructors.Factory.createPath
import graphlib.constructors.Factory.createStar
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class SubsetPropertiesTest {

    @Nested
    internal inner class IsIndependentSet {
        @Test
        fun star() {
            val g = createStar(10)
            println(g)
            assertTrue(isIndependentSet(setOf(2, 3, 4, 5, 6), g))
            assertFalse(isIndependentSet(setOf(1, 4), g))
            assertFalse(isIndependentSet(setOf(1, 2, 5, 7), g))
        }

        @Test
        fun path() {
            val g = createPath(10)
            println(g)
            assertTrue(isIndependentSet(setOf(1, 3, 6, 8), g))
            assertFalse(isIndependentSet(setOf(1, 2), g))
            assertFalse(isIndependentSet(setOf(1, 4, 5, 7), g))
        }
    }

    @Nested
    internal inner class IsVertexCover {
        @Test
        fun star() {
            val g = createStar(10)
            println(g)
            assertTrue(isVertexCover(setOf(1), g))
            assertTrue(isVertexCover((2..10).toSet(), g))
            assertFalse(isVertexCover(setOf(2, 4), g))
        }

        @Test
        fun path() {
            val g = createPath(7)
            println(g)
            assertFalse(isVertexCover(setOf(1, 5, 7), g))

            assertTrue(isVertexCover((1..7).toSet(), g))
            assertTrue(isVertexCover(setOf(1, 3, 5, 7), g))
            assertFalse(isVertexCover(setOf(1), g))
        }
    }
}