package iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator — a custom ring buffer exposing traversal via Iterable, plus a lazy
 * range iterator that never materializes a list.
 * Run: javac IteratorDemo.java && java iterator.IteratorDemo
 */
public class IteratorDemo {

    // A custom aggregate that implements Iterable so for-each just works.
    static class RingBuffer<T> implements Iterable<T> {
        private final Object[] data; private int size; private int start;
        RingBuffer(int capacity) { data = new Object[capacity]; }
        void add(T item) {
            data[(start + size) % data.length] = item;
            if (size < data.length) size++; else start = (start + 1) % data.length;
        }
        @SuppressWarnings("unchecked")
        public Iterator<T> iterator() {
            return new Iterator<>() {
                private int i = 0;
                public boolean hasNext() { return i < size; }
                public T next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    return (T) data[(start + i++) % data.length];
                }
            };
        }
    }

    // A lazy iterator: generates values on demand, no backing collection.
    static Iterable<Integer> range(int from, int toExclusive) {
        return () -> new Iterator<>() {
            private int cur = from;
            public boolean hasNext() { return cur < toExclusive; }
            public Integer next() { return cur++; }
        };
    }

    public static void main(String[] args) {
        RingBuffer<String> rb = new RingBuffer<>(3);
        rb.add("a"); rb.add("b"); rb.add("c"); rb.add("d"); // "a" evicted
        for (String s : rb) System.out.print(s + " ");
        System.out.println();

        int sum = 0;
        for (int n : range(1, 6)) sum += n;  // lazy 1..5
        System.out.println("sum 1..5 = " + sum);
    }
}
