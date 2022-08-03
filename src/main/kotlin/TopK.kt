import java.util.*

fun <E : Comparable<E>> topKWithPivot(col: Collection<E>, k: Int): Set<E> {
    if (k > col.size) throw IllegalArgumentException("k too big for")
    if (k == 0) return emptySet()
    if (col.size == k) return col.toSet()

    val pivot = col.random()
    val (geq, lower) = col.partition { it >= pivot }

    return if (k <= geq.size)
        topKWithPivot(geq, k)
    else
        geq.plus(topKWithPivot(lower, k - geq.size)).toSet()
}


fun topKWithHeap(col: Collection<Int>, k: Int): Set<Int> {
    if (k > col.size || k < 0) throw IllegalArgumentException("Illegal value for k")
    if (k == 0) return emptySet()

    val heap = PriorityQueue { a: Int, b: Int -> b - a }
    heap.addAll(col)

    val res = HashSet<Int>()
    repeat(k) { res.add(heap.remove()) }
    return res
}