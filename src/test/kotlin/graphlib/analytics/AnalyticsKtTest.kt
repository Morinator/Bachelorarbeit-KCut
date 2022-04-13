package graphlib.analytics

import graphlib.constructors.Factory.createCycle
import org.junit.jupiter.api.Test

internal class AnalyticsKtTest {

    @Test
    fun printAnalyticsTest() { // It's just printing stuff, so the best we can do is check if it runs I guess
        printAnalytics(createCycle(5))
    }
}
