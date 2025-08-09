package wordnet;

import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayDeque;

public class SAP {
    Digraph G;

    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        this.G = G;
    } // constructor

    public int length(int v, int w) {
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
            throw new IllegalArgumentException("Vertices must be from 0 to " + (G.V() - 1));
        if (v == w) return 0;

        int[] visitedV = new int[G.V() - 1], visitedW = new int[G.V() - 1];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        visitedV[v] = 0;
        visitedW[w] = 0;
        queue.push(v);
        while (!queue.isEmpty()) {
            int current = queue.pop();
            for (Integer n : G.adj(current)) {
                if (visitedV[n] == 0) {
                    visitedV[n] = visitedV[current] + 1;
                }
            }
        }

        queue.push(w);
        while (!queue.isEmpty()) {
            int current = queue.pop();
            for (Integer n : G.adj(current)) {
                if (visitedV[n] > 0) return visitedV[n] + ++visitedW[current];
                else visitedW[n] = visitedW[current] + 1;
            }
        }
        return -1;
    } // length()

    public int ancestor(int v, int w) {
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
            throw new IllegalArgumentException("Vertices must be from 0 to " + (G.V() - 1));
        if (v == w) return v;

        int[] visitedV = new int[G.V() - 1];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        visitedV[v] = 1;
        queue.push(v);
        // BFS from v to root
        while (!queue.isEmpty()) {
            Iterable<Integer> neighbors = G.adj(queue.pop());
            for (Integer n : neighbors) {
                if (visitedV[n]++ == 0) queue.push(v);
            }
        }
        // BFS from w until LCA or root is reached
        queue.push(w);
        while (!queue.isEmpty()) {
            for (Integer n : G.adj(queue.pop())) {
                if (visitedV[n] > 0) return n;
            }
        }
        // if no intersection, return -1
        return -1;
    } // ancestor()

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        // null args checks
        if (v == null || w == null) throw new IllegalArgumentException("null arg/s");
        v.forEach((vertex) -> {
            if (vertex == null) throw new IllegalArgumentException("Iterable contains null arg/s");
        });
        w.forEach((vertex) -> {
            if (vertex == null) throw new IllegalArgumentException("Iterable contains null arg/s");
        });

        int[] distV = new int[G.V() - 1];
        int[] distW = new int[G.V() - 1];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        // add v IDs to queue and BFS to root
        for (Integer ID : v) {
            distV[ID] = 0;
            queue.push(ID);
        }
        while (!queue.isEmpty()) {
            int current = queue.pop();
            for (Integer n : G.adj(current)) {
                if (distV[n] == 0) distV[n] = distV[current] + 1;
            }
        }
        // Check if w IDs were reached in v's BFS.
        // If no LCA, add to queue and perform BFS until LCA is found
        for (Integer ID : w) {
            if (distV[ID] != 0) return distV[ID];
            distW[ID] = 0;
            queue.push(ID);
        }
        while (!queue.isEmpty()) {
            int current = queue.pop();
            for (Integer n : G.adj(current)) {
                if (distW[n] == 0) {
                    if (distV[n] != 0) return distV[n] + (distW[current] + 1);
                    distW[n] = distW[current] + 1;
                }
            }
        }
        // if no intersection, return -1
        return -1;
    } // length()

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        // null args checks
        if (v == null || w == null) throw new IllegalArgumentException("null arg/s");
        v.forEach((vertex) -> {
            if (vertex == null) throw new IllegalArgumentException("Iterable contains null arg/s");
        });
        w.forEach((vertex) -> {
            if (vertex == null) throw new IllegalArgumentException("Iterable contains null arg/s");
        });

        int[] ancestorsV = new int[G.V() - 1];
        int[] ancestorsW = new int[G.V() - 1];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        // process v IDs into queue, marking all processed ID's as > 0
        for (Integer ID : v) {
            ancestorsV[ID]++;
            queue.push(ID);
        }
        // perform BFS on w
        while (!queue.isEmpty()) {
            int current = queue.pop();
            for (Integer n : G.adj(current)) {
                ancestorsV[n]++;
            }
        }
        // process w IDs, checking for LCA in ancestorsV
        for (Integer ID : w) {
            if (ancestorsV[ID] != 0) return ID;
            ancestorsW[ID]++;
            queue.push(ID);
        }
        // perform BFS on w until LCA found or root
        while (!queue.isEmpty()) {
            int current = queue.pop();
            for (Integer n : G.adj(current)) {
                if (ancestorsW[n]++ == 0) {
                    if (ancestorsV[n] != 0) return n;
                }
            }
        }
        // if no LCA found, return -1
        return -1;
    } // ancestor()

    public static void main(String[] args) {

    }
} // SAP class
