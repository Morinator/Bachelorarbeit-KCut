package graphlib

class MyGraph<V> {

    private val m = HashMap<V, MutableSet<V>>()

    fun addVertex(v: V) {
        if (v !in m.keys)
            m[v] = HashSet()
    }

    fun addEdge(a: V, b: V) {
        if (a != b) {
            addVertex(a)
            addVertex(b)

            m[a]!!.add(b)
            m[b]!!.add(a)
        }
    }

    operator fun get(v: V): Set<V> = m[v]!!

    fun deleteVertex(v: V) {
        if (v in m.keys) {
            for (w in m[v]!!)
                m[w]!!.remove(v)
            m.remove(v)
        }
    }

    fun deleteEdge(a: V, b: V) {
        if (a in m[b]!!) {
            m[a]!!.remove(b)
            m[b]!!.remove(a)
        }
    }

    fun areNB(a: V, b: V): Boolean = a in m[b]!!

    val size get() = m.keys.size

    fun degreeOf(v: V): Int = m[v]!!.size

    val edgeCount get() = m.values.sumOf { it.size } / 2

    @Suppress("PropertyName")
    val V: Set<V>
        get() = m.keys

}
