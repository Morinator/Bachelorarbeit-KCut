object Stats {
    var optimalUpperBounds : Long = 0
    var optimalHeuristics: Long = 0
    var treeNode: Long = 0
    var candidates: Long = 0
    var satRule: Long = 0
    var satVertices: Long = 0
    var needlessRule: Long = 0
    var kernelDeletions: Long = 0
    var kernelRuns: Long = 0

    fun print() {
        println("\n######### TESTING LOGGER #########\n")
        println("optimalUpperBounds   " + optimalUpperBounds.toString().padStart(15))
        println("optimalHeuristics    " + optimalHeuristics.toString().padStart(15))
        println("treeNode:            " + treeNode.toString().padStart(15))
        println("candidates:          " + candidates.toString().padStart(15))
        println("satRule:             " + satRule.toString().padStart(15))
        println("satVertices:         " + satVertices.toString().padStart(15))
        println("needlessRule:        " + needlessRule.toString().padStart(15))
        println("kernelApplications:  " + kernelDeletions.toString().padStart(15))
        println("kernelRuns:          " + kernelRuns.toString().padStart(15))
    }
}
