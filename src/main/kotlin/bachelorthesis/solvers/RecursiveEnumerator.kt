package bachelorthesis.solvers

fun subsetTreeRecursive(curr: MutableList<Int>, free: MutableCollection<Int>, k: Int) {

    if (curr.size == k) {
        println(curr)
    } else {
        for (e in free.toList().sorted()) {
            free.remove(e)

            curr.add(e)

            subsetTreeRecursive(curr, HashSet(free), k)
        }
    }

    if (curr.size > 0)
        curr.removeLast()
}

fun main() {
    subsetTreeRecursive(ArrayList(), mutableListOf(1, 2, 3, 4, 5), 3)
}
