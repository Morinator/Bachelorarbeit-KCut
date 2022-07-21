package graphlib.exploration

import graphlib.datastructures.SimpleGraph
import java.util.*

class BFS_Level_Iterator<VType>(private val G: SimpleGraph<VType>, v: VType) : Iterator<List<VType>> {

    private val explored = hashSetOf(v)
    private val queue = LinkedList(listOf(v)) // init with just 1 element

    override fun next(): List<VType> {
        val result = ArrayList(queue)
        queue.clear()

        for (v in result)
            G[v].filter { it !in explored }.also { // non-explored neighbours
                queue.addAll(it)
                explored.addAll(it)
            }

        return result
    }

    override fun hasNext() = queue.isNotEmpty()
}
