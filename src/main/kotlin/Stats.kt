object Stats {

    const val padding = 10

    var treeNode: Long = 0
    var candidates: Long = 0

    // OUTSIDE TREE
    var optimalUpperBounds: Long = 0
    var optimalHeuristics: Long = 0
    var kernelDeletions: Long = 0
    var kernelRuns: Long = 0

    // WITHIN TREE
    var satRule: Long = 0
    var satVertices: Long = 0
    var needlessRule: Long = 0

    fun print() {
        println("\n######### TESTING LOGGER #########\n")
        println("treeNode:            " + treeNode.toString().padStart(padding) + "   TREE-STATS")
        println("candidates:          " + candidates.toString().padStart(padding))
        println("optimalUpperBounds   " + optimalUpperBounds.toString().padStart(padding) + "   OUTSIDE TREE")
        println("optimalHeuristics    " + optimalHeuristics.toString().padStart(padding))
        println("kernelApplications:  " + kernelDeletions.toString().padStart(padding))
        println("kernelRuns:          " + kernelRuns.toString().padStart(padding))
        println("satRule:             " + satRule.toString().padStart(padding) + "   WITHIN TREE")
        println("satVertices:         " + satVertices.toString().padStart(padding))
        println("needlessRule:        " + needlessRule.toString().padStart(padding))
    }
}
