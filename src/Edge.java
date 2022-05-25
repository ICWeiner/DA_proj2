// Classe que representa uma aresta
class Edge {
    int to;     // No destino
    int capacity; // capacidade de transporte
    int time;
    
    Edge(int t, int c,int time) {
        to = t;
        capacity = c;
        this.time = time;
    }
}
