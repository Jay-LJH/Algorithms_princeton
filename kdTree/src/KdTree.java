import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    public KdTree()                               // construct an empty set of points{
    {
        size = 0;
        root = null;
    }

    public boolean isEmpty()                      // is the set empty?{
    {
        return size == 0;
    }

    public int size()                         // number of points in the set
    {
        return size;
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        check(p);
        if (root == null) {
            root = new node(p, null);
        } else {
            node temp = root;
            while (true) {
                if(temp.key==p)
                    return;
                if (temp.direct) {
                    if (p.x() < temp.key.x()) {
                        if (temp.left == null) {
                            temp.left = new node(p, temp);
                            size++;
                            break;
                        }
                        else {
                            temp = temp.left;
                        }
                    } else{
                        if (temp.right == null) {
                            temp.right = new node(p, temp);
                            size++;
                            break;
                        } else {
                            temp = temp.right;
                        }
                    }
                } else {
                    if (p.y() < temp.key.y()) {
                        if (temp.down == null) {
                            temp.down = new node(p, temp);
                            size++;
                            break;
                        } else {
                            temp = temp.down;
                        }
                    } else {
                        if (temp.up == null) {
                            temp.up = new node(p, temp);
                            size++;
                            break;
                        } else {
                            temp = temp.up;
                        }
                    }
                }
            }
        }
    }

    public boolean contains(Point2D p)            // does the set contain point p?{
    {
        check(p);
        if (root == null)
            return false;
        else {
            node temp = root;
            while (true) {
                if (temp.key.equals(p))
                    return true;
                if (temp.direct) {
                    if (p.x() < temp.key.x()) {
                        if (temp.left == null)
                            return false;
                        temp = temp.left;
                    } else {
                        if (temp.right == null)
                            return false;
                        temp = temp.right;
                    }
                } else {
                    if (p.y() < temp.key.y()) {
                        if (temp.down == null)
                            return false;
                        temp = temp.down;
                    } else {
                        if (temp.up == null)
                            return false;
                        temp = temp.up;
                    }
                }
            }
        }
    }

    public void draw()                         // draw all points to standard draw{
    {
        if (!isEmpty())
            root.draw();
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if(rect==null)
            throw new IllegalArgumentException();
        List<Point2D> list = new ArrayList<>();
        recursive_range(rect, list, root);
        return list;
    }

    private void recursive_range(RectHV rect, List<Point2D> list, node n) {
        if (n == null)
            return;
        if (rect.contains(n.key)) {
            list.add(n.key);
        }
        if (n.direct) {
            if (n.key.x() > rect.xmax()) {
                recursive_range(rect, list, n.left);
            } else if (n.key.x() < rect.xmin()) {
                recursive_range(rect, list, n.right);
            } else {
                recursive_range(rect, list, n.right);
                recursive_range(rect, list, n.left);
            }
        } else {
            if (n.key.y() > rect.ymax()) {
                recursive_range(rect, list, n.down);
            } else if (n.key.y() < rect.ymin()) {
                recursive_range(rect, list, n.up);
            } else {
                recursive_range(rect, list, n.up);
                recursive_range(rect, list, n.down);
            }
        }
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        check(p);
        if (root == null)
            return null;
        Point2D ans=new Point2D(10,10);
        return nearest(p, root,ans);
    }

    private Point2D nearest(Point2D p, node n,Point2D ans) {
        if(n==null)
            return ans;
        if(p.distanceSquaredTo(n.key)<p.distanceSquaredTo(ans))
            ans=n.key;
        if(n.direct){
            if(p.x()<n.key.x())
            {
                ans=nearest(p,n.left,ans);
                if(p.distanceSquaredTo(ans)> Math.pow(p.x()-n.key.x(),2))
                    ans=nearest(p,n.right,ans);
            }
            else {
                ans=nearest(p,n.right,ans);
                if(p.distanceSquaredTo(ans)> Math.pow(p.x()-n.key.x(),2))
                    ans=nearest(p,n.left,ans);
            }
        }
        else {
            if(p.y()<n.key.y())
            {
                ans=nearest(p,n.down,ans);
                if(p.distanceSquaredTo(ans)> Math.pow(p.y()-n.key.y(),2))
                    ans=nearest(p,n.up,ans);
            }
            else {
                ans=nearest(p,n.up,ans);
                if(p.distanceSquaredTo(ans)> Math.pow(p.y()-n.key.y(),2))
                    ans=nearest(p,n.down,ans);
            }
        }
        return ans;
    }

    private class node {
        public node(Point2D key, node parent) {
            this.key = key;
            if (parent != null)
                this.direct = !parent.direct;
            else
                this.direct = true;
            this.parent = parent;
            left = null;
            right = null;
            up = null;
            down = null;
        }

        public void draw() {
            key.draw();

            if (direct) {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                key.draw();
                double x = key.x();
                Point2D start;
                Point2D end;
                if (parent == null) {
                    start = new Point2D(x, 0);
                    end = new Point2D(x, 1);
                } else if (parent.parent != null && parent.parent.parent == null) {
                    if (key.y() > parent.key.y()) {
                        start = new Point2D(x, parent.key.y());
                        end = new Point2D(x, 1);
                    } else {
                        start = new Point2D(x, 0);
                        end = new Point2D(x, parent.key.y());
                    }
                } else {
                    if (Math.min(Math.min(key.y(), parent.key.y()), parent.parent.parent.key.y()) == key.y()) {
                        start = new Point2D(x, 0);
                        end = new Point2D(x, parent.key.y());
                    } else if (Math.max(Math.max(key.y(), parent.key.y()), parent.parent.parent.key.y()) == key.y()) {
                        start = new Point2D(x, parent.key.y());
                        end = new Point2D(x, 1);
                    } else {
                        start = new Point2D(x, parent.key.y());
                        end = new Point2D(x, parent.parent.parent.key.y());
                    }
                }
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(start.x(), start.y(), end.x(), end.y());
            } else {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                key.draw();
                double y = key.y();
                Point2D start;
                Point2D end;
                if (parent.parent == null) {
                    if (key.x() > parent.key.x()) {
                        start = new Point2D(parent.key.x(), y);
                        end = new Point2D(1, y);
                    } else {
                        start = new Point2D(0, y);
                        end = new Point2D(parent.key.x(), y);
                    }
                } else {
                    if (Math.min(Math.min(key.x(), parent.key.x()), parent.parent.parent.key.x()) == key.x()) {
                        start = new Point2D(0, y);
                        end = new Point2D(parent.key.x(), y);
                    } else if (Math.max(Math.max(key.x(), parent.key.x()), parent.parent.parent.key.x()) == key.x()) {
                        start = new Point2D(parent.key.x(), y);
                        end = new Point2D(1, y);
                    } else {
                        start = new Point2D(parent.key.x(), y);
                        end = new Point2D(parent.parent.parent.key.x(), y);
                    }
                }
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(start.x(), start.y(), end.x(), end.y());
            }
            if (left != null)
                left.draw();
            if (right != null)
                right.draw();
            if (up != null)
                up.draw();
            if (down != null)
                down.draw();
        }
        private void brute_draw(){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            key.draw();
            if (left != null)
                left.brute_draw();
            if (right != null)
                right.brute_draw();
            if (up != null)
                up.brute_draw();
            if (down != null)
                down.brute_draw();
        }
        private Point2D key;
        private boolean direct; // if direct,split the space vertical, else horizontal
        private node parent;
        private node left;
        private node right;
        private node up;
        private node down;
    }

    private node root;
    private int size;

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        String filename = "circle10.txt";
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        kdtree.draw();
    }

    private void check(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
    }
}
