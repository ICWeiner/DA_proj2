package Exc2;

import java.util.*;

import static java.util.Collections.reverse;

public class Graph2 {
  int n; /** Number of nodes in the graph */

  int e; /** Number of edges in the graph */

  Vector<Vector<Integer>> adj; /** Vector of vectors of adjacencies */

  int[][] cap; /** Matrix of capacities */

  int[][] time; /** Matrix of time */

  List<List<Integer>> paths; /** List of lists with paths */

  List<Integer> flux; /** List of flux */

  Set<Edge> CPMedges; /** List of Edges used in a given path*/
  
  /** @Constructor of Graph
   * @for to initialize all vectors for n nodes
   * @cap is initialized with n+1 since nodes start in 1 instead of 0
   * @time is initialized with n+1 since nodes start in 1 instead of 0  */
  public Graph2(int n, int e) {
    this.n = n;
    this.e = e;
    adj = new Vector<>();
    for (int i = 0; i <= n; i++) adj.add(new Vector<>());
    cap = new int[n + 1][n + 1];
    time = new int[n + 1][n + 1];
  }
  
  /** adjacency of node a to node b while also adding the capacity and time of the path */
  public void addLink(int a, int b, int c, int d) {
    adj.get(a).add(b);
    adj.get(b).add(a);
    cap[a][b] = c;
    time[a][b] = d;
  }
  
  /** @function BFS to find augmentation path returns flow value in that path
   * @Queue queue to be filled with nodes, initial insertion is with the starting node set to the max capacity
   * @while removes the first node from the queue and loops through the adjacent nodes only if the node hasn't been visited and the respective edge still has capacity to pass flow
   * @if when it enters the if clause it updates the father also updates the flux and checks if it has reached the end, if it hasn't then it adds the node to queue <br>
   * if it has reached the end return the new flow */
  int bfs(int s, int t, int[] parent) {
    for (int i = 1; i <= n; i++) parent[i] = -1;
    
    parent[s] = -2;
    List<NodeQ> q = new LinkedList<>();
    q.add(new NodeQ(s, Integer.MAX_VALUE));
    
    while (!q.isEmpty()) {
      int cur = q.get(0).node;
      int flow =  q.get(0).flow;
      q.remove(0);
      
      for (int next : adj.get(cur)) {
        if (parent[next] == -1 && cap[cur][next] > 0) {
          parent[next] = cur;
          int new_flow = Math.min(flow, cap[cur][next]);
          if (next == t) return new_flow;
          q.add(new NodeQ(next, new_flow));
        }
      }
    }
    return 0;
  }
  
  /** @function Edmonds-Karp algorithm for maximum flow between s and t that returns maximum flow value (cap[][] gets residual graph)
   * @variables flow is the flow to be determined and array parent to rebuild the path also initialize the two different lists also the TreeSet
   * @flow is determined by calling the function bfs <br>
   * when the flow is returned from the bfs function we create a new path inside the list of lists <br>, and we add the flux to the flux list
   * then traverse the node cur until reaching the s node adding to the capacity array from prev to cur
   * the new flow and subtracting the flow from cur to prev creating the residual flow <br>
   * when the new flow is zero the while breaks and all the paths are sent to the different functions to create the different solutions for various exercises*/
  public void maxFlow( int t, int c, int groupSize) {
    int s = 1;
    int flow = 0, i = 0;
    int[] parent = new int[n + 1];
    paths = new LinkedList<>();
    flux = new LinkedList<>();
    CPMedges = new TreeSet<>();
    
    while (true) {
      int new_flow = bfs(s, t, parent);
      if (new_flow == 0) {
  
        switch (c) {
          case 1 -> {
            System.out.println("\nFlow for the group of " + groupSize + ":");
            groupPath(groupSize);
          }
          case 2 -> {
            System.out.println("How many people showed up now ?");
            Scanner reader = new Scanner(System.in);
            int addOn = reader.nextInt();
            System.out.println("\nFlow for the group of " + groupSize + addOn + ":");
            groupPath(groupSize + addOn);
          }
          case 3 -> {
            System.out.println("\nFlow for the maximum group: " + flow);
            maxPath();
          }
          default -> timeFLux(c);
        }
        break;
      }
      
      paths.add(new LinkedList<>());
      flux.add(new_flow);
      flow += new_flow;
      int cur = t;
      while (cur != s) {
        paths.get(i).add(cur);
        int prev = parent[cur];
        cap[prev][cur] -= new_flow;
        cap[cur][prev] += new_flow;
        cur = prev;
      }
      i++;
    }
  }
  
