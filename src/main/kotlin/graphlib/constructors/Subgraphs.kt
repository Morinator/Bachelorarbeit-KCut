package graphlib.constructors

import graphlib.datastructures.SimpleGraph

fun <VType> inducedSubgraph(G: SimpleGraph<VType>, S: Collection<VType>): SimpleGraph<VType> =
    SimpleGraph<VType>().apply {
        addVertices(S)

        for (v in S)
            for (w in G[v])
                if (w in S) addEdge(v, w)
    }
