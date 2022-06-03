package Exc1;

import java.util.LinkedList;

/** Class that represents an edge */
class Node {
    /** List of edges that start from node*/
    public LinkedList<Edge> adj;
    /** Smallest distance to starting node*/
    public int distance;
    /** Marks if node has been visited*/
    public boolean visited;

    /** Constructor initialized adj as an empty list*/
    Node() {
        adj = new LinkedList<>();
    }
}