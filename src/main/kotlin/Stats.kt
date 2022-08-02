object Stats {

    const val padding = 10

    var numTrees : Long = 0
    var treeNode: Long = 0
    var candidates: Long = 0

    // OUTSIDE TREE
    var optimalUpperBounds: Long = 0
    var optimalHeuristics: Long = 0
    var kernelDeletions: Long = 0
    var kernelRuns: Long = 0
    var newExclusionRule : Long = 0

    // WITHIN TREE
    var satRule: Long = 0
    var satVertices: Long = 0
    var needlessRule: Long = 0
    var newRule : Long = 0

    fun print() {
        println("\n######### TESTING LOGGER #########\n")
        println("numTrees:            " + numTrees.toString().padStart(padding) + "   TREE-STATS")
        println("treeNode:            " + treeNode.toString().padStart(padding))
        println("candidates:          " + candidates.toString().padStart(padding))
        println("optimalUpperBounds   " + optimalUpperBounds.toString().padStart(padding) + "   OUTSIDE TREE")
        println("optimalHeuristics    " + optimalHeuristics.toString().padStart(padding))
        println("kernelApplications:  " + kernelDeletions.toString().padStart(padding))
        println("kernelRuns:          " + kernelRuns.toString().padStart(padding))
        println("newExclusionRule:    " + newExclusionRule.toString().padStart(padding))
        println("satRule:             " + satRule.toString().padStart(padding) + "   WITHIN TREE")
        println("satVertices:         " + satVertices.toString().padStart(padding))
        println("needlessRule:        " + needlessRule.toString().padStart(padding))
        println("newRule:             " + newRule.toString().padStart(padding))
    }
}
