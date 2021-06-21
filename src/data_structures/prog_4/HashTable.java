package data_structures.prog_4;

import java.util.Iterator;
import java.util.ConcurrentModificationException;

public class Hashtable<K extends Comparable<K>, V extends Comparable<V>> implements DictionaryADT<K, V> {
    private LinearList<DictionaryNode<K, V>>[] list;
    private int currSize, tableSize, maxSize, modCounter;

    class DictionaryNode<K, V> implements Comparable<DictionaryNode<K, V>> {
        K key;
        V value;

        public DictionaryNode(K k, V v) {
            key = k;
            value = v;
        }

        public int compareTo(DictionaryNode<K, V> node) {
            return ((Comparable<K>) key).compareTo(node.key);
        }
    }

    public Hashtable(int n) {
        currSize = 0;
        maxSize = n;
        modCounter = 0;
        tableSize = (int) (maxSize * 1.3f);
        list = new LinearList[tableSize];
        for (int i = 0; i < tableSize; i++)
            list[i] = new LinearList<>();
    }

    public boolean contains(K key) {
        DictionaryNode<K, V> tmp = new DictionaryNode<>(key, null);
        return list[getIndex(key)].contains(tmp);
    }

    public boolean add(K k, V v) {
        if (isFull())
            return false;
        DictionaryNode<K, V> tmp = new DictionaryNode<>(k, v);
        DictionaryNode<K, V> returnValue = list[getIndex(k)].find(tmp); // checks for duplicate
        if (returnValue != null)
            return false;
        list[getIndex(k)].addLast(tmp);
        currSize++;
        modCounter++;
        return true;
    }

    public boolean delete(K key) {
        if (contains(key)) {
            DictionaryNode<K, V> tmp = new DictionaryNode<>(key, null);
            list[getIndex(key)].remove(tmp);
            currSize--;
            modCounter++;
            return true;
        }
        return false;
    }

    public V getValue(K key) {
        int index = getIndex(key);
        DictionaryNode<K, V> tmp = list[index].find(new DictionaryNode<>(key, null));
        if (tmp == null)
            return null;
        return tmp.value;
    }

    public K getKey(V value) {
        for (int i = 0; i < tableSize; i++) {
            for (DictionaryNode<K, V> tmp : list[i]) {
                if ((tmp.value).compareTo(value) == 0)
                    return tmp.key;
            }
        }
        return null;
    }

    public int size() {
        return currSize;
    }

    public boolean isFull() {
        return currSize == maxSize;
    }

    public boolean isEmpty() {
        return currSize == 0;
    }

    public void clear() {
        for (int i = 0; i < tableSize; i++)
            list[i].clear();
        currSize = 0;
        modCounter = 0;
    }

    public Iterator<K> keys() {
        return new KeyIteratorHelper<>();
    }

    public Iterator<V> values() {
        return new ValueIteratorHelper<>();
    }

    private int getIndex(K key) {
        return (key.hashCode() & 0x7FFFFFFF) % tableSize;
    }

    abstract class IteratorHelper<E> implements Iterator<E> {
        protected DictionaryNode<K, V>[] nodes;
        protected int idx;
        protected long modCheck;

        public IteratorHelper() {
            nodes = new DictionaryNode[currSize];
            idx = 0;
            int j = 0;
            modCheck = modCounter;
            for (int i = 0; i < tableSize; i++)
                for (DictionaryNode n : list[i])
                    nodes[j++] = n;

            nodes = shellSort(nodes);
        }

        public boolean hasNext() {
            if (modCheck != modCounter)
                throw new ConcurrentModificationException();
            return idx < currSize;
        }

        private DictionaryNode<K, V>[] shellSort(DictionaryNode<K, V> n[]) {

            if (n.length < 2)
                return n;
            int in = 0, out = 0, h = 1;
            DictionaryNode<K, V> temp;
            int size = n.length;

            while (h <= size / 3)
                h = h * 3 + 1;
            while (h > 0) {
                for (out = h; out < size; out++) {
                    temp = n[out];
                    in = out;
                    while (in > h - 1 && ((Comparable) n[in - h]).compareTo(temp) >= 0) {
                        n[in] = n[in - h];
                        in -= h;
                    }
                    n[in] = temp;

                } // end for
                h = (h - 1) / 3;
            } // end while

            return n;
        }

        public abstract E next();

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    class KeyIteratorHelper<K> extends IteratorHelper<K> {
        public KeyIteratorHelper() {
            super();
        }

        public K next() {
            return (K) nodes[idx++].key;
        }
    }

    class ValueIteratorHelper<V> extends IteratorHelper<V> {
        public ValueIteratorHelper() {
            super();
        }

        public V next() {
            return (V) nodes[idx++].value;
        }
    }
}
