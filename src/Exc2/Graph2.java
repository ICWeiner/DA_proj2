package Exc2;

import java.util.*;

import static java.util.Collections.reverse;

public class Graph2 {
  int n;                       // Numero de nos do grafo
  int e;                       // Numero de relaçoes no grafo
  Vector<Vector<Integer>> adj; // Lista de adjacencias
  int[][] cap;                 // Matriz de capacidades
  int[][] time;                // Matriz de tempo
  List<List<Integer>> paths;
  List<Integer> flux;
  
  public Graph2(int n, int e) {
    this.n = n;
    this.e = e;
    adj = new Vector<>();
    for (int i = 0; i <= n; i++) adj.add(new Vector<>());
    cap = new int[n + 1][n + 1]; // +1 se os nos comecam em 1 ao inves de 0
    time = new int[n + 1][n + 1]; // +1 se os nos comecam em 1 ao inves de 0
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
    for (int i = 1; i <= n; i++) parent[i] = -1;
    
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
        if (parent[next] == -1 && cap[cur][next] > 0) {
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
    int flow = 0, i = 0;                // fluxo a calcular
    int[] parent = new int[n + 1]; // array de pais (permite reconstruir caminho)
    paths = new LinkedList<>();
    flux = new LinkedList<>();
    
    while (true) {
      int new_flow = bfs(s, t, parent); // fluxo de um caminho de aumento
      if (new_flow == 0) { // se nao existir, terminar algoritmo de Edmonds-Karp
        if (flow < groupSize) {
          System.out.println("Impossible to travel as a group");
          break;
        }
        System.out.println("Fluxo para o grupo maximo: " + flow);
        maxPath();
        System.out.println("Fluxo para o grupo de " + groupSize + ":");
        groupPath(groupSize);
        break;
      }
      
      paths.add(new LinkedList<>());
      flux.add(new_flow);
      flow += new_flow;  // aumentar fluxo total com fluxo deste caminho
      int cur = t;
      while (cur != s) { // percorrer caminho de aumento e alterar arestas
        paths.get(i).add(cur);
        int prev = parent[cur];
        cap[prev][cur] -= new_flow;
        cap[cur][prev] += new_flow;
        cur = prev;
      }
      i++;
    }
  }
  
  private void maxPath() {                  // Exercício 2.3
    int i=0;
    for (List<Integer> path : paths) {
      reverse(path);
      System.out.print("Fluxo neste caminho é " + flux.get(i) + ": 1 -> ");
      for (int a = 0; a < (path.size() - 1); a++)
        System.out.print(path.get(a) + " -> ");
      System.out.println(path.get(path.size() - 1));
      i++;
    }
  }
  
  private void groupPath(int size) {      // Exercício 2.1
    int max = 0, maxInt = 0;  // max é o index da lista e maxInt é o valor nessa posição
    while(true){
      for(int i=0; i<flux.size() ;i++)
        if(flux.get(i) > maxInt){
          max = i;
          maxInt = flux.get(i);
        }
        
      reverse(paths.get(max));
      System.out.print("Fluxo neste caminho é " + maxInt + ": 1 -> ");
      for (int a=0; a<(paths.get(max).size()-1) ;a++)
        System.out.print(paths.get(max).get(a) + " -> ");
      System.out.println(paths.get(max).get(paths.get(max).size()-1));
      if(size - maxInt <= 0) break;
      size -= maxInt;
      flux.remove(max);
      paths.remove(max);
      max = 0; maxInt = 0;
    }
  }
}
