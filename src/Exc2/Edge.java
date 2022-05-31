package Exc2;

public class Edge implements Comparable<Edge>{
    /** Node start */
    int start;
    /** Node destiny */
    int to;
    /** time of node */
    int time;
    
    /** Edge constructor */
    Edge(int s, int t, int time) {
      start = s;
      to = t;
      this.time = time;
    }
  
  @Override
  public int compareTo(Edge o) {
    if (start < o.start) return -1;
    if (start > o.start) return +1;
    if (to < o.to) return -1;
    if (to > o.to) return +1;
    return 0;
  }
}
