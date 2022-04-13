package graphlib.datastructures

data class Solution<V>(val vertices: Set<V>, val value: Int) {

    constructor(vertices: List<V>, value: Int) : this(vertices.toSet(), value)
}
