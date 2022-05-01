package bachelorthesis.solvers

import graphlib.datastructures.Solution

abstract class DecisionSolver<V> {

    abstract fun calc(t: Int): Solution<V>?
}