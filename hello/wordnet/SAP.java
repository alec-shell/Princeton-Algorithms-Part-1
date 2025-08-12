package wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;

public class SAP {
    private final Digraph G;

    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Null arg");
        this.G = G;
    } // constructor()

    public int length(int v, int w) {
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
            throw new IllegalArgumentException("Arg/s out of range");
        if (v == w) return 0;
        int[] distV = new int[G.V()], distW = new int[G.V()];
        Arrays.fill(distV, -1);
        Arrays.fill(distW, -1);
        distV[v] = 0;
        distW[w] = 0;
        ArrayDeque<Integer> queueV = new ArrayDeque<>(), queueW = new ArrayDeque<>();
        queueV.offer(v);
        queueW.offer(w);

        return findLength(queueV, queueW, distV, distW);
    } // length()

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!validateVertices(v, w)) throw new IllegalArgumentException("Invalid arg/s");
        int[] distV = new int[G.V()], distW = new int[G.V()];
        Arrays.fill(distV, -1);
        Arrays.fill(distW, -1);
        ArrayDeque<Integer> queueV = new ArrayDeque<>(), queueW = new ArrayDeque<>();
        v.forEach((ID) -> {
            distV[ID] = 0;
            queueV.offer(ID);
        });
        w.forEach((ID) -> {
            distW[ID] = 0;
            queueW.offer(ID);
        });

        return findLength(queueV, queueW, distV, distW);
    } // length()

    private int findLength(ArrayDeque<Integer> queueV, ArrayDeque<Integer> queueW, int[] distV,
                           int[] distW) {
        int closest = -1;
        while (!queueV.isEmpty() || !queueW.isEmpty()) {
            if (!queueV.isEmpty()) {
                int resp = combiBFS(queueV, distV, distW)[0];
                if (closest == -1 || (resp != -1 && resp < closest)) closest = resp;
            }
            if (!queueW.isEmpty()) {
                int resp = combiBFS(queueW, distW, distV)[0];
                if (closest == -1 || (resp != -1 && resp < closest)) closest = resp;
            }
        }
        return closest;
    } // findLength()

    public int ancestor(int v, int w) {
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
            throw new IllegalArgumentException("Arg/s out of range");
        int[] markedV = new int[G.V()], markedW = new int[G.V()];
        markedV[v] = 0;
        markedW[w] = 0;
        ArrayDeque<Integer> queueV = new ArrayDeque<>(), queueW = new ArrayDeque<>();
        queueV.offer(v);
        queueW.offer(w);

        return findLCA(queueV, queueW, markedV, markedW);
    } // ancestor()

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!validateVertices(v, w)) throw new IllegalArgumentException("Invalid arg/s");
        int[] markedV = new int[G.V()], markedW = new int[G.V()];
        ArrayDeque<Integer> queueV = new ArrayDeque<>(), queueW = new ArrayDeque<>();
        v.forEach((ID) -> {
            queueV.offer(ID);
            markedV[ID] = 0;
        });
        w.forEach((ID) -> {
            queueW.offer(ID);
            markedW[ID] = 0;
        });

        return findLCA(queueV, queueW, markedV, markedW);
    } // ancestor()

    private int findLCA(ArrayDeque<Integer> queueV, ArrayDeque<Integer> queueW, int[] markedV,
                        int[] markedW) {
        int lca = -1;
        int distance = -1;
        while (!queueV.isEmpty() || !queueW.isEmpty()) {
            if (!queueV.isEmpty()) {
                int[] resp = combiBFS(queueV, markedV, markedW);
                if (distance == -1 || resp[0] < distance) {
                    distance = resp[0];
                    lca = resp[1];
                }
            }
            if (!queueW.isEmpty()) {
                int[] resp = combiBFS(queueW, markedW, markedV);
                if (distance == -1 || resp[0] < distance) {
                    distance = resp[0];
                    lca = resp[1];
                }
            }
        }
        return lca;
    } // findLCA()

    private boolean validateVertices(Iterable<Integer> v, Iterable<Integer> w) {
        Iterator<Integer> iterV = v.iterator();
        Iterator<Integer> iterW = w.iterator();
        while (iterV.hasNext() || iterW.hasNext()) {
            if (iterV.hasNext()) {
                Integer next = iterV.next();
                if (next == null || next < 0 || next >= G.V()) return false;
            }
            if (iterW.hasNext()) {
                Integer next = iterW.next();
                if (next == null || next < 0 || next >= G.V()) return false;
            }
        }
        return true;
    } // validateVertices()

    private int[] combiBFS(ArrayDeque<Integer> queue, int[] distA, int[] distB) {
        int prev = queue.poll();

        int distance = -1;
        int lca = -1;
        for (Integer ID : G.adj(prev)) {
            if (distA[ID] == -1) {
                distA[ID] = distA[prev] + 1;
                queue.offer(ID);
                if (distB[ID] != -1) {
                    int dist = distB[ID] + distA[ID];
                    if (distance == -1 || dist < distance) {
                        distance = dist;
                        lca = ID;
                    }
                }
            }
        }
        return new int[] { distance, lca };
    } // distanceBFS()

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int V = StdIn.readInt();
            int W = StdIn.readInt();
            int length = sap.length(V, W);
            int ancestor = sap.ancestor(V, W);
            StdOut.printf("Length = %d Ancestor = %d\n", length, ancestor);
        }
    } // main()

} // SAP class
