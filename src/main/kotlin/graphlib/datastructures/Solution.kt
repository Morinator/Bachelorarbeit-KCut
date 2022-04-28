package graphlib.datastructures

data class Solution<V>(val vertices: MutableSet<V> = HashSet(), var value: Int = Int.MIN_VALUE) {

    constructor(vertices: List<V>, value: Int) : this(vertices.toMutableSet(), value)
}
