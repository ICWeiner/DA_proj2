import Exc1.Graph;
import Exc2.Graph2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Proj2 {
    public static void main(String[] args) {

        try {
            File file = new File("input/in01_b.txt");
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

            System.out.println("Exercício 1:");
            //chamar estas funçoes faz o que é pedido no cenario 1
            g.maxPath(1);   // encontrar caminho com mais capacidade para o no final
            g.dijkstra(1);  //encontrar caminho mais curto para o no final
    
            System.out.println("Exercício 2:");
            //chamar esta funçoes faz o que é pedido no cenario 2
            h.maxFlow(1,nodes,6);
            
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
