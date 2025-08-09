package wordnet;

import edu.princeton.cs.algs4.Digraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordNet {
    private Map<Integer, ArrayList<String>> synMap = new HashMap<>();
    private Map<String, ArrayList<Integer>> nounMap = new HashMap<>();
    private Digraph rootedDAG;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException("Null arg/s");

        processSynsets(synsets);

        // init rootedDAG with vertex count from synset
        rootedDAG = new Digraph(synMap.size());

        processHypernyms(hypernyms);

        boolean validDAG = false;
        for (Integer ID : synMap.keySet()) {
            if (rootedDAG.outdegree(ID) == 0) validDAG = true;
        }
        if (!validDAG) throw new IllegalArgumentException("Invalid synsets");
    } // constructor

    private void processSynsets(String synsets) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(synsets));

            String line;
            while ((line = file.readLine()) != null) {
                splitSynsets(line);
            }
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    } // processSynsets()

    private void splitSynsets(String line) {
        String[] info = line.split(",");
        int id = Integer.parseInt(info[0]);
        // split synsets
        String[] setArray = info[1].strip().split(" ");
        ArrayList<String> setList = new ArrayList<>();
        // iterate through nouns, assigning to setMap
        for (String s : setArray) {
            setList.add(s);
            // add or update nounMap with noun IDs
            if (nounMap.containsKey(s)) {
                ArrayList<Integer> currentList = nounMap.get(s);
                currentList.add(id);
                nounMap.replace(s, currentList);
            }
            ArrayList<Integer> newList = new ArrayList<>();
            newList.add(id);
            nounMap.put(s, newList);
        }
        synMap.put(id, setList);
    } // splitSynsets()

    private void processHypernyms(String hypernyms) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(hypernyms));
            // split ids and hypernyms
            String line;
            while ((line = file.readLine()) != null) {
                String[] set = line.split(",");
                int v = Integer.parseInt(set[0]);
                for (int i = 1; i < set.length; i++) {
                    int hID = Integer.parseInt(set[i]);
                    rootedDAG.addEdge(v, hID);
                }
            }
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    } // processHypernyms()

    public Iterable<String> nouns() {
        return nounMap.keySet();
    } // nouns()

    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("Null arg");
        return nounMap.containsKey(word);
    } // isNoun()

    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException("Null arg/s");
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException("Invalid arg/s");
        return -1;
    } // distance()

    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException("Null arg/s");
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException("Invalid arg/s");

        return null;
    } // sap()

    public static void main(String[] args) {
        WordNet test = new WordNet(
                "/home/Alec/repos/Princeton-Algorithms-Part-1/hello/wordnet/synsets.txt",
                "/home/Alec/repos/Princeton-Algorithms-Part-1/hello/wordnet/hypernyms.txt");

        System.out.println(test.isNoun("'hood"));
        System.out.println("V: " + test.rootedDAG.V() + " E: " + test.rootedDAG.E());
    } // main()

} // WordNet class