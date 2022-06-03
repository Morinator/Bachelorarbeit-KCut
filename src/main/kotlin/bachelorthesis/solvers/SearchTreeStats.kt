package bachelorthesis.solvers

/**
 * Tracks info about the algorithm itself while it runs.
 */
data class SearchTreeStats(
    var numSubsets: Int = 0,
    var numTreeNodes: Int = 0,
    var numSatisfactoryRuleApplications: Int = 0
)
