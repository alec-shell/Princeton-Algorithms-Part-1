package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;

public class KdTree {
    private Node root;
    private int size = 0;

    public KdTree() {
        this.root = null;
    } // end constructor

    public boolean isEmpty() {
        return root == null;
    } // end isEmpty

    public int size() {
        return this.size;
    } // end size

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (size == 0) {
            size++;
            root = new Node(p, new RectHV(0, 0, 1, 1), true, null, null);
        }
        else insertHelper(p, root);
    } // end insert

    private void insertHelper(Point2D p, Node node) {
        int comp = node.compareTo(p);
        if (comp == 0) return;
        else if (comp > 0) {
            if (node.lesser == null) {
                RectHV rect = buildRect(node, false);
                node.lesser = new Node(p, rect, !node.isVertical, null, null);
                size++;
                return;
            }
            else insertHelper(p, node.lesser);
        }
        else {
            if (node.greater == null) {
                RectHV rect = buildRect(node, true);
                node.greater = new Node(p, rect, !node.isVertical, null, null);
                size++;
                return;
            }
            else insertHelper(p, node.greater);
        }
    } // end insertHelper

    private RectHV buildRect(Node parent, boolean isGreater) {

        if (parent.isVertical) {
            if (isGreater) {
                return new RectHV(parent.point.x(), parent.rect.ymin(), parent.rect.xmax(),
                                  parent.rect.ymax());
            }
            return new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.point.x(),
                              parent.rect.ymax());
        }
        else {
            if (isGreater) {
                return new RectHV(parent.rect.xmin(), parent.point.y(), parent.rect.xmax(),
                                  parent.rect.ymax());
            }
            return new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(),
                              parent.point.y());
        }
    } // end buildRect

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return containsHelper(p, root);
    } // end contains

    private boolean containsHelper(Point2D p, Node node) {
        if (node == null) return false;
        else if (node.point.compareTo(p) == 0) return true;
        else return containsHelper(p, node.lesser) || containsHelper(p, node.greater);
    } // end containsHelper

    public void draw() {
        draw(root);
    } // end draw

    private void draw(Node node) {
        if (node == null) return;
        StdDraw.point(node.point.x(), node.point.y());
        if (node.isVertical) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }
        draw(node.lesser);
        draw(node.greater);
    } // end draw (recursive)

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        return null;
    } // end range

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return null;
    } // end nearest

    private class Node implements Comparable<Point2D> {
        private Point2D point;
        private RectHV rect;
        private boolean isVertical;
        private Node lesser;
        private Node greater;

        Node(Point2D p, RectHV rect, boolean isVertical, Node lesser, Node greater) {
            this.point = p;
            this.rect = rect;
            this.isVertical = isVertical;
            this.lesser = lesser;
            this.greater = greater;
        } // end constructor

        public int compareTo(Point2D that) {
            if (this.point == that) return 0;
            if (isVertical) {
                int comp = Double.compare(this.point.x(), that.x());
                if (comp != 0) return comp;
                return Double.compare(this.point.y(), that.y());
            }
            else {
                int comp = Double.compare(this.point.y(), that.y());
                if (comp != 0) return comp;
                return Double.compare(this.point.x(), that.x());
            }
        } // end compareTo
    } // end Node

    public static void main(String[] args) {
        KdTree test = new KdTree();
        Point2D p1 = new Point2D(.5, .5);
        Point2D p2 = new Point2D(.25, .5);
        Point2D p3 = new Point2D(.25, .9);
        Point2D p4 = new Point2D(.75, .4);
        Point2D p5 = new Point2D(.75, .25);
        test.insert(p5);
        test.insert(p2);
        test.insert(p1);
        test.insert(p3);
        test.insert(p4);
        StdOut.println("CONTAINS(T): " + test.contains(p3));
        StdOut.println("CONTAINS(F): " + test.contains(new Point2D(11, 10)));
        StdOut.println("SIZE: " + test.size());

        test.draw();
    } // end main
} // end KdTree class
