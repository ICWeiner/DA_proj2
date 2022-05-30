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
    
    /**  */
    public void addLink(int a, int b, int c, int d) {
        nodes[a].adj.add(new Edge(b, c, d));
    }


    
    // Algoritmo de Dijkstra-ish for max flow path
    public void maxPath(int s) {//this whole thing is sussy, im still not 100% how it works
        //Inicializar nos como nao visitados e com distancia infinita
        for (int i=1; i<=n; i++) {
            nodes[i].visited  = false;
        }

        //nodes[s].distance = Integer.MAX_VALUE; //integer max probably what was the point of this? oh well
        TreeSet<NodeQ> q = new TreeSet<>();
        q.add(new NodeQ(0, s)); // Criar um par (dist=0, no=s)


        int[] caps = new int[n + 1]; // array que contem a capacidade de cada no
        int[] pai = new int[ n +1]; // array que contem o pai de cada no
        for (int i = 2 ; i < caps.length ; i++) caps[i] = Integer.MIN_VALUE;
        caps[1] = Integer.MAX_VALUE;

        while (!q.isEmpty()) {
            // Retirar no com menor distancia (o "primeiro" do set, que e uma BST)
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
        System.out.println("Caminho maximo encontrado, capacidade = "  + caps[caps.length - 1 ]);
        
        List<Integer> print = new LinkedList<>();
        for (int i = n; i > 1;){//imprimir caminho
            print.add(i);
            i = pai[i];
        }
        reverse(print);
        System.out.print(1 + " -> ");
        for (int a=0; a<(print.size()-1) ;a++)
            System.out.print(print.get(a) + " -> ");
        System.out.println(print.get(print.size()-1));
    }

    public void dijkstra(int s) {
        //Inicializar nos como nao visitados e com distancia infinita
        for (int i = 1; i <= n; i++) {
            nodes[i].distance = Integer.MAX_VALUE;
            nodes[i].visited = false;
        }

        int[] pai = new int[ n +1];

        // Inicializar "fila" com no origem
        nodes[s].distance = 0;
        TreeSet<NodeQ> q = new TreeSet<>();
        q.add(new NodeQ(0, s)); // Criar um par (dist=0, no=s)

        // Ciclo principal do Dijkstra
        while (!q.isEmpty()) {

            // Retirar no com menor distancia (o "primeiro" do set, que e uma BST)
            NodeQ nq = q.pollFirst();
            int u = nq.node;
            nodes[u].visited = true;

            for (Edge e : nodes[u].adj) {
                int v = e.to;
                //int cost = e.capacity;
                if (!nodes[v].visited && nodes[u].distance + 1 < nodes[v].distance) {
                    q.remove(new NodeQ(nodes[v].distance, v)); // Apagar do set
                    nodes[v].distance = nodes[u].distance + 1;
                    q.add(new NodeQ(nodes[v].distance, v));    // Inserir com nova (e menor) distancia
                    pai[v] = u;
                }
            }
        }

        System.out.println("Caminho mais rapido encontrado, distancia = "  + nodes[n].distance);
    
        List<Integer> print = new LinkedList<>();
        for (int i = n; i > 1;){//imprimir caminho
            print.add(i);
            i = pai[i];
        }
        reverse(print);
        System.out.print(1 + " -> ");
        for (int a=0; a<(print.size()-1) ;a++)
            System.out.print(print.get(a) + " -> ");
        System.out.println(print.get(print.size()-1));
    }

    /*public void bfs(Node v) {
        LinkedList<Node> q = new LinkedList<Node>();
        for (int i = 1; i <= n; i++) nodes[i].visited = false;

        q.add(v);
        v.visited = true;
        v.distance = 0;

        while (q.size() > 0) {
            Node u = q.removeFirst();
            System.out.println(u + " [dist=" + u.distance + "]");
            for (Exc1.Edge w : u.adj)
                if (!nodes[w].visited) {
                    q.add(w);
                    nodes[w].visited = true;
                    nodes[w].distance = nodes[u].distance + 1;
                }
            }
        }

    void findMaxPath(){
        //LinkedList<Integer> path = new LinkedList<>();
        int flow[][] = new int[n + 1][n +1];

        for(int i = 0; i < n; i++)
            flow[0][n] = Integer.MAX_VALUE;

        for(int i = 1; i < nodes.length; i++){
            for(Exc1.Edge edge : nodes[i].adj){
                System.out.println(edge.capacity + " " + nodes[i].flow);
                if(edge.capacity > nodes[i].flow)  flow[i][edge.to] = edge.capacity;
                flow[i][edge.to] = nodes[i].flow;
            }
        }


        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= n; j++){
                System.out.print(" " + flow[i][j] + " ");
            }
            System.out.println();
        }
    }

    void fordFulkerson(){

    }

    void floydWarshall(){
        int dist[][] = new int[n + 1][n + 1];

        for(int i = 1; i <=n; i++){
            dist[i][i] = 0;
        }

        for(int i = 1; i <= nodes.length; i++){
            for(Exc1.Edge edge: nodes[i - 1].adj){
                //dist[]
            }
        }


    }
    */

}