package puzzle8;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    private final int[][] board;
    private final int N;


    public Board(int[][] tiles) {
        N = tiles.length;
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = tiles[i][j];
            }
        }
    } // end constructor

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(String.format("%2d ", board[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    } // end toString

    public int dimension() {
        return N;
    } // end dimension

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int candidate = board[i][j];
                if (candidate == 0) continue;
                int desired = i * N + j + 1;
                if (desired != candidate) hamming++;
            }
        }
        return hamming;
    } // end hamming

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int candidate = board[i][j];
                if (candidate == 0) continue;
                int row = (candidate - 1) / N;
                int col = (candidate - 1) % N;
                manhattan += Math.abs(row - i) + Math.abs(col - j);
            }
        }
        return manhattan;
    } // end manhattan

    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int prospect = board[i][j];
                if (prospect == 0) continue;
                int desired = i * N + j + 1;
                if (desired != prospect) return false;
            }
        }
        return true;
    } // end isGoal

    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.board, that.board);
    } // end equals

    public Iterable<Board> neighbors() {
        Queue<Board> iterable = new Queue<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    if (i - 1 >= 0) addToQueue(iterable, i, j, i - 1, j);
                    if (i + 1 < N) addToQueue(iterable, i, j, i + 1, j);
                    if (j - 1 >= 0) addToQueue(iterable, i, j, i, j - 1);
                    if (j + 1 < N) addToQueue(iterable, i, j, i, j + 1);
                    break;
                }
            }
        }
        return iterable;
    } // end neighbors

    private void addToQueue(Queue<Board> iterable, int i, int j, int x, int y) {
        exchange(i, j, x, y);
        iterable.enqueue(new Board(board));
        exchange(i, j, x, y);
    } // end addToQueue

    private void exchange(int i, int j, int x, int y) {
        int temp = board[i][j];
        board[i][j] = board[x][y];
        board[x][y] = temp;
    } // end exchange

    public Board twin() {
        int i = 0;
        int j = 0;
        if (board[i][j] == 0) i++;
        if (board[i][j + 1] == 0) i++;
        exchange(i, j, i, j + 1);
        Board twin = new Board(board);
        exchange(i, j, i, j + 1);
        return twin;
    } // end twin

    public static void main(String[] args) {
        // test array
        int[][] array = new int[][] {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 }
        };

        int[][] twoXtwo = {
                { 1, 0 },
                { 3, 2 }
        };

        // create two identical test boards
        Board test = new Board(twoXtwo);
        Board equalTest = new Board(twoXtwo);

        // Test all function outputs and send return values to stdIO
        StdOut.println("TO STRING: \n" + test.toString());
        StdOut.println("HAMMING: " + test.hamming());
        StdOut.println("MANHATTAN: " + test.manhattan());
        StdOut.println("DIMENSION: " + test.dimension());
        StdOut.println("IS GOAL?: " + test.isGoal());
        StdOut.println("Twin: \n" + test.twin().toString());

        Iterable<Board> iter = test.neighbors();
        StdOut.println("POSSIBLE MOVES: ");
        for (Board b : iter) System.out.println(b.toString());
        StdOut.println("EQUALS (T): " + test.equals(equalTest));
        StdOut.println("EQUALS (F): " + test.equals(test.twin()));
    } // end main
} // end Board class
