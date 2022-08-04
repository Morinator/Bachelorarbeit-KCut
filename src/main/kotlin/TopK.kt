import java.util.*


fun topKWithHeap(col: Collection<Int>, k: Int): Set<Int> {
    if (k < 0) throw IllegalArgumentException("Illegal value for k")
    if (k == 0) return emptySet()
    if (k >= col.size) return col.toSet()

    val heap = PriorityQueue { a: Int, b: Int -> b - a }
    heap.addAll(col)

    return HashSet<Int>().apply {
        repeat(k) { add(heap.remove()) }
    }
}