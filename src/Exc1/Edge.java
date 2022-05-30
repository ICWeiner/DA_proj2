package Exc1;

/** Class that represents an edge */
class Edge {
    /** Node destiny */
    int to;
    /** transport capacity */
    int capacity;
    /** time of node */
    int time;

    /** Edge constructor */
    Edge(int t, int c,int time) {
        to = t;
        capacity = c;
        this.time = time;
    }
}
