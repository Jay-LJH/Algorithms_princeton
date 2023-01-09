import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    public RandomizedQueue() {
        size = 0;
        length = 8;
        array = (Item[]) new Object[8];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == length) {
            resize(length * 2);
        }
        array[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        if (length > 16 && size < (length / 4)) {
            resize(length / 2);
        }
        int n = StdRandom.uniformInt(size);
        Item ans = array[n];
        size--;
        array[n] = array[size];
        return ans;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int n = StdRandom.uniformInt(size);
        return array[n];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        public RandomIterator() {
            a = (Item[]) new Object[size];
            arraycopy(array, 0, a, 0, size);
            for (int i = 0; i < size; i++) {
                int n = StdRandom.uniformInt(size);
                Item temp = a[n];
                a[n] = a[i];
                a[i] = temp;
            }
        }

        @Override
        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            return a[i++];
        }

        private Item[] a;
        private int i;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> t = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            t.enqueue(i);
        }
        Iterator<Integer> i = t.iterator();
        while (i.hasNext()) {
            System.out.print(i.next());
        }
        System.out.print("\n");
        i = t.iterator();
        while (i.hasNext()) {
            System.out.print(i.next());
        }
        System.out.print("\n");
        System.out.println(t.dequeue());
        System.out.println(t.sample());
        i = t.iterator();
        while (i.hasNext()) {
            System.out.print(i.next());
        }
        System.out.print("\n");
    }

    private void resize(int length) {
        Item[] temp = (Item[]) new Object[length];
        arraycopy(array, 0, temp, 0, size);
        array = temp;
        this.length = array.length;
    }

    private void arraycopy(Item[] src, int t1, Item[] dst, int t2, int size) {
        for (int i = 0; i < size; i++) {
            dst[t2 + i] = src[t1 + i];
        }
    }

    private Item[] array;
    private int size;
    private int length;
}
