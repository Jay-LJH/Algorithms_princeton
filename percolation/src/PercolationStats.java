import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import java.lang.Math;

public class PercolationStats {

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        T=trials;
        x=new double[T];
        for(int i=0;i<T;i++){
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
            x[i]=(double) p.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(x);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(x);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean()-1.96*stddev()/Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean()+1.96*stddev()/Math.sqrt(T);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats Pe=new PercolationStats(n,T);
        System.out.println("mean                    = "+Pe.mean());
        System.out.println("stddev                  = "+Pe.stddev());
        System.out.println("95% confidence interval = ["+Pe.confidenceLo()+","+Pe.confidenceHi()+"]");
    }
    private int T;
    private double[] x;

}
