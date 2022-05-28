package Exc1;

import Exc1.Edge;

import java.util.LinkedList;

// Classe que representa um no
class Node {
    public LinkedList<Edge> adj; // Lista de adjacencias
    public int distance;         // Distancia ao no origem da pesquisa
    public boolean visited;


    Node() {
        adj = new LinkedList<>();
    }
}