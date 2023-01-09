import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == points[j]) throw new IllegalArgumentException();
            }
        }
        this.points = points;
        Arrays.sort(this.points, Point::compareTo);
    }

    // the number of line segments
    public int numberOfSegments() {
        if (num == 0) segments();
        return num;
    }

    // the line segments
    public LineSegment[] segments() {
        num = 0;
        LineSegment[] lineSegments = new LineSegment[1000];
        Point[] list = points.clone();
        Point[] start = new Point[1000];
        Point[] end = new Point[1000];
        for (Point p : points) {
            Arrays.sort(list, p.slopeOrder());
            int count = 0;
            for (int i = list.length - 1; i > 0; i--) {
                if (p.slopeTo(list[i]) == p.slopeTo(list[i - 1])) {
                    count++;
                }
                else {
                    if (count >= 2) {
                        boolean flag = true;
                        for (int n = 0; n < num; n++) {
                            if (start[n].slopeTo(end[n]) == p.slopeTo(list[i])
                                    && start[n].slopeTo(p) == p.slopeTo(list[i])
                            ) {
                                Point[] line = new Point[] { start[n], end[n], p, list[i] };
                                Arrays.sort(line, Point::compareTo);
                                start[n] = line[0];
                                end[n] = line[3];
                                flag = false;
                            }
                        }
                        if (flag) {
                            start[num] = p;
                            end[num] = list[i];
                            num++;
                        }
                    }
                    count = 0;
                }
            }
        }
        for (int i = 0; i < num; i++)
            lineSegments[i] = new LineSegment(start[i], end[i]);
        return Arrays.copyOfRange(lineSegments, 0, num);
    }

    public static void main(String[] args) {
        In in = new In("input200.txt");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        System.out.println(collinear.numberOfSegments());
    }

    private static void showlist(Point[] points) {
        for (Point t : points) {
            System.out.print(t);
        }
        System.out.println();
    }

    private Point[] points;
    private int num;
}
