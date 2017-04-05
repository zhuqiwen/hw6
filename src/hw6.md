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

#### ListDigraph.java

````java
/**
 * Created by Qiwen Zhu on 4/3/17.
 */

import java.util.*;

interface Digraph {
	Iterable<Integer> in(int v);
	Iterable<Integer> out(int v);
	default void addEdge(int u, int v) {
		addEdge(u, v, 1);
	}
	void addEdge(int u, int v, int weight);
	default int weight(int u, int v) {
		throw new UnsupportedOperationException();
	}
	int numVertices();
	int numEdges();
}

/**
 * ListDigraph is a class that implements a directed graph using
 * a Map of adjacency lists.
 *
 * A digraph may have self-loops but no parallel edges. We assume that
 * vertices are labeled by the integers 0, 1, ..., n - 1. The edges are
 * labeled with a positive weight.
 *
 * TODO: Give the big-Oh cost for each of the following operations in
 *       terms of n (the number of vertices):
 *          in            O(n^2)
 *          out           O(n)
 *          addEdge       O(1)
 *          weight        O(n)
 *          numVertices   O(1)
 *          numEdges      O(1)
 *          twoHopsAway   O(n^2)
 */

public class ListDigraph implements Digraph {

	/**
	 * An Edge encapsulates the incident vertex and a weight.
	 */
	class Edge {
		int to, weight;
		Edge(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}

		public String toString() {
			return String.format("(%d,%d)", to, weight);
		}
	}

	private Map<Integer, List<Edge>> adj;
	private int n;         // number of vertices
	private int m;         // number of edges

	/**
	 * TODO
	 *
	 * Create a directed graph with no edges and n vertices,
	 * labeled 0, 1, 2, ..., n - 1. Assume n is non-negative.
	 */
	public ListDigraph(int n) {
		assert n >= 0;
		this.n = n;
		adj  = new HashMap<>();
		for (int i = 0; i < n; i++)
		{
			adj.put(i, new LinkedList<>());
		}
	}

	/**
	 * TODO
	 *
	 * Returns the weight of the edge from u to v, if it exists. Otherwise,
	 * Integer.MAX_VALUE is returned.
	 */
	public int weight(int u, int v)
	{
		if(!hasEdge(u, v))
		{
			throw new IllegalArgumentException("there is no edge from " + u + " to " + v);
		}

		for(Edge edge : adj.get(u))
		{
			if(edge.to == v)
			{
				return edge.weight;
			}
		}
		return Integer.MAX_VALUE;
	}

