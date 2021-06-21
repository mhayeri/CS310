package data_structures.prog_4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface DictionaryADT<K extends Comparable<K>, V> {

    /**
     * 
     * @param key
     * @return boolean Returns true if the dictionary has an object identified by
     *         the key in it, otherwise false.
     */
    boolean contains(K key);

    /**
     * 
     * @param key
     * @param value
     * @return boolean Adds the given key/value pair to the dictionary. Returns
     *         false if the dictionary is full, or if the key is a duplicate.
     *         Returns true if the addition succeeded.
     */
    boolean add(K key, V value);

    /**
     * 
     * @param key
     * @return boolean Deletes the key/value pair identified by the key parameter.
     *         Returns true if the key/value pair was found and removed, otherwise
     *         false.
     */
    boolean delete(K key);

    /**
     * 
     * @param key
     * @return V Returns the value associated with the parameter key. Returns null
     *         if the key is not found or the dictionary is empty.
     */
    V getValue(K key);

    /**
     * 
     * @param value
     * @return K Returns the key associated with the parameter value. Returns null
     *         if the value is not found in the dictionary. If more than one key
     *         exists that matches the given value, returns the first one found.
     */
    K getKey(V value);

    /**
     * 
     * @return int Returns the number of key/value pairs currently stored in the
     *         dictionary.
     */
    int size();

    /**
     * 
     * @return boolean Returns true if the dictionary is at max capacity.
     */
    boolean isFull();

    /**
     * 
     * @return boolean Returns true if the dictionary is empty.
     */
    boolean isEmpty();

    /**
     * Returns the Dictionary object to an empty state
     */
    void clear();

    /**
     * 
     * @return Iterator Returns an iterator of the keys in the dictionary, in
     *         ascending sorted order. The iterator must be fail-fast.
     */
    Iterator keys();

    /**
     * 
     * @return Iterator Returns an Iterator of the values in the dictionary, The
     *         order of the values must match the order of the keys. The iterator
     *         must be fail-fast.
     */
    Iterator values();

}
