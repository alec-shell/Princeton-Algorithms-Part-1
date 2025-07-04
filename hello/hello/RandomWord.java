import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        double index = 2.0;
        String victor = StdIn.readString();
        while (!StdIn.isEmpty()) {
            if (StdRandom.bernoulli(1.0 / index)) {
                victor = StdIn.readString();
            }
            index++;
        }
        StdOut.println(victor);
    }
}
