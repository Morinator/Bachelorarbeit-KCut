package graphlib.datastructures

data class Solution<VType>(val V: MutableSet<VType>, var value: Int) {

    constructor(vertices: List<VType>, value: Int) : this(vertices.toMutableSet(), value)
}
