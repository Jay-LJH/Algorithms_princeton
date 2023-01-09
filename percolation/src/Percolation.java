import edu.princeton.cs.algs4.StdRandom;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        N = n;
        grid = new boolean[n + 1][n + 1];
        id = new int[(n + 1) * (n + 1)];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                grid[i][j] = false;
                id[i * n + j] = i * n + j;
            }
        }
        grid[0][0] = grid[0][1] = true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!grid[row][col]) {
            grid[row][col] = true;
        }
        if (row == 1) {
            connect(row, col, 0, 0);
        } else if (row == N) {
            connect(row, col, 0, 1);
        }
        if (row > 1 && isOpen(row - 1, col))
            connect(row, col, row - 1, col);
        if (row < N && isOpen(row + 1, col))
            connect(row, col, row + 1, col);
        if (col < N && isOpen(row, col + 1))
            connect(row, col, row, col + 1);
        if (col > 1 && isOpen(row, col - 1))
            connect(row, col, row, col - 1);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return root(row, col) == root(0, 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                if (isOpen(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return root(0, 1) == root(0, 0);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 20;
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int row = StdRandom.uniformInt(n) + 1;
            int col = StdRandom.uniformInt(n) + 1;
            while (p.isOpen(row, col)) {
                row = StdRandom.uniformInt(n) + 1;
                col = StdRandom.uniformInt(n) + 1;
            }
            p.open(row, col);
        }
        System.out.println((double) p.numberOfOpenSites() / (n * n));
        return;
    }

    private int root(int row, int col) {
        int i = row * N + col;
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    private void connect(int row, int col, int row2, int col2) {
        if (isOpen(row, col) && isOpen(row2, col2)) {
            int i = root(row, col);
            int j = root(row2, col2);
            if (i == j) return;
            id[i] = j;
        }
    }

    private boolean[][] grid;
    private int[] id;
    private int N;
}