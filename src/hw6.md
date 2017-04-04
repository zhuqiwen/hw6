#hw6
######by Qiwen Zhu

##1.a
Show how this graph is represented as an adjacency matrix.
````
    0    1    2    3    4    5    6
0   0    2    1    1    0    0    0

1   0    0    0    3    4    0    0

2   0    0    0    0    0    5    0

3   0    0    2    0    2    2    8

4   0    0    0    0    0    0    5

5   0    0    0    0    0    0    0

6   0    0    0    0    0    1    0
````

##1.b
Show how this graph is represented as adjacency lists.
````aidl
(vertex, edge)
0: {(1, 2), (2, 1), (3, 1)}
1: {(3, 3), (4, 4)}
2: {(5, 5)}
3: {(2, 2), (4, 2), (5, 2), (6, 8)}
4: {(6, 5)}
5: {}
6: {(5, 1)}
````

##1.c
You run the memoized Shortest Path algorithm, described in lec10a to find δ(4, 2), which is the length of the shortest path from vertex 4 to vertex 2. Along the way, you update the cache. Recall that the cache is a table where each entry contains a vertex x and δ(4, x). Show the state of the cache when the algorithm terminates.
````
    x   | δ(4, x)
    0   | infinite
    1   | infinite
    2   | infinite
    3   | infinite  
````

##1.d
Why does the algorithm not work for graphs with cycles?

If there are cycles in the graph, there can be infinite loop when it is unreachable from s to t. 



##2
Follow the instructions in this starter file to complete the implementation of a Digraph in terms of adjacency lists: ListDigraph.java. Build a detailed Testing class with comprehensive tests of all methods. ShortestPath.java contains an implementation of the memoized Shortest Path algorithm. In ShortestPath.main(), create the graph given in Problem 1 and then call shortestPath() to find the solution for s = 0 and t = 6. Print out the cache with each update and compare your results to the answer you submitted for the lec11b pre-lecture exercise. Submit three files: ListDigraph.java, Testing.java, and ShortestPath.java.
