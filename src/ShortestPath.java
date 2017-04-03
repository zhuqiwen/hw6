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
                                        Map<Integer, Integer> cache) {
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
//    assert 5 == shortestPath(0, 3, gr);
    assert 6 == shortestPath(4, 2, gr);
    assert Integer.MAX_VALUE == shortestPath(2, 4, gr);
    System.out.println(gr);
    
    /**
     * TODO
     * 
     * Create the graph shown in the hw, find the shortest path from 0 to 6,
     * and print the cache as you go.
     */
    
  }  
}
