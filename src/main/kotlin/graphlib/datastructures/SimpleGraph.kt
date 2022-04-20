package graphlib.datastructures

/**
 * Undirected
 * no loops
 * only single edges
 */
class SimpleGraph<V> {

    // The internal hashmap that maps any vertex to the set of its neighbours
    private val m = HashMap<V, MutableSet<V>>()

    /**
     * @return True if [v] wasn't contained already
     */
    fun addVertex(v: V): Boolean {
        if (v in m.keys) return false

        m[v] = HashSet() // has empty neighbour-set
        return true
    }

    fun addEdge(a: V, b: V) {
        if (a == b) return // no self-loops

        addVertex(a)
        addVertex(b)

        // we know they are contained from the calls above, so we do !!
        m[a]!!.add(b)
        m[b]!!.add(a)
    }

    /**
     * @return The neighbours of [v].
     */
    operator fun get(v: V): Set<V> = m[v]!!

    operator fun contains(v: V) = v in m.keys

    fun deleteVertex(v: V): Boolean {
        if (v !in m.keys) return false

        for (nb in m[v]!!)
            m[nb]!!.remove(v)
        m.remove(v)
        return true
    }

    /**
     * @return True if an edge between [a] and [b] previously existed
     */
    fun deleteEdge(a: V, b: V): Boolean {
        if (a !in m[b]!!) return false

        m[a]!!.remove(b)
        m[b]!!.remove(a)
        return true
    }

    /**
     * @return True if [a] and [b] are connected by an edge
     */
    fun areNeighbours(a: V, b: V): Boolean = a in m[b]!!

    /**
     * @return The number of vertices in the graph (isolated vertices also count)
     */
    val size get() = m.keys.size

    fun degreeOf(v: V): Int = m[v]!!.size

    /**
     * @return Number of edges in the graph
     */
    val edgeCount get() = m.values.sumOf { it.size } / 2

    fun vertices(): Set<V> = m.keys

    /**Returns the neighbour-vertices of [vertices]. This is defined as the set of all vertices *v* so that *v* has an edge
     * to some vertex in [vertices], but *v* is not contained in [vertices].*/
    operator fun get(vertices: Collection<V>): Set<V> = HashSet<V>().apply {
        for (v in vertices) addAll(get(v).filter { it !in vertices })
    }

    fun copy() = SimpleGraph<V>().also { it.m.putAll(this.m) } // SHALLOW COPY

    override fun toString() = vertices().joinToString("\n") { "$it: ${get(it)}" }

    override fun equals(other: Any?) = (other is SimpleGraph<*>) && m == other.m

    override fun hashCode() = m.hashCode()

    val delta: Int // MAX-DEGREE
        get() = vertices().maxOfOrNull { degreeOf(it) } ?: 0

    val degrees: List<Int>
        get() = vertices().map { degreeOf(it) }
}
