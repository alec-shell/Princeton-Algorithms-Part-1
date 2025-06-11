package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private int segmentCount = 0;
    private LineSegment[] segments = new LineSegment[1];

    public FastCollinearPoints(Point[] points) {
        // check for null inputs
        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }
        // copy points, sort, and check for duplicate coordinates
        Point[] copy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copy[i] = points[i];
        }
        Merge.sort(copy);
        for (int i = 1; i < copy.length; i++) {
            if (copy[i].compareTo(copy[i - 1]) == 0) throw new IllegalArgumentException();
        }
        // find segments
        for (int i = 0; i < copy.length - 3; i++) {
            Merge.slopeSort(points, i);
            double slope = points[i].slopeTo(points[i + 1]);
            int count = 0;
            for (int j = i + 1; j < copy.length; j++) {
                if (points[i].slopeTo(points[j]) == slope) count++;
                else {
                    if (count >= 3) {
                        segmentCount++;
                        resizeArray();
                        segments[segmentCount - 1] = new LineSegment(points[i], points[j - 1]);
                        slope = points[i].slopeTo(points[j]);
                        count = 1;
                    }
                }
            }
        }
    } // end constructor

    private void resizeArray() {
        if (segmentCount >= segments.length) {
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

        // merge sort by slope
        public static void slopeSort(Point[] points, int i) {
            aux = new Point[points.length];
            slopeSort(points, points[i], i + 1, points.length - 1);
        } // end slopeSort

        private static void slopeSort(Point[] points, Point p, int lo, int hi) {
            if (lo >= hi) return;
            else {
                int mid = lo + (hi - lo) / 2;
                slopeSort(points, p, lo, mid);
                slopeSort(points, p, mid + 1, hi);
                slopeMerge(points, p, lo, mid, hi);
            }
        } // end slopeSort

        private static void slopeMerge(Point[] points, Point p, int lo, int mid, int hi) {
            int left = lo;
            int right = mid + 1;

            for (int i = lo; i <= hi; i++) {
                if (left > mid) aux[i] = points[right++];
                else if (right > hi) aux[i] = points[left++];
                else if (slopeLess(p, points[left], points[right])) aux[i] = points[left++];
                else aux[i] = points[right++];
            }

            for (int i = lo; i <= hi; i++) {
                points[i] = aux[i];
            }
        } // end slopeMerge

        private static boolean slopeLess(Point a, Point b, Point c) {
            double slopeAB = a.slopeTo(b);
            double slopeAC = a.slopeTo(c);
            return slopeAB < slopeAC;
        } // end slopeLess

        // merge sort by Cartesian coordinates
        public static void sort(Point[] points) {
            aux = new Point[points.length];
            sort(points, 0, points.length - 1);
        } // end sort

        private static void sort(Point[] points, int lo, int hi) {
            if (lo >= hi) return;
            else {
                int mid = lo + (hi - lo) / 2;
                sort(points, lo, mid);
                sort(points, mid + 1, hi);
                merge(points, lo, mid, hi);
            }
        } // end sort

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

        private static boolean less(Point a, Point b) {
            return a.compareTo(b) < 0;
        } // end less
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    } // end main
} // end FastCollinearPoints class
