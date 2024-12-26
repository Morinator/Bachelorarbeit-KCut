# Algorithm Engineering for `Max Cut` with Cardinality Constraints


This project contains the code for my Bachelor thesis, working with the Algorithms research group of the University of Marburg.

The following is an english translation of the summary of the thesis (see PDF in project):

---

In the graph problem Max (k, n - k)-Cut, a vertex set S of size k is sought so that as many edges as possible have exactly one endpoint in S. The number of these edges is called the cut size. In addition, Max (k, n - k)-Cut is an NP-hard graph problem.

An exact branch-and-bound algorithm for Max (k, n - k)-Cut is designed and implemented.
Improvements are made to this algorithm, which are largely based on recognizing whether a current partial solution can still be extended to an optimal solution. 
We will apply algorithmic techniques such as preprocessing the problem instance by a so-called kernel and creating an upper bound by local search. 
We will also dynamically cache the function value instead of completely recalculating it for each candidate solution. 

Finally, we model Max (k, n - k)-Cut also as an Integer Linear Program (ILP) and solve it using the CPLEX solver from IBM.

We use 19 common benchmark graphs to solve for the parameter values k ∈ {1, . . . , 30} to evaluate the runtimes.
For each implemented improvement, we evaluate how much the runtime has been reduced compared to the previous version.
The experiments show that the effectiveness of the various improvements varies greatly.
For 4 of the 19 graphs tested, the maximum k solved within one hour was increased from 1 to at least 12 by all improvements in total.
The ILP can be increased within one hour.
