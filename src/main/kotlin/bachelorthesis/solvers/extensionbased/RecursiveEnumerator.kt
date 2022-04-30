package bachelorthesis.solvers.extensionbased

fun subsetTreeRecursive(curr: MutableList<Int>, free: MutableCollection<Int>, k: Int) {

    if (curr.size == k) {
        println(curr)
        return
    }

    for (e in free.toList().sorted()) {
        free.remove(e)

        val newCurr = ArrayList(curr)
        newCurr.add(e)

        subsetTreeRecursive(newCurr, HashSet(free), k)
    }
}

fun main() {
    subsetTreeRecursive(ArrayList(), mutableListOf(1, 2, 3, 4, 5), 3)
}