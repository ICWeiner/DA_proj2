package Exc1;

/** class that represents a node and implements the Comparable interface
 * used in the priority queue for dijkstra's algorithm*/
class NodeQ implements Comparable<NodeQ> {
    /**Current capacity of node */
    public int capacity;
    /** Number of node this comparable object represents*/
    public int node;

    /**Constructor sets capacity and node */
    NodeQ(int c, int n) {
        capacity = c;
        node = n;
    }
    
    /** function to compare edges in order of capacity of nodes and then node number */
    @Override
    public int compareTo(NodeQ nq) {
        if (capacity < nq.capacity) return -1;
        if (capacity > nq.capacity) return +1;
        if (node < nq.node) return -1;
        if (node > nq.node) return +1;
        return 0;
    }
}