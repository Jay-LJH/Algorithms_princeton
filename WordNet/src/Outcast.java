import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Outcast {
    public Outcast(WordNet wordnet) {
        w = wordnet;
    }      // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        int dis[][] = new int[nouns.length][nouns.length];
        List<pair> list = new ArrayList<>();
        for (int i = 0; i < nouns.length; i++) {
            for (int j = 0; j < nouns.length; j++) {
                dis[i][j] = w.distance(nouns[i], nouns[j]);
            }
            list.add(new pair(nouns[i], Arrays.stream(dis[i]).sum()));
        }
        Collections.sort(list, pair::CompareTo);
        return list.get(0).noun;
    }// given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }  // see test client below

    private class pair {
        private String noun;
        private int dis;

        public pair(String noun, int dis) {
            this.dis = dis;
            this.noun = noun;
        }

        private int CompareTo(pair that) {
            return that.dis - this.dis;
        }
    }

    private WordNet w;
}