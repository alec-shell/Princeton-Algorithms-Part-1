import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // attributes
    private Node first = null;
    private Node last = null;
    private int count = 0;

    public boolean isEmpty() {
        return count == 0;
    } // end isEmpty

    public int size() {
        return count;
    } // end size

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (first == null) {
            first = new Node(item, null, null);
            last = first;
        }
        else {
            Node temp = first;
            first = new Node(item, null, temp);
            temp.setPrev(first);
        }
        count++;
    } // end addFirst

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (last == null) {
            last = new Node(item, null, null);
            first = last;
        }
        else {
            Node temp = last;
            last = new Node(item, temp, null);
            temp.setNext(last);
        }
        count++;
    } // end addLast

    public Item removeFirst() {
        if (count == 0) {
            throw new NoSuchElementException();
        }
        Item temp = first.getItem();
        if (count == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.getNext();
            first.setPrev(null);
        }
        count--;
        return temp;
    } // end removeFirst

    public Item removeLast() {
        if (count == 0) {
            throw new NoSuchElementException();
        }
        Item temp = last.getItem();
        if (count == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.getPrev();
            last.setNext(null);
        }
        count--;
        return temp;
    } // end removeLast

    public Iterator<Item> iterator() {
        return new DequeIterator();
    } // end iterator

    private class Node {
        // attributes
        private Item item;
        private Node prev;
        private Node next;

        public Node(Item item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        } // end constructor

        public Node() {
        }

        // getters
        public final Item getItem() {
            return this.item;
        } // end getItem

        public final Node getNext() {
            return this.next;
        } // end getNext

        public final Node getPrev() {
            return this.prev;
        } // end getPrev

        // setters
        public final void setNext(Node next) {
            this.next = next;
        } // end setNext

        public final void setPrev(Node prev) {
            this.prev = prev;
        } // end setPrev
    } // end Node class

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        private int counter = 0;

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return counter < count;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.getItem();
            current = current.getNext();
            counter++;
            return item;

        }
    } // end DequeIterator class

    public static void main(String[] args) {
        Deque<Integer> test = new Deque<>();

        StdOut.println("Empty: " + test.isEmpty());
        StdOut.println("Init size: " + test.size());

        test.addFirst(32);
        test.addLast(24);
        test.addFirst(11);
        test.addLast(12);

        StdOut.println("Empty: " + test.isEmpty());
        StdOut.println("New Size: " + test.size());

        Iterator<Integer> testIt = test.iterator();

        for (Integer num : test) {
            StdOut.println(testIt.next());
        }

        StdOut.println("Removed first node with item: " + test.removeFirst());
        StdOut.println("Removed last node with item: " + test.removeLast());
        StdOut.println("New first node value: " + test.first.getItem());
        StdOut.println("New last node value: " + test.last.getItem());
        StdOut.println("New Size: " + test.size());
    } // end main
} // end Deque class
