package graphlib.datastructures

data class Solution<V>(val vertices: MutableSet<V>, var value: Int) {

    constructor(vertices: List<V>, value: Int) : this(vertices.toMutableSet(), value)
}
