package graphlib.constructors

import graphlib.datastructures.SimpleGraph

fun <V> kCore(g: SimpleGraph<V>, k: Int): SimpleGraph<V> {
    val result = g.copy()
    while (true) {
        var hasChanged = false
        for (v in result.vertices().toList()) // prevents ConcurrentModificationException
            if (result.degreeOf(v) < k) {
                result.deleteVertex(v)
                hasChanged = true
            }
        if (!hasChanged)
            break
    }
    return result
}
