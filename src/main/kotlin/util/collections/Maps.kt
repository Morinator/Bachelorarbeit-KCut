package util.collections

/**
 * Removes and returns a random element of the set
 */
fun MutableMap<Int, Int>.increment(key: Int) {
    set(key, get(key)!! + 1)
}