import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s;
        if (BinaryStdIn.isEmpty())
            return;
        s = BinaryStdIn.readString();
        CircularSuffixArray c = new CircularSuffixArray(s);
        char[] code = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            if (c.index(i) == 0)
                BinaryStdOut.write(i);
        }
        for (int i = 0; i < s.length(); i++) {
            if (c.index(i) == 0)
                code[i] = s.charAt(s.length() - 1);
            else
                code[i] = s.charAt(c.index(i) - 1);
            BinaryStdOut.write(code[i]);
        }
        BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int start=BinaryStdIn.readInt();
        List<node> list=new ArrayList<>();
        int count=0;
        while(!BinaryStdIn.isEmpty()) {
            list.add(new node(count++,BinaryStdIn.readChar()));
        }
        List<node> sort = new ArrayList<>(list);
        sort.sort(Comparator.comparing(node -> node.c));
        for(int i=sort.get(start).i;i!=start;i=sort.get(i).i){
            BinaryStdOut.write(list.get(i).c);
        }
        BinaryStdOut.write(list.get(start).c);
        BinaryStdOut.flush();
    }
    private static class node{
        int i;
        char c;
        node(int i,char c)
        {
            this.c=c;
            this.i=i;
        }
    }
    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-"))
            transform();
        else
            inverseTransform();
    }
}
