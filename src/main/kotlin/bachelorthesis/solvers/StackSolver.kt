package bachelorthesis.solvers

import graphlib.datastructures.SimpleGraph
import graphlib.datastructures.Solution
import graphlib.properties.cutSize

// TODO auswerten wie gut Heuristik läuft

// TODO Regeln finden, ob man Suchbaum frühzeitig abbrechen kann

class StackSolver(private val g: SimpleGraph<Int>, private val k: Int) {

    private var bestSolution = Solution<Int>()
    private val counter: MutableMap<Int, Int> = g.vertices().associateWithTo(HashMap()) { 0 }

    private fun valWithCounter(S: Collection<Int>): Int = cutSize(g, S) + S.sumOf { counter[it]!! }
    private fun cont(v: Int, T: Collection<Int>) = g.degreeOf(v) + counter[v]!! - (2 * (g[v] intersect T.toSet()).size)

    fun calc(): Solution<Int> {

        var t = 0
        tIncreaseLoop@ while (t <= g.degreeSequence.takeLast(k).sum()) {

            val T = ArrayList<Int>()
            val extension = mutableListOf(g.vertices().toMutableList())

            var tmpSolution: Solution<Int>? = null

            searchTree@ while (extension.isNotEmpty()) {


                if (T.size < k && // ### branch ###
                    T.size + extension.last().size >= k
                ) {

                    val newElem = extension.last().first()
                    extension.last().remove(newElem)

                    if ((T.size < k - 1)) {
                        extension.add(extension.last().toMutableList())
                        extension.last().sortByDescending { cont(it, T) }
                    }

                    T.add(newElem)


                } else { // ### backtrack ###

                    if (T.size == k) {
                        val cutSize = valWithCounter(T)
                        if (cutSize >= t) {
                            tmpSolution = Solution(T, cutSize)
                            t = cutSize + 1
                            break@searchTree
                        }
                    }

                    if (T.size != k) {
                        extension.removeLast()
                    }

                    if (T.size > 0) {
                        T.removeLast()
                    }
                }
            }


            if (tmpSolution == null)
                break@tIncreaseLoop
            else
                bestSolution = tmpSolution
        }



        return bestSolution
    }
}
