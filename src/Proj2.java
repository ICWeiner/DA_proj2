import Exc1.Graph;
import Exc2.Graph2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Proj2 {
    public static void main(String[] args) {

        try {
            File file = new File("input/basic.txt"); // change file name here for diferent graphs
            Scanner in = new Scanner(file);

            int nodes = in.nextInt();
            int e = in.nextInt();

            Graph g = new Graph(nodes);//class Graph is used for the first scenario
            Graph2 h = new Graph2(nodes,e);//class Graph2 is used for the second scenario

            for (int i = 0; i < e; i++){
                int a = in.nextInt(),b = in.nextInt(),c = in.nextInt(),d = in.nextInt();
                g.addLink(a,b,c,d);//create the edges for both graphs (they are implemented differently)
                h.addLink(a,b,c,d);
            }

            System.out.println("Exercise 1:");
            // calling these functions does what is asked in scenario 1
            g.maxPath();   // find path with the highest capacity
            g.dijkstra();  // find the shortest path
    
            System.out.println("Exercício 2:");
            // calling this function does what is requested in scenario 2
            // use the second parameter to choose a group size
            h.maxFlow(nodes,10);
            
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
