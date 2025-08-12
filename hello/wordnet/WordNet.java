package wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class WordNet {
    private Digraph G;
    private Map<Integer, String> synMap = new HashMap<>();
    private Map<String, HashSet<Integer>> nounMap = new HashMap<>();
    private SAP sap;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException("Null arg/s");
        In syn = new In(synsets);
        In hyper = new In(hypernyms);

        int V = processSynset(syn);

        G = new Digraph(V);

        if (!processHypernyms(hyper)) throw new IllegalArgumentException("DAG must have one root");

        int[] markers = new int[V];
        for (Integer ID : synMap.keySet()) {
            if (cycleDetection(markers, ID))
                throw new IllegalArgumentException("Cycle/s detected in DAG");
        }

        sap = new SAP(G);
    } // constructor()

    private int processSynset(In syn) {
        int vertices = 0;
        while (!syn.isEmpty()) {
            vertices++;
            String[] line = syn.readLine().split(",");
            int ID = Integer.parseInt(line[0]);
            synMap.put(ID, line[1]);
            String[] nouns = line[1].strip().split(" ");
            for (String noun : nouns) {
                HashSet<Integer> IDs;
                if (!nounMap.containsKey(noun)) {
                    IDs = new HashSet<>();
                    IDs.add(ID);
                    nounMap.put(noun, IDs);
                }
                else {
                    IDs = nounMap.get(noun);
                    IDs.add(ID);
                    nounMap.replace(noun, IDs);
                }
            }
        }
        return vertices;
    } // processSynset()

    private boolean processHypernyms(In hyper) {
        // single root check
        int rootCount = 0;
        while (!hyper.isEmpty()) {
            String[] IDs = hyper.readLine().split(",");
            if (IDs.length == 1) rootCount++;
            int ID = Integer.parseInt(IDs[0].strip());
            for (int i = 1; i < IDs.length; i++) G.addEdge(ID, Integer.parseInt(IDs[i].strip()));
        }
        return rootCount == 1;
    } // processHypernyms()

    private boolean cycleDetection(int[] marked, int v) {
        if (marked[v] == 1) return true;
        if (marked[v] == 2) return false;
        marked[v] = 1;
        for (int n : G.adj(v)) {
            if (!cycleDetection(marked, n)) return true;
        }
        marked[v] = 2;
        return false;
    } // cycleDetection()

    public Iterable<String> nouns() {
        return nounMap.keySet();
    } // nouns()

    public boolean isNoun(String noun) {
        return nounMap.containsKey(noun);
    } // isNoun

    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("Invalid arg/s");
        return sap.length(nounMap.get(nounA), nounMap.get(nounB));
    } // distance

    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("Invalid arg/s");
        int ancestorID = sap.ancestor(nounMap.get(nounA), nounMap.get(nounB));
        return synMap.get(ancestorID);
    } // sap()

    public static void main(String[] args) {

    } // main()

} // WordNet class
