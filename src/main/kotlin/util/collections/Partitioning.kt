package util.collections

/**This data structure represents a set *S* that is partitioned.
 * This means *S* is divided into subsets that are mutually disjoint, but the union of all the subset is *S*.
 *
 * One potential reason a partition of a set might arise is an equivalence relation.
 * In this case each subset of *S* consists of elements that are *equal* by the relation.
 *
 * @param T The type of the elements.*/
class Partitioning<T> {

    /** Maps each element to the subset it is partitioned in.*/
    private val m = LinkedHashMap<T, MutableSet<T>>()

    val elements: Set<T>
        get() = m.keys

    val size: Int
        get() = m.size

    /**
     * @return A list of all distinct subsets in the partitioning
     */
    fun subsets(): List<Set<T>> = m.values.distinct()

    /**@return *True* iff any subset contains [t]*/
    operator fun contains(t: T): Boolean = t in elements

    /**@return The subset the element [t] is in, throws exception if [t] is not in any subset.*/
    operator fun get(t: T): Set<T> = m[t]!!

    /**Adds [newElem] in a new subset, that then only contains [newElem].*/
    operator fun plusAssign(newElem: T) {
        if (newElem !in this) m[newElem] = hashSetOf(newElem)
    }

    /**Creates a new subset containing all elements from [elements].
     * Throws exception if it already contains some element from [elements].*/
    fun add(elements: Collection<T>) {
        val someElement = elements.first()
        this += someElement
        elements.forEach { addToSubset(someElement, it) }
    }

    /**Adds [newElem] to the subset that [oldElem] is already in.
     * If [newElem] is already contained, then nothing happens.*/
    fun addToSubset(oldElem: T, newElem: T) {
        if (newElem !in this) {
            m[oldElem]!!.add(newElem)
            m[newElem] = m[oldElem]!!
        }
    }

    /**If [elem] is in some subset of the partitioning, then [elem] gets removed from that subset.*/
    operator fun minusAssign(elem: T) {
        m[elem]?.remove(elem)
        m.remove(elem)
    }

    /**Calls [minusAssign] on every element from [elements].
     * Each element gets removed from the subset is in, if there is such a subset.*/
    operator fun minusAssign(elements: Collection<T>) {
        elements.forEach { this -= it }
    }

    /**If there is a subset *s* of this partitioning so that [elem] is in *s*,
     * then *s* gets removed from the partitioning*/
    fun removeSubset(elem: T) {
        m[elem]!!.toList().forEach { this -= it }
    }

    /**Adds all subsets of [other] as new subsets.
     * This methods requires the elements of [other] to be disjoint from
     * the element in this <=> [other] may not contain an element that is already saved in this object.*/
    fun disjointUnion(other: Partitioning<T>) {
        other.elements.forEach { require(it !in this) }
        other.subsets().forEach { add(it) }
    }

    /**Let [a] and [b] two elements of some subsets (potentially same) in this [Partitioning].
     * This method merges the subset that contains [a] and the subset that contains [b] into one big subset.
     */
    fun merge(a: T, b: T) {
        require(a in this && b in this)

        if (m[a] === m[b]) return       //a and b already are in the same subset

        val (x, y) = listOf(a, b).sortedBy { m[it]!!.size } //size of the subset m[x] is <= the size of the subset [my]

        m[x]!!.toList().forEach {   //adds the smaller subset into the other
            m[y]!!.add(it)
            m[it] = m[y]!!
        }
    }
}