package util.collections

import java.util.Collections.sort
import java.util.Comparator.comparing

/**
 * @return this in a new mutable list, sorted in descending order by [metric]
 */
fun <T, R : Comparable<R>> Collection<T>.sortedDesc(metric: (T) -> R): MutableList<T> =
    toMutableList().apply {
        sort(this, comparing(metric))
        reverse() // this is ugly, but it makes the main code shorter and bloats it here, so worth I guess
    }