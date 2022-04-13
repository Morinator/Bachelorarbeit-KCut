package bachelorthesis

import org.paukov.combinatorics3.Generator

fun main() {
    Generator.combination(1, 2, 3, 4).simple(2).forEach { println(it) }
}
