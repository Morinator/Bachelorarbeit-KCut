package graphlib.properties

import graphlib.algorithms.exploration.checkIfConnected
import graphlib.datastructures.SimpleGraph

/**
 * @return True iff [g] is a tree, which is equivalent to being connected and cycle-free.
 */
fun <V> checkIfTree(g: SimpleGraph<V>) = checkIfConnected(g) && g.edgeCount == g.size - 1
