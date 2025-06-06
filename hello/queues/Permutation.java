import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> words = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            words.enqueue(StdIn.readString());
        }

        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            StdOut.println(words.dequeue());
        }
    } // end main
} // end Permutation class