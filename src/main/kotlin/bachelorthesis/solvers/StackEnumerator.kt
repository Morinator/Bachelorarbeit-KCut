package bachelorthesis.solvers

fun subsetTreeStacked(col: Collection<Int>, k: Int) {

    val free = mutableListOf(HashSet(col))
    val curr: MutableList<Int> = ArrayList()

    while (free.isNotEmpty())
        if (free.last().isNotEmpty() && curr.size < k) { //branch

            val newElem = free.last().minOf { it }
            free.last().remove(newElem)

            free.add(HashSet(free.last()))
            curr.add(newElem)

        } else { // backtrack

            if (curr.size == k)
                println(curr)

            if (curr.size > 0)
                curr.removeLast()

            free.removeLast()
        }

}

fun main() {
    subsetTreeStacked(mutableListOf(1, 2, 3, 4, 5), 3)
}