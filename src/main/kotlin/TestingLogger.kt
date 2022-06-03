object TestingLogger {

    var numSubsets: Long = 0
    var numTreeNodes: Long = 0
    var numSatRule: Long = 0

    fun print() {
        println("\n############### TESTING LOGGER ###############")
        println("numTreeNodes:  " + numTreeNodes.toString().padStart(10))
        println("numSubsets:    " + numSubsets.toString().padStart(10))
        println("numSatRule:    " + numSatRule.toString().padStart(10))
    }
}