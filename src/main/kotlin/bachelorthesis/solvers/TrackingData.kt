package bachelorthesis.solvers

/**
 * Tracks info about the algorithm itself while it runs.
 */
data class TrackingData(
    var subsets: Int = 0,
    var treeNodes: Int = 0
)
