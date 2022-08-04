import java.util.*


fun topSelect(col: Collection<Int>, num: Int): Set<Int> {
    if (num < 0) throw IllegalArgumentException("Illegal value for k")
    if (num == 0) return emptySet()
    if (num >= col.size) return col.toSet()

    val heap = PriorityQueue { a: Int, b: Int -> b - a }
    heap.addAll(col)

    return HashSet<Int>().apply {
        repeat(num) { add(heap.remove()) }
    }
}