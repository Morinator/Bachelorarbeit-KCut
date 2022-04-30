package bachelorthesis.solvers.extensionbased


fun aaaa(curr: MutableList<Int>, free: MutableCollection<Int>, k: Int) {

    if (curr.size == k) {
        println(curr)
        return
    }

    for (e in free.toList().sorted()) {
        free.remove(e)

        val newCurr = ArrayList(curr)
        newCurr.add(e)

        aaaa(newCurr, HashSet(free), k)
    }
}

fun main() {
    aaaa(ArrayList(), mutableListOf(1, 2, 3, 4, 5), 3)
}