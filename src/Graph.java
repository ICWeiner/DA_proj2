import java.util.Set;
import java.util.TreeSet;

// Classe que representa um grafo
public class Graph {
    int n;          // Numero de nos do grafo
    Node[] nodes;   // Array para conter os nos
    
    Graph(int n) {
        this.n = n;
        nodes = new Node[n+1];  // +1 se os nos comecam em 1 ao inves de 0
        for (int i=1; i<=n; i++)
            nodes[i] = new Node();
    }
    
    void addLink(int a, int b, int c,int d) {
        nodes[a].adj.add(new Edge(b, c, d));
    }
    
    // Algoritmo de Dijkstra
    void dijkstra(int s) {
        for (int i=1; i<=n; i++) {
            nodes[i].capacity = Integer.MIN_VALUE;
        }
        // Inicializar "fila" com no origem
        nodes[s].capacity = Integer.MAX_VALUE;
        TreeSet<NodeQ> q = new TreeSet<>();
        q.add(new NodeQ(0,s)); // Criar um par (dist=0, no=s)
        for(int i=1; i<=n ;i++) {
            if(i == s) continue;
            q.add(new NodeQ(nodes[i].capacity, i));
        }
        
        // Ciclo principal do Dijkstra
        while (!q.isEmpty()) {
            NodeQ nq = q.pollLast();
            int u = nq.node;
            
            if(nodes[u].capacity == Integer.MIN_VALUE){
                break;
            }
            
            System.out.println(u + " [capacity=" + nodes[u].capacity + "]");
            
            // Relaxar arestas do no retirado
            for (Edge e : nodes[u].adj) {
                int v = e.to;
                int aux = Math.min(nodes[u].capacity, nodes[v].capacity );
                
                if(aux > nodes[v].capacity){
                    q.remove(new NodeQ(nodes[v].capacity, v));
                    nodes[v].capacity = aux;
                    q.add(new NodeQ(nodes[v].capacity, v));
                }
            }
        }
    }
};