import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        queue.insert(new node(initial,null,0));
        MinPQ<node> twin=new MinPQ<>(new Comparator<node>() {
            @Override
            public int compare(node node, node t1) {
                return node.priority-t1.priority;
            }
        });
        twin.insert(new node(initial.twin(),null,0));
        while (true){
            node n=queue.delMin();
            node t=twin.delMin();
            if(n.tiles.isGoal()){
                last=n;
                solveable=true;
                move=n.move;
                break;
            }
            else if(t.tiles.isGoal()){
                last=null;
                solveable=false;
                move=-1;
                break;
            }
            else {
                Iterable<Board> neighbors=n.tiles.neighbors();
                for (Board b: neighbors) {
                    if(n.parent!=null && b.equals(n.parent.tiles)){
                        continue;
                    }
                    else {
                        queue.insert(new node(b,n,n.move+1));
                    }
                }
                neighbors=t.tiles.neighbors();
                for (Board b: neighbors) {
                    if(t.parent!=null && b.equals(t.parent.tiles)){
                        continue;
                    }
                    else {
                        twin.insert(new node(b,t,t.move+1));
                    }
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solveable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return move;
    }


    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        node n=last;
        List<Board> boards=new ArrayList<>();
        while (n!=null){
            boards.add(n.tiles);
            n=n.parent;
        }
        Collections.reverse(boards);
        return boards;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In("puzzle02.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            for (Board board : solver.solution())
                StdOut.println(board);
            StdOut.println("Minimum number of moves = " + solver.moves());
        }
    }
    private MinPQ<node> queue=new MinPQ<>(new Comparator<node>() {
        @Override
        public int compare(node node, node t1) {
            return node.priority-t1.priority;
        }
    });
    private node last;
    private boolean solveable;
    private int move;
    private class node {
        public int move;
        public int priority;
        public int manhattan;
        public Board tiles;
        public node parent;

        public node(Board tiles, node parent, int move) {
            this.tiles = tiles;
            this.parent = parent;
            this.move = move;
            manhattan = tiles.manhattan();
            priority = move + manhattan;
        }
    }
}