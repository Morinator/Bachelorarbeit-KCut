package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution

interface DecisionSolver<V> {

    fun calc(t: Int ,g: SimpleGraph<V>, k: Int): Solution<V>?
}