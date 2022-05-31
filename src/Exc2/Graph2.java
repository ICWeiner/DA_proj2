package Exc2;

import java.util.*;

import static java.util.Collections.reverse;

public class Graph2 {
  /** Number of nodes in the graph */
  int n;
  /** Number of relationships in the graph */
  int e;
  /** Vector of vectors of adjacencies */
  Vector<Vector<Integer>> adj;
  /** Matrix of capacities */
  int[][] cap;
  /** Matrix of time */
  int[][] time;
  /** List of lists with paths */
  List<List<Integer>> paths;
  /** List of flux */
  List<Integer> flux;
  /** List of Edges containing time */
  Set<Edge> CPMedges;
  
  /** @Constructor of Graph
   * @for to initialize all vectors for n nodes
   * @cap is initialized with n+1 since nodes start in 1 instead of 0
   * @time is initialized with n+1 since nodes start in 1 instead of 0  */
  public Graph2(int n, int e) {
    this.n = n;
    this.e = e;
    adj = new Vector<>();
    for (int i = 0; i <= n; i++) adj.add(new Vector<>());
    cap = new int[n + 1][n + 1]; // +1 se os nos comecam em 1 ao inves de 0
    time = new int[n + 1][n + 1]; // +1 se os nos comecam em 1 ao inves de 0
  }
  
  /** adjacencies of the undirected graph, because we may have to walk in the opposite <br>
   * direction while looking for augmentation paths */
  public void addLink(int a, int b, int c, int d) {
    adj.get(a).add(b);
    adj.get(b).add(a);
    cap[a][b] = c;
    time[a][b] = d;
  }
  
  /** @function BFS to find augmentation path returns flow value in that path */
  int bfs(int s, int t, int[] parent) {
    for (int i = 1; i <= n; i++) parent[i] = -1;
    
    parent[s] = -2;
    Queue<NodeQ> q = new LinkedList<>(); // BFS queue
    // boot with source node and infinite capacity
    q.add(new NodeQ(s, Integer.MAX_VALUE));
    
    while (!q.isEmpty()) {
      // return first in queue
      int cur = q.peek().node;
      int flow = q.peek().flow;
      q.poll();
      
      // loop through nodes adjacent to the current node (cur)
      for (int next : adj.get(cur)) {
        // if the neighbor has not yet been visited (parent==-1)
        // the respective edge still has the capacity to pass flow
        if (parent[next] == -1 && cap[cur][next] > 0) {
          parent[next] = cur;                               // update father
          int new_flow = Math.min(flow, cap[cur][next]);    // update flux
          if (next == t) return new_flow;                   // have we arrived the end ?
          q.add(new NodeQ(next, new_flow));                 // add to queue
        }
      }
    }
    return 0;
  }
  
  /** Edmonds-Karp algorithm for maximum flow between s and t that returns maximum flow value (cap[][] gets residual graph) */
  public void maxFlow(int s, int t, int groupSize) {
    int flow = 0, i = 0;                // flux to be calculated
    int[] parent = new int[n + 1];      // parent array (allows rebuild path)
    paths = new LinkedList<>();
    flux = new LinkedList<>();
    CPMedges = new TreeSet<>();
    
    while (true) {
      int new_flow = bfs(s, t, parent);     // flow of an increase path
      if (new_flow == 0) {                  // if not, terminate Edmonds-Karp algorithm
        if (flow < groupSize) {
          System.out.println("Impossible to travel as a group");
          break;
        }
        System.out.println("Flow for the maximum group: " + flow);
        maxPath();
        System.out.println("Flow for the group of " + groupSize + ":");
        groupPath(groupSize);
        timeFLux();
        break;
      }
      
      paths.add(new LinkedList<>());
      flux.add(new_flow);
      flow += new_flow;               // increase total flow with flow this way
      int cur = t;
      while (cur != s) {              // traverse augmentation path and change edges
        paths.get(i).add(cur);
        int prev = parent[cur];
        cap[prev][cur] -= new_flow;
        cap[cur][prev] += new_flow;
        cur = prev;
      }
      i++;
    }
  }
  
  /** @function that determines all paths for max group  */
  private void maxPath() {                  // Exercise 2.3
    int i=0;
    for (List<Integer> path : paths) {
      path.add(1);
      reverse(path);
      System.out.print("Flow on this path is " + flux.get(i) + ":");
      for (int a = 0; a < (path.size() - 1); a++)
        System.out.print(path.get(a) + " -> ");
      System.out.println(path.get(path.size() - 1));
      i++;
    }
  }
  
  /** @function that determines number of needed paths for the group amount specified  */
  private void groupPath(int size) {      // Exercise 2.1
    int max = -1, maxInt = -1, s = 0;            // max is the index of the list and maxInt is the value at that position
    while(true){
      for(int i=0; i<flux.size() ;i++)
        if(flux.get(i) > maxInt){
          max = i;
          maxInt = flux.get(i);
        }

      System.out.print("Flow on this path is " + maxInt + ":");
      for (int a=0; a<(paths.get(max).size()-1) ;a++)
        System.out.print(paths.get(max).get(a) + " -> ");
      System.out.println(paths.get(max).get(paths.get(max).size()-1));
      
      while(s < paths.get(max).size()-1){
        Edge e = new Edge(paths.get(max).get(s), paths.get(max).get(s+1), time[paths.get(max).get(s)][paths.get(max).get(s+1)]);
        CPMedges.add(e);
        s++;
      }

      if(size - maxInt <= 0) break;
      size -= maxInt;
      flux.remove(max);
      paths.remove(max);
      max = 0; maxInt = 0; s = 0;
    }
  }
  
  private void timeFLux(){
    //CPMedges.
    System.out.println("Time flux:" + CPMedges.size());
    for (Edge e : CPMedges)
      System.out.println(e.start + ", " + e.to + " tempo:" + e.time);
  }
}
