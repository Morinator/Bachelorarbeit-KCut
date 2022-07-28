import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

object StackSolver {

    fun calc(G: SimpleGraph<Int, DefaultEdge>, k: Int, useHeuristic: Boolean): Set<Int> {

        var S = if (useHeuristic) {
            (1..10).map { localSearchRun(G, k) }.maxByOrNull { cut(G, it) }!!
        } else
            G.vertexSet().take(k).toSet()

        val counter = G.vertexSet().associateWith { 0 }

        val upperBound = G.vertexSet().map { G.degreeOf(it) }.sorted().takeLast(k).sum()

        while (cut(G, S) <= upperBound)
            S = runTree(G, k, cut(G, S) + 1, counter)?.toSet() ?: break

        AlgoStats.print()
        return S
    }

    private fun runTree(G: SimpleGraph<Int, DefaultEdge>, k: Int, t: Int, counter: Map<Int, Int>): List<Int>? {

        val T: MutableList<Int> = ArrayList()
        var cut = 0

        val sat: MutableList<Boolean> = ArrayList()
        val ext: MutableList<MutableList<Int>> = ArrayList()

        fun satExists(): Boolean = (sat.size == T.size + 1)
        fun doSatRule() = satExists() && sat.last()
        fun satBorder(): Double = ((t - cut).toDouble() / (k - T.size).toDouble()) + 2 * (k - 1)

        fun cont(v: Int) = (G.degreeOf(v) + counter[v]!!) - (2 * T.count { it in neighborSetOf(G, v) })

        fun needlessRule() {
            if (T.size < k) { //TODO Think about what is the fitting condition here
                val x = ext.last()
                if (cont(x.first()) < satBorder()) {
                    val border = (t - cut).toDouble() / (k - T.size).toDouble() - 2 * (k - 1) * (k - 1)
                    n@ for (i in x.indices.reversed())
                        if (cont(x[i]) < border) {
                            AlgoStats.needlessRule++
                            x.removeLast()
                        } else
                            break@n
                }
            }
        }


        ext.add(G.vertexSet().sortedBy { cont(it) }.reversed().toMutableList())
        needlessRule()

        while (ext.isNotEmpty()) {

            if (doSatRule()) AlgoStats.satRule++
            if (T.size >= k || T.size + ext.last().size < k || doSatRule()) {

                if (T.size == k) {
                    AlgoStats.candidates++

                    if (cut >= t)
                        return T
                }

                if (T.size < k)
                    ext.removeLast()
                if (satExists())
                    sat.removeLast()

                if (T.size > 0)
                    cut -= cont(T.removeLast())

            } else { // ##### BRANCH #####

                AlgoStats.treeNode++

                val newElem = ext.last().removeFirst()

                if (T.size < k - 1)
                    ext.add(ext.last().sortedBy { cont(it) }.reversed().toMutableList())

                val cont = cont(newElem)
                val isSatisfactory = cont >= satBorder()
                cut += cont

                T.add(newElem)

                if (!satExists()) {
                    sat.add(isSatisfactory)
                    if (isSatisfactory)
                        AlgoStats.satVertices++
                }
                needlessRule()
            }
        } // end while-loop

        return null
    }
}

fun <V> cut(G: SimpleGraph<V, DefaultEdge>, S: Collection<V>): Int =
    S.sumOf { v -> neighborListOf(G, v).count { it !in S } }