	/**
	 * TODO
	 *
	 * Returns true iff the edge between u and v exists.
	 */
	public boolean hasEdge(int u, int v)
	{
		for(Edge edge : adj.get(u))
		{
			if(edge.to == v)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the number of vertices in this graph.
	 */
	public int numVertices() {
		return n;
	}

	/**
	 * Returns the number of edges in this graph.
	 */
	public int numEdges() {
		return m;
	}

	/**
	 * TODO
	 *
	 * Adds the edge from u to v of the given weight to this graph. If
	 * the edge already exists, then its weight is replaced with the new weight.
	 */
	public void addEdge(int u, int v, int weight)
	{
		if(hasEdge(u, v))
		{
			for(Edge edge : adj.get(u))
			{
				if(edge.to == v)
				{
					edge.weight = weight;
				}

			}
			return;
		}
		m++;
		adj.get(u).add(new Edge(v, weight));
	}

	/**
	 * TODO
	 *
	 * Returns those vertices that are incident to an outgoing edge of v.
	 */
	public Set<Integer> out(int v) {
		Set<Integer> neighbors = new HashSet<Integer>();
		for(Edge edge :  adj.get(v))
		{
			neighbors.add(edge.to);
		}

		return neighbors;
	}

	/**
	 * TODO
	 *
	 * Returns those vertices that are incident to an incoming edge of v.
	 */
	public Set<Integer> in(int v) {
		Set<Integer> neighbors = new HashSet<Integer>();
		for(Integer u : adj.keySet())
		{
			for(Edge edge :  adj.get(u))
			{
				if(edge.to == v)
				{
					neighbors.add(u);
				}
			}
		}

		return neighbors;
	}

	/**
	 * TODO
	 *
	 * Returns those vertices that are exactly two hops away from v.
	 */
	public Set<Integer> twoHopsAway(int v) {
		Set<Integer> neighbors = new HashSet<>();
		for(int i : adj.keySet())
		{
			if(hasEdge(v, i))
			{
				for(int j : adj.keySet())
				{
					if(hasEdge(i, j))
					{
						neighbors.add(j);
					}
				}
			}
		}

		return neighbors;
	}

	/**
	 * Returns a textual representation of the adjacency lists.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Adjacency lists (n = %d, m = %d)", n, m));
		for (int i = 0; i < n; i++) {
			sb.append("\n\t" + i + ": ");
			sb.append(adj.get(i));
		}
		return sb.toString();
	}

}
````

#### ShortestPath.java

```java
/**
 * Created by Qiwen Zhu on 4/3/17.
 */


import java.util.HashMap;
import java.util.Map;

public class ShortestPath {

	/**
	 * Returns the length of the shortest path from s to t in the weighted
	 * acyclic digraph gr.
	 */
	public static int shortestPath(int s, int t, Digraph gr) {
		return shortestPathHelper(s, t, gr, new HashMap<Integer, Integer>());
	}

	private static int shortestPathHelper(int s, int t, Digraph gr,
										  Map<Integer, Integer> cache)
	{



		if (cache.containsKey(t))
			return cache.get(t);
		int ans;
		if (s == t)
			ans = 0;
		else {
			ans = Integer.MAX_VALUE;
			for (int x : gr.in(t))
				ans = Math.min(ans, add(shortestPathHelper(s, x, gr, cache),
						gr.weight(x, t)));
		}
		cache.put(t, ans);

		if(!cache.isEmpty())
		{
			System.out.println("<><><><><><><><><><><><>");
			System.out.println("\tx\t|\tδ(" + s + ", x)");
			for(Integer k : cache.keySet())
			{
				System.out.println("\t" + k + "\t|\t" +cache.get(k)+ "\t");
			}
		}

		return ans;
	}

	/**
	 * Returns x + y, where x and y are positive integers. In the case
	 * of overflow, Integer.MAX_VALUE is returned.
	 */
	public static int add(int x, int y) {
		int sum = x + y;
		return sum < 0 ? Integer.MAX_VALUE : sum;
	}

	public static void main (String[] args) {
		ListDigraph gr = new ListDigraph(5);
		gr.addEdge(0, 1, 1);
		gr.addEdge(0, 2, 2);
		gr.addEdge(1, 3, 4);
		gr.addEdge(2, 1, 5);
		gr.addEdge(2, 3, 6);
		gr.addEdge(4, 2, 6);
//		assert 5 == shortestPath(0, 3, gr);
//		assert 6 == shortestPath(4, 2, gr);
//		assert Integer.MAX_VALUE == shortestPath(2, 4, gr);
//		System.out.println(gr);

		/**
		 * TODO
		 *
		 * Create the graph shown in the hw, find the shortest path from 0 to 6,
		 * and print the cache as you go.
		 */

		gr = new ListDigraph(7);
		gr.addEdge(0, 1, 2);
		gr.addEdge(0, 2, 1);
		gr.addEdge(0, 3, 1);
		gr.addEdge(1, 3, 3);
		gr.addEdge(1, 4, 4);
		gr.addEdge(2, 5, 5);
		gr.addEdge(3, 2, 2);
		gr.addEdge(3, 4, 2);
		gr.addEdge(3, 5, 2);
		gr.addEdge(3, 6, 8);
		gr.addEdge(4, 6, 5);
		gr.addEdge(6, 5, 1);

		assert 8 == shortestPath(0, 6, gr);
		assert Integer.MAX_VALUE == shortestPath(4, 0, gr);
		assert Integer.MAX_VALUE == shortestPath(4, 1, gr);
		assert Integer.MAX_VALUE == shortestPath(4, 2, gr);
		assert Integer.MAX_VALUE == shortestPath(4, 3, gr);



//		System.out.println(gr);

		gr = new ListDigraph(7);
		gr.addEdge(0, 1, 2);
		gr.addEdge(0, 2, 1);
		gr.addEdge(0, 3, 1);
		gr.addEdge(1, 3, 3);
		gr.addEdge(1, 4, 4);
		gr.addEdge(2, 5, 5);
		gr.addEdge(3, 2, 2);
		gr.addEdge(3, 4, 2);
		gr.addEdge(3, 5, 2);
		// here we make the graph cyclic one. 4 -> 6 -> 3 -> 4
		gr.addEdge(6, 3, 8);
		gr.addEdge(4, 6, 5);
		gr.addEdge(6, 5, 1);

//		shortestPath(4, 2, gr);
//		shortestPath(5, 2, gr);
//		shortestPath(1, 0, gr);

		gr = new ListDigraph(-5);
	}
}
```

#### Testing.java

````java
/**
 * Created by Qiwen Zhu on 4/3/17.
 */



import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.lang.AssertionError;

import org.junit.Test;


public class Testing
{

	@Test
	public void noEdgeTest()
	{
		ListDigraph gr = new ListDigraph(3);
		assertTrue(!gr.hasEdge(0 ,1));
		assertTrue(!gr.hasEdge(0 ,2));
		assertTrue(!gr.hasEdge(1 ,0));
		assertTrue(!gr.hasEdge(1 ,2));
		assertTrue(!gr.hasEdge(2 ,1));
		assertTrue(!gr.hasEdge(2 ,0));
	}

	@Test
	public void hasEdgeTest()
	{
		ListDigraph gr = new ListDigraph(3);
		gr.addEdge(0, 1, 2);
		assertTrue(gr.hasEdge(0 ,1));
		assert 2 == gr.weight(0, 1);

		gr.addEdge(0, 2, 4);
		assertTrue(gr.hasEdge(0 ,2));
		assert 4 == gr.weight(0, 2);

		gr.addEdge(1, 2, 3);
		assertTrue(gr.hasEdge(1 ,2));
		assert 2 != gr.weight(1, 2);
		assert 3 == gr.weight(1, 2);

		gr.addEdge(0, 0, 2);
		assertTrue(gr.hasEdge(0 ,0));
		assert 2 == gr.weight(0, 0);
	}


	@Test
	public void weightTest()
	{
		ListDigraph gr = new ListDigraph(3);

		try
		{
			gr.weight(0, 1);
		} catch (IllegalArgumentException e)
		{
			assertThat(e.getMessage(), is("there is no edge from 0 to 1"));
		}

		gr.addEdge(0, 1, 1);
		assert 1 == gr.weight(0, 1);

	}

	@Test
	public void numVerticesTest()
	{
		ListDigraph gr = new ListDigraph(3);
		assert 3 == gr.numVertices();

		try
		{
			gr = new ListDigraph(-4);
		} catch (AssertionError e)
		{
			assertThat(e, isA(java.lang.AssertionError.class));
		}

	}

	@Test
	public void numEdgesTest()
	{
		ListDigraph gr = new ListDigraph(7);
		gr.addEdge(0, 1, 2);
		gr.addEdge(0, 2, 1);
		gr.addEdge(0, 3, 1);
		gr.addEdge(1, 3, 3);
		gr.addEdge(1, 4, 4);
		gr.addEdge(2, 5, 5);
		gr.addEdge(3, 2, 2);
		gr.addEdge(3, 4, 2);
		gr.addEdge(3, 5, 2);
		gr.addEdge(3, 6, 8);
		gr.addEdge(4, 6, 5);
		gr.addEdge(6, 5, 1);

		assert 12 == gr.numEdges();

	}

	@Test
	public void addEdgeTest()
	{
		ListDigraph gr = new ListDigraph(7);
		gr.addEdge(0, 1, 2);
		assert 2 == gr.weight(0, 1);

		gr.addEdge(0, 1, 4);
		assert 4 == gr.weight(0, 1);
	}

	@Test
	public void outAndInTest()
	{
		ListDigraph gr = new ListDigraph(7);
		gr.addEdge(0, 1, 2);
		gr.addEdge(0, 2, 1);
		gr.addEdge(0, 3, 1);
		gr.addEdge(1, 3, 3);
		gr.addEdge(1, 4, 4);
		gr.addEdge(2, 5, 5);
		gr.addEdge(3, 2, 2);
		gr.addEdge(3, 4, 2);
		gr.addEdge(3, 5, 2);
		gr.addEdge(3, 6, 8);
		gr.addEdge(4, 6, 5);
		gr.addEdge(6, 5, 1);

		assert gr.out(0).toString().equals("[1, 2, 3]");
		assert !gr.out(0).toString().equals("[1, 2, 3, 4]");

		assert gr.in(0).toString().equals("[]");
		assert gr.in(1).toString().equals("[0]");
		assert gr.in(5).toString().equals("[2, 3, 6]");
	}

	@Test
	public void twoHopsAwayTest()
	{
		ListDigraph gr = new ListDigraph(7);
		gr.addEdge(0, 1, 2);
		gr.addEdge(0, 2, 1);
		gr.addEdge(0, 3, 1);
		gr.addEdge(1, 3, 3);
		gr.addEdge(1, 4, 4);
		gr.addEdge(2, 5, 5);
		gr.addEdge(3, 2, 2);
		gr.addEdge(3, 4, 2);
		gr.addEdge(3, 5, 2);
		gr.addEdge(3, 6, 8);
		gr.addEdge(4, 6, 5);
		gr.addEdge(6, 5, 1);

		assert "[2, 3, 4, 5, 6]".equals(gr.twoHopsAway(0).toString());

		gr.addEdge(0,0);

		assert "[0, 1, 2, 3, 4, 5, 6]".equals(gr.twoHopsAway(0).toString());
	}
}
````


##3
Problem 10.62(a): Let A be an n-by-n matrix of zeros and ones. A submatrix S of A is any group of contiguous entries that forms a square. Design an O(n^2) algorithm that determines the size of the largest submatrix of ones in A

````java
/**
 * Problem 10.62(a): Let A be an n-by-n matrix of zeros and ones. A submatrix
 * S of A is any group of contiguous entries that forms a square. Design an
 * O(n^2) algorithm that determines the size of the largest submatrix of ones
 * in A.
 */

/**
 * Created by Qiwen Zhu on 4/3/17.
 *
 * To find out the biggest subsquare, we need to keep a count of continuous ones.
 * This means, for row and column, if the count meets a zero, the count should be reset to zero;
 * 	and if the ones keep running, for row or column, we just add 1 to previous count.
 *
 * There might be multiple subsquares, and thus, multiple counts. At last, we need to find the biggest counts.
 *
 * Now the question is how / where we should keep the count / counts.
 *
 * A score bord of n * n size can be used to keep these counts.
 *
 * The idea behind this algorithm is the right-lower corner any subsquare can be used to store the count for current subsquare.
 *
 * Now, we just need to go through every element and
 * 	if an element in mat is 1, its corresponding position on the score board should be filled min(diagnol, left, down) + 1
 * 	this will ensure that only when all three neighbors are equal and not zero, the count represents the size.
 *
 * 	if an element in mat is 0, we just set its corresponding position on the score board to be 0, equivalent to reset a count.
 *
 * Above needs nested for-loop (2-layer), thus its worst running time is n^2.
 */




public class LargestSquareSubmatrix {

	/**
	 * Returns the size (i.e., side length) of the largest square of ones found
	 * within the first n rows and first n columns of mat. Assume n > 0 and
	 * n <= mat.length.
	 */
	public static int largestSubSquare(int[][] mat, int n) {
		assert n > 0 && n <= mat.length;

		int[][] scoreBoard = new int[n][n];
		for(int i = 0; i < n; i++)
		{
			scoreBoard[0][i] = mat[0][i];
			scoreBoard[i][0] = mat[i][0];
		}

		for(int i = 1; i < n; i++)
		{
			for(int j = 1; j < n; j++)
			{
				if(mat[i][j] == 1)
				{
					scoreBoard[i][j] = Math.min(Math.min(scoreBoard[i][j - 1], scoreBoard[i - 1][j]), scoreBoard[i - 1][j - 1]) + 1;
				}

				if(mat[i][j] == 0)
				{
					scoreBoard[i][j] = 0;
				}
			}
		}

//		for(int[] i : scoreBoard)
//		{
//			for(int j : i)
//			{
//				System.out.print(j + ",");
//			}
//			System.out.println();
//		}

		int max = 0;
		for(int[] i : scoreBoard)
		{
			for(int j : i)
			{
				System.out.print(j + ",");
				max = Math.max(max, j);
			}
			System.out.println();
		}

		return max;
	}

	public static void main(String... args) {
		int[][] mat = {
				{ 1, 0, 1, 1, 1, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 1, 0, 0 },
				{ 0, 0, 1, 1, 1, 0, 0, 0 },
				{ 0, 0, 1, 1, 1, 0, 1, 0 },
				{ 0, 0, 1, 1, 1, 1, 1, 1 },
				{ 0, 1, 0, 1, 1, 1, 1, 0 },
				{ 0, 1, 0, 1, 1, 1, 1, 0 },
				{ 0, 0, 0, 1, 1, 1, 1, 0 },
		};

		assert 1 == largestSubSquare(mat, 1);
		assert 1 == largestSubSquare(mat, 2);
		assert 1 == largestSubSquare(mat, 3);
		assert 2 == largestSubSquare(mat, 4);
		assert 3 == largestSubSquare(mat, 5);
		assert 3 == largestSubSquare(mat, 6);
		assert 3 == largestSubSquare(mat, 7);
		assert 4 == largestSubSquare(mat, 8);
	}
}
````

##4
You add the following 20 keys to an empty BTree in the order given using the preemptive strategy described in lec12a. Draw a picture of the tree immediately after inserting each of the underlined keys.

12, 11, 1, 5, 3, 18, 13, 6, 20, 17, 7, 8, 9, 10, 2, 14, 15, 16, 19, 4

##4.a
Assume the bucket size, b, is 4. Note that the middle key in a full bucket is k1 and, following a split, k0 will be in the left sibling and k2, k3 will be in the right sibling. So that you can check your work, there are 3 nodes in the first tree you draw, 4 nodes in the second tree, 5 nodes in the third tree, and 8 nodes in the fourth tree.

````
after inserting 3
           
               (5)
             /     \           
          (1,3)   (11,12)
           
after inserting 17

        (5,     12)
    /        |      \
(1, 3)      (6,11)   (13, 17, 18, 20)

after inserting 2

         (5,        7,    12)
       /     |         |     \
  (1,2,3)   (6)  (8,9,10,11) (13,17,18,20)
  
after inserting 4

               (7)
         /                \
       (5)            (12,           17)   
     /    \        /            |          \
(1,2,3,4)  (6) (8,9,10,11) (13,14,15,16) (18,19,20)

````

##4.b
Repeat the insertions on an empty BTree, but assume the bucket size, b, is 3. There are 3 nodes in the first tree, 7 nodes in the second tree, 10 nodes in the third, and 15 in the fourth.
````
 after inserting 3
 
 
              (11)
            /      \
         (1,3,5)   (12)
 
 after inserting 17
 
              (11)
          /          \
        (3)         (13)  
      /   \         /    \
    (1)  (5,6)   (12)   (17,18,20)
     
 after inserting 2
 
                  (6,         11)
               /         |           \
             (3)        (8)          (13)
           /   \      /    \        /     \
       (1,2)  (5)   (7)  (9,10)   (12)    (17,18,20)

 after inserting 4
   
                          (11)
                   /                \
                 (6)                (15)
              /      \             /      \
           (3)      (8)          (13)     (18)
         /   \     /    \       /   \     /    \
     (1,2) (4,5) (7)  (9,10) (12)  (14)(16,17) (19,20)
 
````