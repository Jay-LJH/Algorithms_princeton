import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    public Deque() {
        array = (Item[]) new Object[8];
        start = end = size = 0;
        length = 8;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (start == 0) {
            Item[] temp = (Item[]) new Object[length * 2];
            arraycopy(array, 0, temp, length, size);
            array = temp;
            start = length - 1;
            size++;
            end += length;
            length = array.length;
            array[start] = item;
        } else {
            start--;
            size++;
            array[start] = item;
        }
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (end == length) {
            Item[] temp = (Item[]) new Object[length * 2];
            arraycopy(array, start, temp, start, size);
            array = temp;
            array[end] = item;
            size++;
            end++;
            length = array.length;
        } else {
            array[end] = item;
            end++;
            size++;
        }
    }

    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        if (size > 0) {
            Item res = array[start];
            start++;
            size--;
            if (size < (length) / 4) {
                Item[] temp = (Item[]) new Object[length / 2];
                arraycopy(array, start, temp, start % ((length) / 4), size);
                array = temp;
                start = start % ((length) / 4);
                end = size + start;
                length = array.length;
            }
            return res;
        }
        return null;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        if (size > 0) {
            Item res = array[end - 1];
            end--;
            size--;
            if (size < (length) / 4) {
                Item[] temp = (Item[]) new Object[length / 2];
                arraycopy(array, start, temp, start % ((length) / 4), size);
                array = temp;
                start = start % ((length) / 4);
                end = size + start;
                length = array.length;
            }
            return res;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public Iterator<Item> iterator() {
        return new Dequeiterator();
    }
    private void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(array[start + i] + " ");
        }
        System.out.print("\n");
    }
    private class Dequeiterator implements Iterator<Item> {
        public boolean hasNext() {
            return i < size + start;
        }

        public Item next() {
            return array[i++];
        }

        private int i = start;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public static void main(String[] args) {
        Deque<Integer> t = new Deque<>();
        for (int i = 0; i < 10; i++) {
            t.addFirst(i);
            t.addLast(i + 10);
        }
        Iterator<Integer> i = t.iterator();
        while (i.hasNext()) {
            System.out.print(i.next());
        }
        t.printDeque();
        System.out.print("\n");
        System.out.println(t.removeFirst());
        System.out.println(t.removeLast());
        i = t.iterator();
        while (i.hasNext()) {
            System.out.print(i.next());
        }
        System.out.print("\n");
    }

    private void arraycopy(Item[] src, int t1, Item[] dst, int t2, int size) {
        for (int i = 0; i < size; i++) {
            dst[t2 + i] = src[t1 + i];
        }
    }

    private Item[] array;
    private int start;
    private int end;
    private int size;
    private int length;
}
