/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int opened = 0;
    private WeightedQuickUnionUF weightedUF;
    private WeightedQuickUnionUF backwashUF;
    private int n;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.grid[i][j] = false;
            }
        }
        this.weightedUF = new WeightedQuickUnionUF(n * n + 2);
        this.backwashUF = new WeightedQuickUnionUF(n * n);
    }

    public void open(int row, int col) {
        if (row < 1 || row > grid.length || col < 1 || col > grid.length) {
            throw new IllegalArgumentException();
        }
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            opened++;
            int siteValue = (row - 1) * n + col;
            if (row == 1) {
                weightedUF.union(siteValue, 0);
            }
            else if (row == n) {
                weightedUF.union(siteValue, n * n + 1);
            }
            checkNeighbors(row, col, siteValue);
        }
    }

    private void checkNeighbors(int row, int col, int siteValue) {
        if (row - 2 >= 0) {
            if (isOpen(row - 1, col)) {
                weightedUF.union((row - 2) * n + col, siteValue);
                backwashUF.union((row - 2) * n + col - 1, siteValue - 1);
            }
        }
        if (row + 1 <= n) {
            if (isOpen(row + 1, col)) {
                weightedUF.union(row * n + col, siteValue);
                backwashUF.union(row * n + col - 1, siteValue - 1);
            }
        }
        if (col - 2 >= 0) {
            if (isOpen(row, col - 1)) {
                weightedUF.union((row - 1) * n + col - 1, siteValue);
                backwashUF.union((row - 1) * n + col - 1, siteValue - 1);
            }
        }
        if (col + 1 <= n) {
            if (isOpen(row, col + 1)) {
                weightedUF.union((row - 1) * n + col + 1, siteValue);
                backwashUF.union((row - 1) * n + col, siteValue - 1);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > grid.length || col < 1 || col > grid.length) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || row > grid.length || col < 1 || col > grid.length) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return backwashUF.find(((row - 1) * n + col - 1)) == backwashUF.find(0);
        }
        return false;
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        return weightedUF.find(0) == weightedUF.find(n * n + 1);
    }
}