  /** @function that prints all paths for the max flow of a given graph */
  private void maxPath() {
    int i=0;
    for (List<Integer> path : paths) {
      path.add(1);
      reverse(path);
      System.out.print("Flow on this path is " + flux.get(i) + " : ");
      for (int a = 0; a < (path.size() - 1); a++)
        System.out.print(path.get(a) + " -> ");
      System.out.println(path.get(path.size() - 1));// print like this so we dont get an arrow out of the last node(visual only)
      i++;
    }
  }
  
  /** @function that determines the needed paths for a given group size
   * @variables max is the index of the list and maxInt is the value of flux at that position, size corresponds to the size of the group
   * @while inside the while the for looks for the max flow inside the list of flows when determined
   * it puts the i value that corresponds to the list inside the paths list then prints the list, the while breaks
   * when the size of the group minus the maxInt is equal or less the 0 <br>
   * inside this function we also add all the edges of the path to a list for use in critical path method  */
  private void groupPath(int size) {
    int max = -1, maxInt = -1, s = 0;
    while(true){
      for(int i=0; i<flux.size() ;i++)
        if(flux.get(i) > maxInt){
          max = i;
          maxInt = flux.get(i);
        }

      System.out.print("Flow on this path is " + maxInt + " : ");
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

  /** @function calls other functions that use CPM to compute the earliest finish time of the trip and free time on any node
   * */
  private void timeFLux(int c){
    int[] earliestStart = EarliestFlux();
    if(c==4)
      System.out.println("\nEarliest finish time is:" + earliestStart[n] );
    else
      latestFlux(earliestStart);
  }

  /** @function that computes the earliest start for each node
   * @return array with n+1 positions containing the earliest start time for each i-node*/
  private int[] EarliestFlux(){
    int[] ES = new int[n + 1];
    //int[] prev = new int[n + 1]; i dont think we need this
    int[] entries = new int[n + 1]; // amount of edges entering each node
    Stack<Integer> s = new Stack<>(); // stack containing nodes that have no entries left
    int minDur = -1; //this var will hold the earliest finish time of the entire trip

    for (Edge e : CPMedges){
      entries[e.to] = entries[e.to] + 1;//compute amount of edges entering each node
    }

    /*for (Edge e : CPMedges){ i dont think we need this because of the way were doing things
      if (entries[e.start] == 0 && (!s.contains(e.start))) s.push(e.start);//compute amount of edges entering each node
    }*/
    s.push(1);//put the starting node in the stack since it should be the only one with no edges entering

    while (!s.isEmpty()){
      int currNode = s.pop();

      if (minDur < ES[currNode]) minDur = ES[currNode];

      for (Edge e : CPMedges){
        if(e.start == currNode){
          if (ES[e.to] < ES[currNode] + e.time){
            ES[e.to] = ES[currNode] + e.time;
            //prev[e.to] = currNode;
          }
          entries[e.to] = entries[e.to] - 1;
          if(entries[e.to] == 0 ){
            s.push(e.to);
          }
        }
      }
    }

    ES[n] = minDur;

    return ES;
  }

  /** @function that computes the latest finish for each node then prints if there is any free time on a node and the total free time
   * */
  private void latestFlux(int[] ES){
    int[] LF = new int[n + 1]; //array with n+1 positions containing the latest finish time for each i-node
    int[] exits = new int[n + 1]; // amount of edges exiting each node
    Set<Edge> CPMedgesReverse = new TreeSet<>(); // reverse the order of the edges to mimic a transposed graph
    Stack<Integer> s = new Stack<>();
    int totalWait = 0;

    for(int i = 1; i < LF.length; i++) LF[i] = ES[n]; //set default LF value as the total trip time


    for (Edge e : CPMedges){ //compute amount of exits per node and build reverse edge list
      exits[e.start] = exits[e.start] + 1;
      CPMedgesReverse.add(new Edge(e.to , e.start,e.time));
    }

    for (Edge e : CPMedgesReverse){
      if (exits[e.start] == 0 && (!s.contains(e.start))) s.push(e.start); // check for all nodes that have no edges exiting from them and put them in stack
      //System.out.println(e.start + "," + e.to);
    }

    while (!s.isEmpty()) {
      int currNode = s.pop();

      for (Edge e : CPMedgesReverse){

        if(e.start == currNode){
          if (LF[e.to] > LF[currNode] - e.time) LF[e.to] = LF[currNode] - e.time; // compute latest finish for each node

          exits[e.to] = exits[e.to] - 1;

          if(exits[e.to] == 0 ) s.push(e.to);// check if current destiny can be added to compute stack
        }
      }
    }

    System.out.println();
    for(int i = 1 ; i < LF.length; i++){
      if(ES[i]<LF[i] && ES[i]!=0){
        int currWait = LF[i] - ES[i];
        System.out.println("Free time:" + currWait + " on node : " + i);
        totalWait+=currWait;
      }
    }
    System.out.println("\nTotal free time : " + totalWait);
  }

}
