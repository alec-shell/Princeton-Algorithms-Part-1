import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    // attributes
    private Item[] itemQueue;
    private int pointer = 0;

    public RandomizedQueue() {
        itemQueue = (Item[]) new Object[1];
    } // end constructor

    public boolean isEmpty() {
        return pointer == 0;
    } // end isEmpty

    public int size() {
        return pointer;
    } // end size

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (pointer == itemQueue.length) {
            resize();
        }
        itemQueue[pointer++] = item;
    } // end enqueue

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniformInt(pointer);
        Item item = itemQueue[index];
        itemQueue[index] = itemQueue[--pointer];
        resize();
        return item;
    } // end dequeue

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return itemQueue[StdRandom.uniformInt(pointer)];
    } // end sample

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    } // end iterator

    private void resize() {
        Item[] newArray;
        if (pointer == itemQueue.length) {
            newArray = (Item[]) new Object[pointer * 2];
            swapQueues(itemQueue, newArray);
        }
        else if (pointer <= itemQueue.length / 4) {
            int size = itemQueue.length / 4;
            if (size < 1) size = 1;
            newArray = (Item[]) new Object[size];
            swapQueues(itemQueue, newArray);
        }
    } // end resize

    private void swapQueues(Item[] queue, Item[] newArray) {
        for (int i = 0; i < pointer; i++) {
            newArray[i] = queue[i];
        }
        this.itemQueue = newArray;
    } // end iterate

    private class RandomizedQueueIterator implements Iterator<Item> {
        // attributes
        int localPointer;
        private Item[] rqIterable;

        public RandomizedQueueIterator() {
            localPointer = pointer;
            rqIterable = (Item[]) new Object[localPointer];
            for (int i = 0; i < localPointer; i++) {
                rqIterable[i] = itemQueue[i];
            }
        } // end constructor

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove

        @Override
        public boolean hasNext() {
            return localPointer > 0;
        } // end hasNext

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int index = StdRandom.uniformInt(localPointer);
            Item item = rqIterable[index];
            rqIterable[index] = rqIterable[--localPointer];
            return item;
        } // end next
    } // end RandomQueueIterator

    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<>();

        StdOut.println("Empty: " + test.isEmpty());
        for (int i = 1; i <= 10; i++) {
            StdOut.println("Adding " + i);
            test.enqueue(i);
        }

        StdOut.println("List size: " + test.size());
        StdOut.println("Empty: " + test.isEmpty());

        StdOut.println("\n iterator: ");
        for (Object item : test) {
            StdOut.println(item);
        }

        StdOut.println("\n pop 3 elements at random: ");
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
    } // end main
} // end RandomizedQueue class
