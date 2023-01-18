import java.util.Comparator;
import java.util.TreeSet;

public class CircularSuffixArray {
    // circular suffix array of s
    public CircularSuffixArray(String s){
        if(s==null||s.length()==0)
            throw new IllegalArgumentException();
        this.s=s;
        index=new int[s.length()];
        TreeSet<node> list=new TreeSet<>(Comparator.comparing(node -> node.s));
        for(int i=0;i<s.length();i++)
            list.add(new node(i,s.substring(i)+s.substring(0,i)));
        int count=0;
       for(node s1:list)
       {
           index[count]=s1.i;
           count++;
       }
    }
    private class node{
        int i;
        String s;
        node(int i,String s)
        {
            this.s=s;
            this.i=i;
        }
    }
    // length of s
    public int length()
    {
        return s.length();
    }
    // returns index of ith sorted suffix
    public int index(int i){
        if(i<0||i>=s.length())
            throw new IllegalArgumentException();
        return index[i];
    }
    private String s;
    private int[] index;
    // unit testing (required)
    public static void main(String[] args){
        CircularSuffixArray c=new CircularSuffixArray("abracadabra!");
    }
}
