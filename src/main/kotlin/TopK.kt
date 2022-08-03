fun <E : Comparable<E>> topK(col: Collection<E>, k: Int): Set<E> {
    if (k > col.size) throw IllegalArgumentException("k too big for")
    if (k == 0) return emptySet()
    if (col.size == k) return col.toSet()

    val pivot = col.random()
    val (geq, lower) = col.partition { it >= pivot }

    return if (k <= geq.size)
        topK(geq, k)
    else
        geq.plus(topK(lower, k - geq.size)).toSet()
}

