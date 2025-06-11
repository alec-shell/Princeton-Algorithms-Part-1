package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private Point[] copy;
    private int segmentCount = 0;
    private LineSegment[] segments = new LineSegment[1];

    public BruteCollinearPoints(Point[] points) {
        // check for null inputs
        if (points == null) throw new IllegalArgumentException();
        copy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            copy[i] = points[i];
        }
        // sort copy and check for duplicates
        Merge.sort(copy);
        for (int i = 1; i < copy.length; i++) {
            if (copy[i].compareTo(copy[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        // look for valid line segments
        validateSegments();
    } // end constructor

    private void validateSegments() {
        for (int i = 0; i < copy.length - 3; i++) {
            for (int j = i + 1; j < copy.length - 2; j++) {
                for (int k = j + 1; k < copy.length - 1; k++) {
                    double ijSlope = copy[i].slopeTo(copy[j]);
                    double jkSlope = copy[j].slopeTo(copy[k]);
                    if (ijSlope != jkSlope) continue;
                    for (int m = k + 1; m < copy.length; m++) {
                        double kmSlope = copy[k].slopeTo(copy[m]);
                        if (kmSlope == jkSlope) {
                            segmentCount++;
                            resizeArray();
                            segments[segmentCount - 1] = new LineSegment(copy[i], copy[m]);
                        }
                    }
                }
            }
        }
    } // end validateSegments

    private void resizeArray() {
        if (segmentCount > segments.length) {
            LineSegment[] temp = new LineSegment[segments.length * 2];
            for (int i = 0; i < segments.length; i++) {
                temp[i] = segments[i];
            }
            segments = temp;
        }
    } // end resizeArray

    public int numberOfSegments() {
        return segmentCount;
    } // end numberOfSegments

    public LineSegment[] segments() {
        LineSegment[] returnable = new LineSegment[segmentCount];
        for (int i = 0; i < segmentCount; i++) {
            returnable[i] = segments[i];
        }
        return returnable;
    } // end segments

    private static class Merge {
        private static Point[] aux;

        public static void sort(Point[] points) {
            aux = new Point[points.length];
            sort(points, 0, points.length - 1);
        }

        private static void sort(Point[] points, int lo, int hi) {
            if (lo == hi) return;
            else {
                int mid = lo + (hi - lo) / 2;
                sort(points, lo, mid);
                sort(points, mid + 1, hi);
                if (!less(points[mid], points[mid + 1])) merge(points, lo, mid, hi);
            }
        } // end sort

        private static boolean less(Point a, Point b) {
            return a.compareTo(b) < 0;
        } // end less

        private static void merge(Point[] points, int lo, int mid, int hi) {
            int left = lo;
            int right = mid + 1;
            for (int i = lo; i <= hi; i++) {
                if (left > mid) aux[i] = points[right++];
                else if (right > hi) aux[i] = points[left++];
                else if (less(points[left], points[right])) aux[i] = points[left++];
                else aux[i] = points[right++];
            }
            for (int i = lo; i <= hi; i++) {
                points[i] = aux[i];
            }
        } // end merge

    } // end Merge class

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
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
        StdDraw.show();
    } // end main
} // end BruteCollinearPoints
