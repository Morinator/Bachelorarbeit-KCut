package graphlib

data class Solution<VType>(val subset: MutableSet<VType>, var value: Int) {

    constructor(vertices: List<VType>, value: Int) : this(vertices.toMutableSet(), value)
}
