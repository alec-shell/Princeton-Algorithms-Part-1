package puzzle8;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<Node> initPQ = new MinPQ<>();
    private MinPQ<Node> twinPQ = new MinPQ<>();
    private boolean solvable = true;

    public Solver(Board init) {
        if (init == null) throw new IllegalArgumentException();
        initPQ.insert(new Node(init, null, 0));
        twinPQ.insert(new Node(init.twin(), null, 0));
        while (!initPQ.min().current.isGoal() && !twinPQ.min().current.isGoal()) {
            iterate(initPQ);
            iterate(twinPQ);
        }
        if (!initPQ.min().current.isGoal()) solvable = false;
    } // end constructor

    private void iterate(MinPQ<Node> PQ) {
        Node prev = PQ.delMin();
        Iterable<Board> neighbors = prev.current.neighbors();
        for (Board n : neighbors) {
            if (!prev.isLast()) {
                if (n.equals(prev.previous.current)) continue;
            }
            PQ.insert(new Node(n, prev, prev.moves + 1));
        }
    } // end iterate

    public boolean isSolvable() {
        if (twinPQ.min().current.isGoal()) return false;
        return solvable;
    } // end isSolvable

    public int moves() {
        if (!isSolvable()) return -1;
        return initPQ.min().moves;
    } // end moves

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> solution = new Stack<>();

        Node current = initPQ.min();
        while (!current.isLast()) {
            solution.push(current.current);
            current = current.previous;
        }
        solution.push(current.current);
        return solution;
    } // end solution

    private class Node implements Comparable<Node> {
        Board current;
        Node previous;
        int moves;
        int manhattan;

        public Node(Board current, Node prev, int moves) {
            this.current = current;
            this.previous = prev;
            this.moves = moves;
            this.manhattan = current.manhattan() + moves;
        } // end constructor

        public final boolean isLast() {
            return previous == null;
        } // end isLast

        public int compareTo(Node that) {
            int thisM = this.manhattan;
            int thatM = that.manhattan;
            if (thisM < thatM) return -1;
            else if (thisM == thatM) return 0;
            return 1;
        } // end compareTo
    } // end Node class

    /*
    public static void main(String[] args) {
        // create init board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board init = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(init);

        // print solution to stdIO
        if (!solver.isSolvable()) StdOut.println("No solution possible.");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) StdOut.println(board);
        }
    } // end main

     */

    public static void main(String[] args) {
        int[][] a = {
                { 2, 1, 3, 4 },
                { 5, 6, 7, 8 },
                { 9, 10, 11, 12 },
                { 12, 15, 14, 0 }
        };

        int[][] b = {
                { 0, 1, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };

        int[][] c = {
                { 3, 0 },
                { 2, 1 }
        };

        Board testBoard = new Board(c);

        Solver test = new Solver(testBoard);
        if (!test.isSolvable()) StdOut.println("Board is unsolvable");
        else {
            StdOut.println("Moves: " + test.moves());
            Iterable<Board> iter = test.solution();
            for (Board board : iter) StdOut.println(board);
        }
    } // end main

} // end Solver class
