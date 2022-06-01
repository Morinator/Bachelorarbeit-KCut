package util.collections

/**
 * Removes and returns a random element of the set
 */
fun <V> MutableMap<V, Int>.increment(key: V) {
    set(key, get(key)!! + 1)
}
