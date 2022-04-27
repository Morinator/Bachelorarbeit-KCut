package util.collections

class MyCombinationIterator<T>(elems: Collection<T>, k: Int) : Iterator<Set<T>> {
    private val li = elems.toList()
    private val idx = IntArray(k) { k - 1 - it }
    private var hasNext = true

    override fun hasNext() = hasNext

    private fun inc(idx: IntArray, maxIndex: Int, depth: Int): Int {
        check(depth != idx.size) { "The End" }
        if (idx[depth] < maxIndex)
            idx[depth] = idx[depth] + 1
        else
            idx[depth] = inc(idx, maxIndex - 1, depth + 1) + 1
        return idx[depth]
    }

    private fun inc(): Boolean {
        return try {
            inc(idx, li.size - 1, 0)
            true
        } catch (e: IllegalStateException) {
            false
        }
    }

    /**
     * @return A NEW list of elements for the currently considered subset
     */
    override fun next(): Set<T> =
        idx.indices.reversed().mapTo(HashSet()) { li[idx[it]] }.also { hasNext = inc() }
}
