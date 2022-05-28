package Exc2;

import java.util.*;

public class Graph2 {
  int n;                       // Numero de nos do grafo
  int e;                       // Numero de relaçoes no grafo
  Vector<Vector<Integer>> adj; // Lista de adjacencias
  int[][] cap;                 // Matriz de capacidades
  int[][] time;                // Matriz de tempo
  
  public Graph2(int n, int e) {
    this.n = n;
    this.e = e;
    adj = new Vector<>();
    for (int i=0; i<=n; i++) adj.add(new Vector<>());
    cap = new int[n+1][n+1]; // +1 se os nos comecam em 1 ao inves de 0
    time = new int[n+1][n+1]; // +1 se os nos comecam em 1 ao inves de 0
  }
  
  public void addLink(int a, int b, int c, int d) {
    // adjacencias do grafo nao dirigido, porque podemos ter de andar no sentido
    // contrario ao procurarmos caminhos de aumento
    adj.get(a).add(b);
    adj.get(b).add(a);
    cap[a][b] = c;
    time[a][b] = d;
  }
  
  // BFS para encontrar caminho de aumento
  // devolve valor do fluxo nesse caminho
  int bfs(int s, int t, int[] parent) {
    for (int i=1; i<=n; i++) parent[i] = -1;
    
    parent[s] = -2;
    Queue<NodeQ> q = new LinkedList<>(); // fila do BFS
    // inicializar com no origem e capacidade infinita
    q.add(new NodeQ(s, Integer.MAX_VALUE));
    
    while (!q.isEmpty()) {
      // returar primeiro no da fila
      int cur = q.peek().node;
      int flow = q.peek().flow;
      q.poll();
      
      // percorrer nos adjacentes ao no atual (cur)
      for (int next : adj.get(cur)) {
        // se o vizinho ainda nao foi visitado (parent==-1)
        // e a aresta respetiva ainda tem capacidade para passar fluxo
        if (parent[next] == -1 && cap[cur][next]>0) {
          parent[next] = cur;                        // atualizar pai
          int new_flow = Math.min(flow, cap[cur][next]); // atualizar fluxo
          if (next == t) return new_flow;            // chegamos ao final?
          q.add(new NodeQ(next, new_flow));          // adicionar a fila
        }
      }
    }
    return 0;
  }
  
  // Algoritmo de Edmonds-Karp para fluxo maximo entre s e t
  // devolve valor do fluxo maximo (cap[][] fica com grafo residual)
  public void maxFlow(int s, int t, int groupSize) {
    int flow = 0;                // fluxo a calcular
    int[] parent = new int[n+1]; // array de pais (permite reconstruir caminho)
    
    while (true) {
      int new_flow = bfs(s, t, parent); // fluxo de um caminho de aumento
      if (new_flow == 0) {
        Path(s,t,groupSize);
        break;
      }         // se nao existir, terminar
      
      // imprimir fluxo e caminho de aumento
      System.out.print("Caminho de aumento: fluxo " + new_flow + " | " + t);
      
      flow += new_flow;  // aumentar fluxo total com fluxo deste caminho
      int cur = t;
      while (cur != s) { // percorrer caminho de aumento e alterar arestas
        int prev = parent[cur];
        cap[prev][cur] -= new_flow;
        cap[cur][prev] += new_flow;
        cur = prev;
        System.out.print(" <- " + cur); // imprimir proximo no do caminho
      }
      System.out.println();
    }
    
    //TODO quando o código chega aqui a matriz cap contem o grafo residual final só falta o código que percorra o caminho de tras para a frente
    System.out.println("Fluxo maximo: " + flow);
  }
  
  private void Path(int s, int t, int size) {
    int[] path = new int[e];
    
  }
}
