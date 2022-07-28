object Stats {

    var optimalHeuristics: Long = 0
    var candidates: Long = 0
    var treeNode: Long = 0
    var satRule: Long = 0
    var satVertices: Long = 0
    var needlessRule: Long = 0
    var kTimesDeltaRule: Long = 0

    fun print() {
        println("\n######### TESTING LOGGER #########\n")
        println("optimalHeuristics  " + optimalHeuristics.toString().padStart(15))
        println("treeNode:          " + treeNode.toString().padStart(15))
        println("candidates:        " + candidates.toString().padStart(15))
        println("satRule:           " + satRule.toString().padStart(15))
        println("satVertices:       " + satVertices.toString().padStart(15))
        println("needlessRule:      " + needlessRule.toString().padStart(15))
        println("kTimesDeltaRule:   " + kTimesDeltaRule.toString().padStart(15))
    }
}
