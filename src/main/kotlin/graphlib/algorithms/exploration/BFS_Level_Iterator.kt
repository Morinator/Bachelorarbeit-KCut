package graphlib.algorithms.exploration

import graphlib.datastructures.SimpleGraph
import java.util.*

class BFS_Level_Iterator<V>(private val g: SimpleGraph<V>, root: V) : Iterator<List<V>> {

    private val explored = hashSetOf(root)
    private val queue = LinkedList(listOf(root)) // init with just 1 element

    override fun next(): List<V> {
        val result = ArrayList(queue)
        queue.clear()

        for (v in result)
            g[v].filter { it !in explored }.also {  // non-explored neighbours
                queue.addAll(it)
                explored.addAll(it)
            }

        return result
    }

    override fun hasNext() = queue.isNotEmpty()
}