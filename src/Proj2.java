import Exc1.Graph;
import Exc2.Graph2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Proj2 {
    public static void main(String[] args) {
    
        Scanner reader = new Scanner(System.in);
        
        List<String> files = new LinkedList<>();
        files.add("in01_b.txt");
        files.add("in02_b.txt");
        files.add("in03_b.txt");
        files.add("in04_b.txt");
        files.add("in05_b.txt");
        files.add("in06_b.txt");
        files.add("in07_b.txt");
        files.add("in08_b.txt");
        files.add("in09_b.txt");
        files.add("in10_b.txt");
        
        for(int i=1; i<10 ;i++)
            System.out.println(i + ": in0"+i+"_b.txt");
        System.out.println(10 + ": in10_b.txt");
        System.out.print("Please select a graph to use -> ");
        
        int textNumber = reader.nextInt();
        
        try {
            File file = new File("input/"+files.get(textNumber-1)); // change file name here for diferent graphs
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
    
            System.out.print("Select what scenery you want 1 or 2 -> ");
            int scenery = reader.nextInt();
            
            if(scenery == 1) {
                System.out.print("Select what option you prefer 1 or 2 -> ");
                int c = reader.nextInt();
                if (c == 1) g.dijkstra();
                else g.maxPath();
            }
            else {
                System.out.println("Select what option you prefer 1, 2, 3, 4 or 5, and also say how big is your group");
                int c = reader.nextInt();
                int group = reader.nextInt();
                h.maxFlow(nodes,c,group);
            }
            
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
