package util.collections

class Counter<E>(from: Collection<E>) {

    private val m = from.associateWithTo(HashMap()) { 0 }

    operator fun get(e: E): Int = m[e]!!

    fun increment(e: E) {
        m[e] = m[e]!! + 1
    }
}
