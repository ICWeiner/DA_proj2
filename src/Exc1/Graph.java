package Exc1;

import java.util.*;
import static java.util.Collections.reverse;
/** Class that represents a graph */
public class Graph {
    /** Number of nodes in graph */
    int n;
    /** Array to hold all the nodes */
    Node[] nodes;
    
    /** @Constructor of Graph
     * @nodes array is initialized with n + 1 nodes */
    public Graph(int n) {
        this.n = n;
        nodes = new Node[n+1];  // +1 se os nos comecam em 1 ao inves de 0
        for (int i=1; i<=n; i++)
            nodes[i] = new Node();
    }
    
    /** adjacency of node a to node b by creating an edge while also adding the capacity and time of the path */
    public void addLink(int a, int b, int c, int d) {
        nodes[a].adj.add(new Edge(b, c, d));
    }

    /** Dijkstra's algorithm slightly modified to find max flow path <br>
     * first we initialize all nodes to unvisited <br>
     * then create a TreeSet and add the first node also initialize caps array that
     * contains capacity of each node and pai array that contains the father of the corresponding capacity of each node <br>
     * then in the while we look for the lowest distance node and look for its edges <br>
     * we look to see if capacity between the node and edge is smaller than the capacity already attributed to the node if it is
     * we insert the new node in the q with that capacity and do it until the queue is finished. <br>
     * when its finished we have found the biggest path. */
    public void maxPath() {
        int s = 1;
        for (int i=1; i<=n; i++) {
            nodes[i].visited  = false;
        }

        TreeSet<NodeQ> q = new TreeSet<>();
        q.add(new NodeQ(0, s));

        int[] caps = new int[n + 1];/**Array that contains the capacity of each node*/
        int[] pai = new int[ n +1]; /**Array that contains the "father" of each node*/
        caps[1] = Integer.MAX_VALUE;

        while (!q.isEmpty()) { //modified Dijkstra's algorithm to find max path
            NodeQ nq = q.pollFirst();
            int  v = nq.node;
            nodes[v].visited = true;

            for (Edge e : nodes[v].adj) {
                int w = e.to;

                if( Integer.min(caps[v],e.capacity) > caps[w]){
                    caps[w] = Integer.min(caps[v],e.capacity) ;
                    pai[w] = v;
                    q.add(new NodeQ(caps[w],w));
                }
            }
        }
        System.out.println("Maximum path found, capacity = "  + caps[caps.length - 1 ]);
        
        List<Integer> print = new LinkedList<>();
        for (int i = n; i > 1;){//add the father of each node to a list, in reverse order
            print.add(i);
            i = pai[i];
        }
        reverse(print);// reverse the order to get the actual path take
        System.out.print(1 + " -> ");
        for (int a=0; a<(print.size()-1) ;a++)
            System.out.print(print.get(a) + " -> ");
        System.out.println(print.get(print.size()-1));
    }

    /** Dijkstra's algorithm to find the shortest path form start to end. <br>
     * first we initialize all nodes as unvisited and with the highest possible max distance <br>
     * then make the first node distance to 0 alse create a TreeSet and add the first node also initialize
     * pai array that contains the father of the corresponding  node <br>
     * then in the while we look for the lowest distance node and look for its edges <br>
     * we look to see if the distance between the node and edge is smaller if it is
     * we remove the node from the set then insert it with the new shorter distance and do it until the queue is finished. <br>
     * when its finished we have found the shortest path. */
    public void dijkstra() {
        int s = 1;
        for (int i = 1; i <= n; i++) {
            nodes[i].distance = Integer.MAX_VALUE;
            nodes[i].visited = false;
        }

        int[] pai = new int[ n +1];

        nodes[s].distance = 0;
        TreeSet<NodeQ> q = new TreeSet<>();
        q.add(new NodeQ(0, s));
        
        while (!q.isEmpty()) {

            NodeQ nq = q.pollFirst();
            int u = nq.node;
            nodes[u].visited = true;

            for (Edge e : nodes[u].adj) {
                int v = e.to;
                if (!nodes[v].visited && nodes[u].distance + 1 < nodes[v].distance) {
                    q.remove(new NodeQ(nodes[v].distance, v));
                    nodes[v].distance = nodes[u].distance + 1;
                    q.add(new NodeQ(nodes[v].distance, v));
                    pai[v] = u;
                }
            }
        }

        System.out.println("Shortest path found, distance = "  + nodes[n].distance);
    
        List<Integer> print = new LinkedList<>();
        for (int i = n; i > 1;){//Same as is written in maxPath()
            print.add(i);
            i = pai[i];
        }
        reverse(print);
        System.out.print(1 + " -> ");
        for (int a=0; a<(print.size()-1) ;a++)
            System.out.print(print.get(a) + " -> ");
        System.out.println(print.get(print.size()-1));
    }
}