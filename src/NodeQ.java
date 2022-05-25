// Classe que representa um no para ficar na fila de prioridade
class NodeQ implements Comparable<NodeQ> {
    public int capacity;
    public int node;

    NodeQ(int c, int n) {
        capacity = c;
        node = n;
    }

    @Override
    public int compareTo(NodeQ nq) {
        if (capacity < nq.capacity) return -1;
        if (capacity > nq.capacity) return +1;
        if (node < nq.node) return -1;
        if (node > nq.node) return +1;
        return 0;
    }
}