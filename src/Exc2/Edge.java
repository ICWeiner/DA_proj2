package Exc2;

public class Edge implements Comparable<Edge>{
    int start;  /** Node start */
    int to;     /** Node destiny */
    int time;   /** time of node */
    
    /** Edge constructor */
    Edge(int s, int t, int time) {
      start = s;
      to = t;
      this.time = time;
    }
  
    /** function to compare edges in order of starting node and then node destiny */
  @Override
  public int compareTo(Edge o) {
    if (start < o.start) return -1;
    if (start > o.start) return +1;
    if (to < o.to) return -1;
    if (to > o.to) return +1;
    return 0;
  }
}
