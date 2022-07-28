import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

object StackSolver {

    fun calc(G: SimpleGraph<Int, DefaultEdge>, k: Int, useHeuristic: Boolean): Set<Int> {

        var S = if (useHeuristic) heuristic(G, k, 10) else G.vertexSet().take(k).toSet()
        val counter = HashMap<Int, Int>().apply { for (v in G.vertexSet()) put(v, 0) }

        while (cut(G, S) <= G.vertexSet().map { G.degreeOf(it) }.sorted().takeLast(k).sum())
            S = runTree(G, k, cut(G, S) + 1, counter) ?: break

        AlgoStats.print()
        return S
    }

}

fun runTree(G: SimpleGraph<Int, DefaultEdge>, k: Int, t: Int, counter: Map<Int, Int>): Set<Int>? {
    val s = State(k = k, t = t)

    fun cont(v: Int) = (G.degreeOf(v) + counter[v]!!) - (2 * s.T.count { it in neighborSetOf(G, v) })

    fun needlessRule() {
        if (s.T.size < k) { //TODO Think about what is the fitting condition here
            val x = s.ext.last()
            if (cont(x.first()) < s.satBorder()) {
                val border = s.needlessBorder()
                n@ for (i in x.indices.reversed()) {
                    if (cont(x[i]) < border) {
                        AlgoStats.needlessRule++
                        x.removeLast()
                    } else
                        break@n
                }
            }
        }
    }

    s.ext.add(G.vertexSet().sortedBy { cont(it) }.reversed().toMutableList())
    needlessRule()

    while (s.ext.isNotEmpty()) {

        if (s.doSatRule()) AlgoStats.satRule++
        if (s.doBacktrack()) {

            if (s.T.size == k) {
                AlgoStats.candidates++

                if (s.valueOfT >= t)
                    return s.T.toSet()
            }

            if (s.T.size < k)
                s.ext.removeLast()
            if (s.satExists())
                s.sat.removeLast()

            if (s.T.size > 0)
                s.valueOfT -= cont(s.T.removeLast())

        } else { // ##### BRANCH #####

            AlgoStats.treeNode++

            val newElem = s.ext.last().removeFirst()

            if (s.T.size < k - 1)
                s.ext.add(s.ext.last().sortedBy { cont(it) }.reversed().toMutableList())

            val cont = cont(newElem)
            val isSatisfactory = cont >= s.satBorder()
            s.valueOfT += cont

            s.T.add(newElem)

            if (!s.satExists()) {
                s.sat.add(isSatisfactory)
                if (isSatisfactory)
                    AlgoStats.satVertices++
            }
            needlessRule()
        }
    } // end while-loop

    return null
}

class State(
    var valueOfT: Int = 0,
    val T: MutableList<Int> = ArrayList(),
    val sat: MutableList<Boolean> = ArrayList(),
    val ext: MutableList<MutableList<Int>> = ArrayList(),
    val k: Int,
    val t: Int
) {

    fun doBacktrack() = T.size >= k || T.size + ext.last().size < k || doSatRule()

    internal fun doSatRule() = satExists() && sat.last()

    fun satExists(): Boolean = (sat.size == T.size + 1)

    private fun kMissing(): Double = (k - T.size).toDouble()
    private fun tMissing(): Double = (t - valueOfT).toDouble()

    fun satBorder(): Double = (tMissing() / kMissing()) + 2 * (k - 1)

    fun needlessBorder(): Double = tMissing() / kMissing() - 2 * (k - 1) * (k - 1)
}

fun <V> cut(G: SimpleGraph<V, DefaultEdge>, S: Collection<V>): Int =
    S.sumOf { v -> neighborListOf(G,v).count { it !in S } }