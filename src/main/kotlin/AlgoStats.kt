object AlgoStats {

    var numCandidates: Long = 0
    var numTreeNodes: Long = 0
    var numSatRule: Long = 0
    var numSatVertices: Long = 0

    fun print() {
        println("\n####### TESTING LOGGER #######\n")
        println("numTreeNodes:    " + numTreeNodes.toString().padStart(15))
        println("numSubsets:      " + numCandidates.toString().padStart(15))
        println("numSatRule:      " + numSatRule.toString().padStart(15))
        println("numSatVertices:  " + numSatVertices.toString().padStart(15))
    }
}
