import Exc1.Graph;
import Exc2.Graph2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Proj2 {
    public static void main(String[] args) {

        try {
            File file = new File("input/basic.txt");
            Scanner in = new Scanner(file);
            int nodes = in.nextInt();
            int e = in.nextInt();
            Graph g = new Graph(nodes);
            Graph2 h = new Graph2(nodes,e);
            for (int i = 0; i < e; i++){
                int a = in.nextInt(),b = in.nextInt(),c = in.nextInt(),d = in.nextInt();
                g.addLink(a,b,c,d);
                h.addLink(a,b,c,d);
            }

            System.out.println("Exercise 1:");
            // calling these functions does what is asked in scenario 1
            g.maxPath(1);   // find path with more capacity for the in the end
            g.dijkstra(1);  // find the shortest way to the in the end
    
            System.out.println("Exercício 2:");
            // calling this function does what is requested in scenario 2
            h.maxFlow(1,nodes,6);
            
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
