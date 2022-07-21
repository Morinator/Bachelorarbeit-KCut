package graphlib.datastructures

/**
 * Undirected
 * no loops
 * only single edges
 */
class SimpleGraph<VType> {

    // The internal hashmap that maps any vertex to the set of its neighbours
    private val m = HashMap<VType, MutableSet<VType>>()

    /**
     * Runtime: O(1)  =  constant
     */
    fun addVertex(v: VType): SimpleGraph<VType> = this.apply {
        if (v !in m.keys)
            m[v] = HashSet()
    }

    /**
     * Like [addVertex], but for multiple vertices.
     * Runtime: O(l) for l vertices
     */
    fun addVertices(vertices: Collection<VType>): SimpleGraph<VType> = this.apply {
        vertices.forEach { addVertex(it) }
    }

    /**
     * Runtime: O(1)  =  constant
     */
    fun addEdge(a: VType, b: VType): SimpleGraph<VType> = this.apply {
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
    operator fun get(v: VType): Set<VType> = m[v]!!

    // TODO Test
    /**
     * @return The open neighbourhood of [S]
     */
    fun neighbours(S: Set<VType>): Set<VType> {
        return mutableSetOf<VType>().apply {
            S.map { get(it) }.forEach { addAll(it) }
            removeAll(S.toSet())
        }
    }

    /**
     * Runtime: O(1)  =  constant
     * @return True iff it contains [v] as a vertex.
     */
    operator fun contains(v: VType) = v in m.keys

    /**
     * Runtime: O( degreeOf(v) )    because every neighbour of [v] also needs to be updated.
     */
    fun deleteVertex(v: VType): SimpleGraph<VType> {
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
    fun deleteEdge(a: VType, b: VType): SimpleGraph<VType> {
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
    fun areNB(a: VType, b: VType): Boolean = a in m[b]!!

    /**
     * Runtime: O(1)  =  constant
     * @return The number of vertices in the graph (isolated vertices also count)
     */
    val size get() = m.keys.size

    /**
     * Runtime: O(1)  =  constant
     */
    fun degreeOf(v: VType): Int = m[v]!!.size

    /**
     * Runtime: O(m) because all edges are iterated
     * @return Number of edges in the graph
     */
    val edgeCount get() = m.values.sumOf { it.size } / 2

    @Suppress("PropertyName")
    val V: Set<VType>
        get() = m.keys

    /**Returns the neighbour-vertices of [vertices]. This is defined as the set of all vertices *v* so that *v* has an edge
     * to some vertex in [vertices], but *v* is not contained in [vertices].*/
    operator fun get(vertices: Collection<VType>): Set<VType> = HashSet<VType>().apply {
        for (v in vertices) addAll(get(v).filter { it !in vertices })
    }

    fun copy() = SimpleGraph<VType>().also { it.m.putAll(this.m) } // SHALLOW COPY

    fun edgeList() = ArrayList<Pair<VType, VType>>().apply {
        for (v in V)
            for (w in get(v))
                if (v.hashCode() < w.hashCode()) // so that edge doesn't appear twice
                    add(Pair(v, w)) // order doesn't matter
    }

    /**
     * Runtime: O(n + m) because all vertices and all edges appear in the string representation
     */
    override fun toString() = V.joinToString("\n") { "$it: ${get(it)}" }

    override fun equals(other: Any?) = (other is SimpleGraph<*>) && m == other.m

    override fun hashCode() = m.hashCode()

    val maxDegree: Int
        get() = V.maxOfOrNull { degreeOf(it) } ?: 0

    /**
     * Runtime: O(n * log n) because the degrees are sorted
     */
    val degreeSequence: List<Int>
        get() = V.map { degreeOf(it) }.sorted()
}
