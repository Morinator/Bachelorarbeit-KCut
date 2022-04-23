package graphlib

import graphlib.datastructures.SimpleGraph
import org.paukov.combinatorics3.Generator.combination
import org.paukov.combinatorics3.Generator.permutation

// TODO Write more tests for this because this looks like it could have some bugs
/**
 * Brute-force checker if two graphs are isomorphic.
 * Works by checking all possible bijections of vertices by iterating through permutations of size |V|.
 */
fun <V> checkIfIsomorphic(g1: SimpleGraph<V>, g2: SimpleGraph<V>): Boolean {

    val vG1 = g1.vertices().toList(); val vG2 = g2.vertices().toList()
    val permIndices = vG1.indices.toList()


    permutation(permIndices).simple().forEach permutationLoop@{ perm ->
        for ((a, b) in combination(permIndices).simple(2)) {

            val nbInG1 = g1.areNB(vG1[perm[a]], vG1[perm[b]])
            val nbInG2 = g2.areNB(vG2[perm[a]], vG2[perm[b]])
            if (nbInG1 != nbInG2) return@permutationLoop
        }

        return true // didn't continue to next loop-iteration, so this one was a success!
    }
    return false // didn't find a valid permutation
}