import java.util.LinkedList;

// Classe que representa um no
class Node {
    public LinkedList<Edge> adj; // Lista de adjacencias
    public boolean visited;      // No ja foi visitado?
    public int capacity;         // Distancia ao no origem da pesquisa

    Node() {
        adj = new LinkedList<>();
    }
};