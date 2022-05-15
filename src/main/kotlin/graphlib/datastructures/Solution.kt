package graphlib.datastructures

data class Solution<V>(val vertices: MutableSet<V> = HashSet(), var value: Int = 0) {

    constructor(vertices: List<V>, value: Int) : this(vertices.toMutableSet(), value)
}
