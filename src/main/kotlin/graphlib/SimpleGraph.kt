package graphlib

class SimpleGraph<V> {

    private val m = HashMap<V, MutableSet<V>>()

    fun addVertex(v: V): SimpleGraph<V> = this.apply {
        if (v !in m.keys)
            m[v] = HashSet()
    }

    fun addVertices(vertices: Collection<V>): SimpleGraph<V> = this.apply {
        vertices.forEach { addVertex(it) }
    }

    fun addEdge(a: V, b: V): SimpleGraph<V> = this.apply {
        if (a != b) {
            addVertex(a)
            addVertex(b)

            m[a]!!.add(b)
            m[b]!!.add(a)
        }
    }

    operator fun get(v: V): Set<V> = m[v]!!

    operator fun contains(v: V) = v in m.keys

    fun deleteVertex(v: V): SimpleGraph<V> {
        if (v in m.keys) {
            for (w in m[v]!!)
                m[w]!!.remove(v)
            m.remove(v)
        }

        return this
    }

    fun deleteEdge(a: V, b: V): SimpleGraph<V> {
        if (a in m[b]!!) {
            m[a]!!.remove(b)
            m[b]!!.remove(a)
        }

        return this
    }


    fun areNB(a: V, b: V): Boolean = a in m[b]!!


    val size get() = m.keys.size


    fun degreeOf(v: V): Int = m[v]!!.size


    val edgeCount get() = m.values.sumOf { it.size } / 2

    @Suppress("PropertyName")
    val V: Set<V>
        get() = m.keys


    operator fun get(vertices: Collection<V>): Set<V> = HashSet<V>().apply {
        for (v in vertices) addAll(get(v).filter { it !in vertices })
    }

    fun copy() = SimpleGraph<V>().also { it.m.putAll(this.m) } // SHALLOW COPY

    override fun toString() = V.joinToString("\n") { "$it: ${get(it)}" }

    override fun equals(other: Any?) = (other is SimpleGraph<*>) && m == other.m

    override fun hashCode() = m.hashCode()

    val maxDegree: Int
        get() = V.maxOfOrNull { degreeOf(it) } ?: 0


    val degreeSequence: List<Int>
        get() = V.map { degreeOf(it) }.sorted()
}
