import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


public class PointSET {
    public PointSET()                               // construct an empty set of points
    {
        set = new TreeSet<>(Point2D::compareTo);
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return set.isEmpty();
    }

    public int size()                         // number of points in the set
    {
        return set.size();
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        check(p);
        set.add(p);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        check(p);
        return set.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        for (Point2D p : set) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if(rect==null)
            throw new IllegalArgumentException();
        List<Point2D> list = new ArrayList<>();
        for (Point2D p : set) {
            if (p.x() <= rect.xmax() && p.x() >= rect.xmin() && p.y() <= rect.ymax() && p.y() >= rect.ymin()) {
                list.add(p);
            }
        }
        return list;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        check(p);
        if(isEmpty())
            return null;
        Point2D near=new Point2D(10,10);
        for(Point2D point:set){
            if(p.distanceSquaredTo(near)>p.distanceSquaredTo(point))
                near=point;
        }
        return near;
    }
    private TreeSet<Point2D> set;

    private void check(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
    }

}