package bachelorthesis

import graphlib.constructors.Factory.createClique

fun main() {
    val g = createClique(5)
    println(g.size())
}