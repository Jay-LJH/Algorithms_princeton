import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == points[j])
                    throw new IllegalArgumentException();
            }
        }
        this.p = points;
        Arrays.sort(this.p, new Comparator<Point>() {
            public int compare(Point point, Point t1) {
                return point.compareTo(t1);
            }
        });
    }

    // the number of line segments
    public int numberOfSegments() {
        if (num == 0) segments();
        return num;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[1000];
        num = 0;
        for (int i = 0; i < p.length; i++) {
            for (int j = i + 1; j < p.length; j++) {
                for (int x = j + 1; x < p.length; x++) {
                    for (int y = x + 1; y < p.length; y++) {
                        if (p[i].slopeTo(p[j]) == p[i].slopeTo(p[x])
                                && p[i].slopeTo(p[j]) == p[i].slopeTo(p[y])) {
                            Point[] points = new Point[] { p[i], p[j], p[x], p[y] };
                            Arrays.sort(points, Point::compareTo);
                            lineSegments[num] = new LineSegment(points[0], points[3]);
                            num++;
                        }
                    }
                }
            }
        }
        return Arrays.copyOfRange(lineSegments, 0, num);
    }

    public static void main(String[] args) {
        In in = new In("input56.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        System.out.println(collinear.numberOfSegments());
        StdDraw.show();
    }

    private int num;
    private Point[] p;
}
