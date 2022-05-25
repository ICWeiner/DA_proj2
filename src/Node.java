import java.util.LinkedList;

// Classe que representa um no
class Node implements Comparable<Node> {
    public LinkedList<Edge> adj; // Lista de adjacencias
    public int capacity;         // Distancia ao no origem da pesquisa

    Node() {
        adj = new LinkedList<>();
    }

    @Override
    public int compareTo(Node nq) {
        return Integer.compare(capacity, nq.capacity);
    }
};