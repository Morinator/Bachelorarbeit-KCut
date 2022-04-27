package util.collections

fun MutableList<Int>.incrementLast() {
    add(removeLast() + 1)
}