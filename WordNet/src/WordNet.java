import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        check(synsets, hypernyms);
        In in = new In(synsets);
        int count=0;
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] strings = line.split(",");
            String[] noun=strings[1].split(" ");
            for(String s:noun)
            {
                if(nouns.containsKey(s)){
                    nouns.get(s).add(Integer.parseInt(strings[0]));
                }
                else {
                    List<Integer> list=new ArrayList<>();
                    list.add(Integer.parseInt(strings[0]));
                    nouns.put(s,list);
                }
            }
            map1.put(Integer.parseInt(strings[0]),strings[1]);
            count++;
        }
        in = new In(hypernyms);
        graph = new Digraph(count);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] strings = line.split(",");
            for (int i = 1; i < strings.length; i++)
                graph.addEdge(Integer.parseInt(strings[0]), Integer.parseInt(strings[i]));
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        check(word);
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        List<Integer> v1 = nouns.get(nounA);
        List<Integer> v2 = nouns.get(nounB);
        SAP s = new SAP(graph);
        return s.length(v1, v2);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        List<Integer> v1 = nouns.get(nounA);
        List<Integer> v2 = nouns.get(nounB);
        SAP s = new SAP(graph);
        int anc = s.ancestor(v1, v2);
        return map1.get(anc);
    }

    private void check(Object... args) {
        for (Object o : args)
            if (o == null)
                throw new IllegalArgumentException();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(wordnet.distance("whacker","factor_IX"));
    }
    private Map<Integer,String> map1=new HashMap<>();
    private Map<String,List<Integer>> nouns=new HashMap<>();
    private Digraph graph;
}