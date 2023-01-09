import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        graph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths b1 = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths b2 = new BreadthFirstDirectedPaths(graph, w);
        int min = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (b1.hasPathTo(i) && b2.hasPathTo(i)) {
                if (min != -1) {
                    min = Math.min(min, b1.distTo(i) + b2.distTo(i));
                } else
                    min = b1.distTo(i) + b2.distTo(i);
            }
        }
        return min;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths b1 = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths b2 = new BreadthFirstDirectedPaths(graph, w);
        int min = -1;
        int anc = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (b1.hasPathTo(i) && b2.hasPathTo(i)) {
                if (min != -1) {
                    if(min> b1.distTo(i) + b2.distTo(i)){
                        min = b1.distTo(i) + b2.distTo(i);
                        anc = i;
                    }
                } else {
                    min = b1.distTo(i) + b2.distTo(i);
                    anc = i;
                }
            }
        }
        return anc;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        check(v,w);
        BreadthFirstDirectedPaths b1 = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths b2 = new BreadthFirstDirectedPaths(graph, w);
        int min = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (b1.hasPathTo(i) && b2.hasPathTo(i)) {
                if (min != -1) {
                    min = Math.min(min, b1.distTo(i) + b2.distTo(i));
                } else
                    min = b1.distTo(i) + b2.distTo(i);
            }
        }
        return min;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        check(v,w);
        BreadthFirstDirectedPaths b1 = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths b2 = new BreadthFirstDirectedPaths(graph, w);
        int min = -1;
        int anc = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (b1.hasPathTo(i) && b2.hasPathTo(i)) {
                if (min != -1) {
                    if(min> b1.distTo(i) + b2.distTo(i)){
                        min = b1.distTo(i) + b2.distTo(i);
                        anc = i;
                    }
                } else {
                    min = b1.distTo(i) + b2.distTo(i);
                    anc = i;
                }
            }
        }
        return anc;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("digraph3.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
    private void check(Iterable<Integer> v,Iterable<Integer> w){
        if(v==null || w==null)
            throw new IllegalArgumentException();
        boolean flag=false;
        for(Integer i:v){
            flag=true;
            if(i==null)
                throw new IllegalArgumentException();
        }
        if(!flag)
            throw new IllegalArgumentException();
        flag=false;
        for(Integer i:w){
            flag=true;
            if(i==null)
                throw new IllegalArgumentException();
        }
        if(!flag)
            throw new IllegalArgumentException();
    }
    private Digraph graph;
}
