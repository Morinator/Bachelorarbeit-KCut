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
     * Runtime: O(1)  =  constant
     */
    fun addVertex(v: V): SimpleGraph<V> = this.apply {
        if (v !in m.keys)
            m[v] = HashSet()
    }

    /**
     * Like [addVertex], but for multiple vertices.
     * Runtime: O(l) for l vertices
     */
    fun addVertices(vertices: Collection<V>): SimpleGraph<V> = this.apply {
        vertices.forEach { addVertex(it) }
    }

    /**
     * Runtime: O(1)  =  constant
     */
    fun addEdge(a: V, b: V): SimpleGraph<V> = this.apply {
        if (a != b) {
            addVertex(a)
            addVertex(b)

            m[a]!!.add(b)
            m[b]!!.add(a)
        }
    }

    // TODO create alias "neighbours"
    /**
     * Runtime: O(1)  =  constant; because no new set is generated,
     * but you only get a view of an internally existing object.
     *
     * @return The neighbours of [v].
     */
    operator fun get(v: V): Set<V> = m[v]!!

    // TODO Test
    /**
     * @return The open neighbourhood of [vertices]
     */
    fun neighbours(vertices: Set<V>): Set<V> {
        return mutableSetOf<V>().apply {
            vertices.map { get(it) }.forEach { addAll(it) }
            removeAll(vertices.toSet())
        }
    }

    /**
     * Runtime: O(1)  =  constant
     * @return True iff it contains [v] as a vertex.
     */
    operator fun contains(v: V) = v in m.keys

    /**
     * Runtime: O( degreeOf(v) )    because every neighbour of [v] also needs to be updated.
     */
    fun deleteVertex(v: V): SimpleGraph<V> {
        if (v in m.keys) {
            for (nb in m[v]!!)
                m[nb]!!.remove(v)
            m.remove(v)
        }

        return this
    }

    /**
     * Runtime: O(1)  =  constant
     */
    fun deleteEdge(a: V, b: V): SimpleGraph<V> {
        if (a in m[b]!!) {
            m[a]!!.remove(b)
            m[b]!!.remove(a)
        }

        return this
    }

    /**
     * Runtime: O(1)  =  constant
     * @return True if [a] and [b] are connected by an edge
     */
    fun areNB(a: V, b: V): Boolean = a in m[b]!!

    /**
     * Runtime: O(1)  =  constant
     * @return The number of vertices in the graph (isolated vertices also count)
     */
    val size get() = m.keys.size

    /**
     * Runtime: O(1)  =  constant
     */
    fun degreeOf(v: V): Int = m[v]!!.size

    /**
     * Runtime: O(|E|) because all edges are iterated
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

    /**
     * Runtime: O(|V| + |E|) because all vertices and all edges appear in the string representation
     */
    override fun toString() = vertices().joinToString("\n") { "$it: ${get(it)}" }

    override fun equals(other: Any?) = (other is SimpleGraph<*>) && m == other.m

    override fun hashCode() = m.hashCode()

    val maxDegree: Int // MAX-DEGREE
        get() = vertices().maxOfOrNull { degreeOf(it) } ?: 0

    /**
     * Runtime: O( |V| * log|V| ) because the degrees are sorted
     */
    val degreeSequence: List<Int>
        get() = vertices().map { degreeOf(it) }.sorted()
}
