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
        if (G == null) throw new IllegalArgumentException("null arg");
        this.G = G;
    } // constructor()

    public int length(int v, int w) {
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
            throw new IllegalArgumentException("arg/s out of range");
        if (v == w) return 0;
        int[] distV = new int[G.V()], distW = new int[G.V()];
        Arrays.fill(distV, -1);
        Arrays.fill(distW, -1);
        distV[v] = 0;
        distW[w] = 0;
        ArrayDeque<Integer> queueV = new ArrayDeque<>(), queueW = new ArrayDeque<>();
        queueV.offer(v);
        queueW.offer(w);
        while (!queueV.isEmpty() || !queueW.isEmpty()) {
            if (!queueV.isEmpty()) {
                int resp = distanceBFS(queueV, distV, distW);
                if (resp != -1) return resp;
            }
            if (!queueW.isEmpty()) {
                int resp = distanceBFS(queueW, distW, distV);
                if (resp != -1) return resp;
            }
        }
        return -1;
    } // length()

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!validateVertices(v, w)) throw new IllegalArgumentException("invalid arg/s");
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
        while (!queueV.isEmpty() || !queueW.isEmpty()) {
            if (!queueV.isEmpty()) {
                int resp = distanceBFS(queueV, distV, distW);
                if (resp != -1) return resp;
            }
            if (!queueW.isEmpty()) {
                int resp = distanceBFS(queueW, distW, distV);
                if (resp != -1) return resp;
            }
        }
        return -1;
    } // length()

    private int distanceBFS(ArrayDeque<Integer> queue, int[] distA, int[] distB) {
        int prev = queue.poll();
        for (Integer ID : G.adj(prev)) {
            if (distA[ID] == -1) {
                if (distB[ID] != -1) return distB[ID] + distA[prev] + 1;
                else distA[ID] = distA[prev] + 1;
                queue.offer(ID);
            }
        }
        return -1;
    } // distanceBFS()

    public int ancestor(int v, int w) {
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
            throw new IllegalArgumentException("arg/s out of range");
        boolean[] markedV = new boolean[G.V()], markedW = new boolean[G.V()];
        markedV[v] = true;
        markedW[w] = true;
        ArrayDeque<Integer> queueV = new ArrayDeque<>(), queueW = new ArrayDeque<>();
        queueV.offer(v);
        queueW.offer(w);
        while (!queueV.isEmpty() || !queueW.isEmpty()) {
            if (!queueV.isEmpty()) {
                int resp = ancestorBFS(queueV, markedV, markedW);
                if (resp != -1) return resp;
            }
            if (!queueW.isEmpty()) {
                int resp = ancestorBFS(queueW, markedW, markedV);
                if (resp != -1) return resp;
            }
        }
        return -1;
    } // ancestor()

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!validateVertices(v, w)) throw new IllegalArgumentException("invalid arg/s");
        boolean[] markedV = new boolean[G.V()], markedW = new boolean[G.V()];
        ArrayDeque<Integer> queueV = new ArrayDeque<>(), queueW = new ArrayDeque<>();
        v.forEach((ID) -> {
            queueV.offer(ID);
            markedV[ID] = true;
        });
        w.forEach((ID) -> {
            queueW.offer(ID);
            markedW[ID] = true;
        });
        while (!queueV.isEmpty() || !queueW.isEmpty()) {
            if (!queueV.isEmpty()) {
                int resp = ancestorBFS(queueV, markedV, markedW);
                if (resp != -1) return resp;
            }
            if (!queueW.isEmpty()) {
                int resp = ancestorBFS(queueW, markedW, markedV);
                if (resp != -1) return resp;
            }
        }
        return -1;
    } // ancestor()

    private int ancestorBFS(ArrayDeque<Integer> queue, boolean[] markedA, boolean[] markedB) {
        int prev = queue.poll();
        for (Integer ID : G.adj(prev)) {
            if (!markedA[ID]) {
                if (markedB[ID]) return ID;
                queue.offer(ID);
                markedA[ID] = true;
            }
        }
        return -1;
    } // ancestorBFS()

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
