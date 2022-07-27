package bachelorthesis.fixedmaxcut

import bachelorthesis.cut
import graphlib.Factory.createPath
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CutSizeKtTest {

    @Test
    fun cutSize1() {
        assertEquals(2, cut(createPath(4), setOf(2)))
    }
}
