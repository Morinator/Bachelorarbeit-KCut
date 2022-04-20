package graphlib.constructors

import graphlib.datastructures.SimpleGraph

fun <V> inducedSubgraph(g: SimpleGraph<V>, vertices: Collection<V>): SimpleGraph<V> =
    SimpleGraph<V>().apply {
        vertices.forEach { addVertex(it) } // needed as vertices may get isolated

        for (v in vertices)
            for (nb in g[v])
                if (nb in vertices) addEdge(v, nb)
    }
