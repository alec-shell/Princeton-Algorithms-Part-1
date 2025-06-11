/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

package collinear;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    } // end constructor

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    } // end draw

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    } // end drawTo

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        double xVal = that.x - this.x;
        double yVal = that.y - this.y;

        if (xVal == 0) {
            if (yVal == 0) return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        else if (yVal == 0) return +0.0;
        return yVal / xVal;
    } // end slopeTo

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y) return -1;
        if (this.y == that.y) {
            if (this.x < that.x) return -1;
            if (this.x == that.x) return 0;
        }
        return 1;
    } // end compareTo

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new PointComparator(this);
    } // end slopeOrder

    private class PointComparator implements Comparator<Point> {
        private Point zero;

        public PointComparator(Point zero) {
            this.zero = zero;
        } // end constructor

        public int compare(Point one, Point two) {
            double zeroToOne = zero.slopeTo(one);
            double zeroToTwo = zero.slopeTo(two);

            if (zeroToOne < zeroToTwo) return -1;
            if (zeroToOne > zeroToTwo) return 1;
            return 0;
        } // end compare
    } // end PointComparator class

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    } // end toString

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point test1 = new Point(0, 0);
        Point test2 = new Point(1, 1);
        Point test3 = new Point(2, 2);

        test1.draw();
        test2.draw();
        test3.draw();
        test1.drawTo(test2);
        test1.drawTo(test3);
        edu.princeton.cs.algs4.StdOut.println(test1.slopeTo(test1));
        edu.princeton.cs.algs4.StdOut.println(test1.slopeTo(test2));
        edu.princeton.cs.algs4.StdOut.println(test1.compareTo(test1));
        edu.princeton.cs.algs4.StdOut.println(test1.compareTo(test2));
        Comparator<Point> test1C = test1.slopeOrder();
        edu.princeton.cs.algs4.StdOut.println(test1C.compare(test2, test3));
    } // end main
} // end Point class