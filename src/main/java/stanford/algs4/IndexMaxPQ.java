package stanford.algs4;

/*************************************************************************
 *  Compilation:  javac IndexMaxPQ.java
 *
 *  Execution:    java IndexMaxPQ
 *
 *  Indexed PQ implementation using a binary heap.
 *
 *********************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

import stanford.stdlib.StdOut;
import stanford.stdlib.StdRandom;

public class IndexMaxPQ<Key extends Comparable<Key>> implements
        Iterable<Integer> {
    private int N; // number of elements on PQ
    private int[] pq; // binary heap using 1-based indexing
    private int[] qp; // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    private Key[] keys; // keys[i] = priority of i

    public IndexMaxPQ(int NMAX) {
        keys = (Key[]) new Comparable[NMAX + 1]; // make this of length NMAX??
        pq = new int[NMAX + 1];
        qp = new int[NMAX + 1]; // make this of length NMAX??
        for (int i = 0; i <= NMAX; i++)
            qp[i] = -1;
    }

    // is the priority queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // is the index k in the priority queue?
    public boolean contains(int k) {
        return qp[k] != -1;
    }

    // number of elements in the priority queue
    public int size() {
        return N;
    }

    // associate key with index k
    public void insert(int k, Key key) {
        if (contains(k))
            throw new RuntimeException("item is already in pq");
        N++;
        qp[k] = N;
        pq[N] = k;
        keys[k] = key;
        swim(N);
    }

    // return index of a maximal key
    public int maxIndex() {
        if (N == 0)
            throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    // return a maximal key
    public Key maxKey() {
        if (N == 0)
            throw new NoSuchElementException("Priority queue underflow");
        return keys[pq[1]];
    }

    // delete a maximal key and returns its associated index
    public int delMax() {
        if (N == 0)
            throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        exch(1, N--);
        sink(1);
        qp[min] = -1; // delete
        keys[pq[N + 1]] = null; // to help with garbage collection
        pq[N + 1] = -1; // not needed
        return min;
    }

    // return key associated with index k
    public Key keyOf(int k) {
        if (!contains(k))
            throw new NoSuchElementException("item is not in pq");
        else
            return keys[k];
    }

    // change the key associated with index k
    public void change(int k, Key key) {
        changeKey(k, key);
    }

    // change the key associated with index k
    public void changeKey(int k, Key key) {
        if (!contains(k))
            throw new NoSuchElementException("item is not in pq");
        keys[k] = key;
        swim(qp[k]);
        sink(qp[k]);
    }

    // increase the key associated with index k
    public void increaseKey(int k, Key key) {
        if (!contains(k))
            throw new NoSuchElementException("item is not in pq");
        if (keys[k].compareTo(key) >= 0)
            throw new RuntimeException("illegal increase");
        keys[k] = key;
        swim(qp[k]);
    }

    // decrease the key associated with index k
    public void decreaseKey(int k, Key key) {
        if (!contains(k))
            throw new NoSuchElementException("item is not in pq");
        if (keys[k].compareTo(key) <= 0)
            throw new RuntimeException("illegal increase");
        keys[k] = key;
        sink(qp[k]);
    }

    // delete the key associated with index k
    public void delete(int k) {
        if (!contains(k))
            throw new NoSuchElementException("item is not in pq");
        int index = qp[k];
        exch(index, N--);
        swim(index);
        sink(index);
        keys[k] = null;
        qp[k] = -1;
    }

    /**************************************************************
     * General helper functions
     **************************************************************/
    private boolean less(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) < 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    /**************************************************************
     * Heap helper functions
     **************************************************************/
    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(j, j + 1))
                j++;
            if (!less(k, j))
                break;
            exch(k, j);
            k = j;
        }
    }

    /***********************************************************************
     * Iterators
     **********************************************************************/

    /**
     * Return an iterator that iterates over all of the elements on the priority
     * queue in descending order.
     * <p>
     * The iterator doesn't implement <tt>remove()</tt> since it's optional.
     */
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {
        // create a new pq
        private IndexMaxPQ<Key> copy;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            copy = new IndexMaxPQ<Key>(pq.length - 1);
            for (int i = 1; i <= N; i++)
                copy.insert(pq[i], keys[pq[i]]);
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Integer next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return copy.delMax();
        }
    }

    public static void main(String[] args) {
        // insert a bunch of strings
        String[] strings = { "it", "was", "the", "best", "of", "times", "it",
                "was", "the", "worst" };

        IndexMaxPQ<String> pq = new IndexMaxPQ<String>(strings.length);
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        // print each key using the iterator
        for (int i : pq) {
            StdOut.println(i + " " + strings[i]);
        }

        StdOut.println();

        // increase or decrease the key
        for (int i = 0; i < strings.length; i++) {
            if (StdRandom.uniform() < 0.5)
                pq.increaseKey(i, strings[i] + strings[i]);
            else
                pq.decreaseKey(i, strings[i].substring(0, 1));
        }

        // delete and print each key
        while (!pq.isEmpty()) {
            String key = pq.maxKey();
            int i = pq.delMax();
            StdOut.println(i + " " + key);
        }
        StdOut.println();

        // reinsert the same strings
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        // delete them in random order
        int[] perm = new int[strings.length];
        for (int i = 0; i < strings.length; i++)
            perm[i] = i;
        StdRandom.shuffle(perm);
        for (int i = 0; i < perm.length; i++) {
            String key = pq.keyOf(perm[i]);
            pq.delete(perm[i]);
            StdOut.println(perm[i] + " " + key);
        }

    }
}
