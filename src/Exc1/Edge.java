package Exc1;

// Classe que representa uma aresta
class Edge {
    int to;     // No destino
    int capacity; // capacidade de transporte
    int time;  //tempo

    Edge(int t, int c,int time) {
        to = t;
        capacity = c;
        this.time = time;
    }
}
