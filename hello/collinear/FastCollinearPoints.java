package collinear;

public class FastCollinearPoints {
    private Point[] copy;
    private int segmentCount = 0;
    private LineSegment[] segments = new LineSegment[1];

    public FastCollinearPoints(Point[] points) {
        // check for null inputs
        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }
        // copy points to copy, sort, and check for duplicate coordinates
        copy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copy[i] = points[i];
        }
        Merge.sort(copy);
        for (int i = 1; i < copy.length; i++) {
            if (copy[i].compareTo(copy[i - 1]) == 0) throw new IllegalArgumentException();
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
        return segments;
    } // end segments

    private static class Merge {
        private static Point[] aux;

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

} // end FastCollinearPoints class
