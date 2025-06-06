/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

package percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_CONST = 1.96;
    private static final double SQRT_VAL = 0.5;
    private double[] results;


    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        results = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation testGrid = new Percolation(n);
            while (!testGrid.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                if (!testGrid.isOpen(row, col)) {
                    testGrid.open(row, col);
                }
            }
            results[i] = testGrid.numberOfOpenSites() / (double) (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - ((CONFIDENCE_CONST
                * stddev()) / ((double) results.length * SQRT_VAL));
    }

    public double confidenceHi() {
        return mean() + ((CONFIDENCE_CONST * stddev()) / ((double) results.length * SQRT_VAL));
    }

    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]),
                                                     Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + test.mean());
        StdOut.println("stddev                  = " + test.stddev());
        StdOut.println(
                "95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi()
                        + "]");
    }
}
