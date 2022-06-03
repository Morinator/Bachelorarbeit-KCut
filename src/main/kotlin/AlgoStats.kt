object AlgoStats {

    var numSubsets: Long = 0
    var numTreeNodes: Long = 0
    var numSatRule: Long = 0

    fun print() {
        println("\n####### TESTING LOGGER #######\n")
        println("numTreeNodes:  " + numTreeNodes.toString().padStart(15))
        println("numSubsets:    " + numSubsets.toString().padStart(15))
        println("numSatRule:    " + numSatRule.toString().padStart(15))
    }
}