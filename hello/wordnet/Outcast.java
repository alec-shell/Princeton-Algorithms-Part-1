package wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.Map;

public class Outcast {
    private final WordNet WN;

    public Outcast(WordNet WN) {
        if (WN == null) throw new IllegalArgumentException("Null arg");
        this.WN = WN;
    } // constructor

    public String outcast(String[] nouns) {
        int furthest = 0;
        String outcast = null;
        Map<Integer, Map<Integer, Integer>> memo = new HashMap<>();

        for (int i = 0; i < nouns.length; i++) {
            int dist = 0;
            Map<Integer, Integer> lengths = new HashMap<>();
            for (int j = 0; j < nouns.length; j++) {
                Integer distance = null;
                if (memo.get(j) != null) distance = memo.get(j).get(i);
                if (distance != null) dist += distance;
                else {
                    distance = WN.distance(nouns[i], nouns[j]);
                    dist += distance;
                    lengths.put(j, distance);
                }
            }
            if (dist > furthest) {
                furthest = dist;
                outcast = nouns[i];
            }
            memo.put(i, lengths);
        }
        return outcast;
    } // outcast()

    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wn);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            String output = outcast.outcast(nouns);
            StdOut.println(args[t] + " : " + output);
        }
    } // main()

} // Outcast class
