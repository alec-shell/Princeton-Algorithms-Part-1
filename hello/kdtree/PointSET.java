package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Random;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> pointSet;

    public PointSET() {
        pointSet = new TreeSet<>();
    } // end constructor

    public boolean isEmpty() {
        return pointSet.isEmpty();
    } // end isEmpty

    public int size() {
        return pointSet.size();
    } // end size

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        pointSet.add(p);
    } // end insert

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return pointSet.contains(p);
    }

    public void draw() {
        for (Point2D p : pointSet) StdDraw.point(p.x(), p.y());
    } // end draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> points = new Queue<>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) points.enqueue(p);
        }
        return points;
    } // end range

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D nearest = null;
        for (Point2D n : pointSet) {
            if (n.equals(p)) continue;
            if (nearest == null) nearest = n;
            else if (p.distanceTo(n) < p.distanceTo(nearest)) nearest = n;
        }
        return nearest;
    } // end nearest

    public static void main(String[] args) {
        Random rand = new Random();
        PointSET test = new PointSET();

        StdOut.println("IS EMPTY? " + test.isEmpty());
        StdOut.println("ADDING RANDOM POINTS... ");
        // add points to test
        for (int i = 0; i < 200; i++) {
            test.insert(new Point2D(rand.nextDouble(), rand.nextDouble()));
        }
        StdOut.println("IS EMPTY? " + test.isEmpty());

        // add known point for testing
        Point2D pTest = new Point2D(1, 2);
        test.insert(pTest);
        StdOut.println("CONTAINS POINT(1, 2)? " + test.contains(pTest));
        StdOut.println("NEAREST NEIGHBOR: " + test.nearest(pTest));
        StdOut.println("SET SIZE: " + test.size());

        double minX = rand.nextDouble();
        double minY = rand.nextDouble();
        double maxX = ((1 - minX) * 0.5) + minX;
        double maxY = ((1 - minY) * 0.5) + minY;
        StdOut.println("CREATING RECTANGLE... ");
        RectHV testRect = new RectHV(minX, minY, maxX, maxY);
        StdOut.println(testRect.toString());

        // create Iterable of contained points from test in testRect
        Iterable<Point2D> contained = test.range(testRect);
        StdOut.println("POINTS CONTAINED IN RECTANGLE: ");
        for (Point2D p : contained) StdOut.println(p);

        // draw test set and test rectangle
        test.draw();
        testRect.draw();
    } // end main
} // end PointSET class