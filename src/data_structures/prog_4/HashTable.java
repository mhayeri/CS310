package data_structures.prog_4;

import java.util.Iterator;
import java.util.ConcurrentModificationException;

public class HashTable<K extends Comparable<K>, V extends Comparable<V>> implements DictionaryADT<K, V> {
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

    public HashTable(int n) {
        currSize = 0;
        maxSize = n;
        modCounter = 0;
        tableSize = (int) (maxSize * 1.3f);
        list = new LinearList[tableSize];
        for (int i = 0; i < tableSize; i++)
            list[i] = new LinearList<>();
    }

    /**
     * 
     * @param key
     * @return boolean Returns true if the dictionary has an object identified by
     *         the key in it, otherwise false.
     */
    public boolean contains(K key) {
        DictionaryNode<K, V> tmp = new DictionaryNode<>(key, null);
        return list[getIndex(key)].contains(tmp);
    }

    /**
     * 
     * @param key
     * @param value
     * @return boolean Adds the given key/value pair to the dictionary. Returns
     *         false if the dictionary is full, or if the key is a duplicate.
     *         Returns true if the addition succeeded.
     */
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

    /**
     * 
     * @param key
     * @return boolean Deletes the key/value pair identified by the key parameter.
     *         Returns true if the key/value pair was found and removed, otherwise
     *         false.
     */
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

    /**
     * 
     * @param key
     * @return V Returns the value associated with the parameter key. Returns null
     *         if the key is not found or the dictionary is empty.
     */
    public V getValue(K key) {
        int index = getIndex(key);
        DictionaryNode<K, V> tmp = list[index].find(new DictionaryNode<>(key, null));
        if (tmp == null)
            return null;
        return tmp.value;
    }

    /**
     * 
     * @param value
     * @return K Returns the key associated with the parameter value. Returns null
     *         if the value is not found in the dictionary. If more than one key
     *         exists that matches the given value, returns the first one found.
     */
    public K getKey(V value) {
        for (int i = 0; i < tableSize; i++) {
            for (DictionaryNode<K, V> tmp : list[i]) {
                if ((tmp.value).compareTo(value) == 0)
                    return tmp.key;
            }
        }
        return null;
    }

    /**
     * 
     * @return int Returns the number of key/value pairs currently stored in the
     *         dictionary.
     */
    public int size() {
        return currSize;
    }

    /**
     * 
     * @return boolean Returns true if the dictionary is at max capacity.
     */
    public boolean isFull() {
        return currSize == maxSize;
    }

    /**
     * 
     * @return boolean Returns true if the dictionary is empty.
     */
    public boolean isEmpty() {
        return currSize == 0;
    }

    /**
     * Returns the Dictionary object to an empty state
     */
    public void clear() {
        for (int i = 0; i < tableSize; i++)
            list[i].clear();
        currSize = 0;
        modCounter = 0;
    }

    /**
     * 
     * @return Iterator Returns an iterator of the keys in the dictionary, in
     *         ascending sorted order. The iterator must be fail-fast.
     */
    public Iterator<K> keys() {
        return new KeyIteratorHelper<>();
    }

    /**
     * 
     * @return Iterator Returns an Iterator of the values in the dictionary, The
     *         order of the values must match the order of the keys. The iterator
     *         must be fail-fast.
     */
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
