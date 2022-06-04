package util.collections

import java.util.Collections.sort
import java.util.Comparator.comparing

/** Returns the intersection of multiple sets in a new [HashSet] object.
 *
 * Let the sets you want to intersect be A_1, ..., A_n. The naive algorithm is to take a copy of A_1,
 * remove all elements not present in A_2, then remove all elements not present in A_3, and keep going like this
 * until A_n.
 *
 * If unfortunately A_1 is the biggest of those sets, you may have to remove needlessly many elements.
 * If you instead start with the smallest set A_i for i = 1...n, you already know you won't have to remove that many elements.
 * As an extreme example, assume n=2 with A_1 = {1, ..., 1_000_000} and A_2 = {1}.
 *
 * For this reason, the sets are sorted by their size in ascending order, and in this order the algorithm is
 * performed. This way, the function is significantly faster.*/
fun <T> intersectAll(collection: Collection<Set<T>>): Set<T> = run {
    val setsBySize = collection.toList().sortedBy { it.size }
    HashSet(setsBySize.first()).apply { for (i in 1 until setsBySize.size) retainAll(setsBySize[i]) }
}

/**
 * Removes and returns a random element of the set
 */
fun <T> MutableSet<T>.popRandom(): T {
    val elem = random()
    remove(elem)
    return elem
}

/**
 * @return this in a new mutable list, sorted in descending order by [metric]
 */
fun <T, R : Comparable<R>> Collection<T>.sortedDesc(metric: (T) -> R): MutableList<T> =
    toMutableList().apply {
        sort(this, comparing(metric))
        reverse() // LMAO this is ugly, but it makes the main code shorter and bloats it here, so worth I guess
    }


fun <E> intersectionSize(a: Collection<E>, b: Collection<E>) =
    if (a.size < b.size) a.count { it in b } else b.count { it in a }