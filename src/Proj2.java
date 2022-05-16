// Exemplo de aplicacao do algoritmo de Dijkstra
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Proj2 {
    public static void main(String args[]) {

        try {
            File file = new File("input/basic.txt");
            Scanner in = new Scanner(file);
            Graph g = new Graph(in.nextInt());
            int   e = in.nextInt();
            for (int i=0; i<e; i++){
                g.addLink(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
            }

            //g.dijkstra(1);//encontrar caminho mais curto para cada no
            g.maxCapacity(1);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
