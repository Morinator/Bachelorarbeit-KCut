object AlgoStats {

    var candidateCounter: Long = 0
    var treeNodeCounter: Long = 0
    var satRuleCounter: Long = 0
    var satVerticesCounter: Long = 0

    fun print() {
        println("\n####### TESTING LOGGER #######\n")
        println("treeNodeCounter:     " + treeNodeCounter.toString().padStart(15))
        println("candidateCounter:    " + candidateCounter.toString().padStart(15))
        println("satRuleCounter:      " + satRuleCounter.toString().padStart(15))
        println("satVerticesCounter:  " + satVerticesCounter.toString().padStart(15))
    }
}
