package data_structures.prog_4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class BinarySearchTree<K extends Comparable<K>, V> implements DictionaryADT<K, V> {
    private int size;
    private long modCounter;
    private DictionaryNode<K, V> parent;

    class DictionaryNode<K, V> implements Comparable<DictionaryNode<K, V>> {
        private K key;
        private V value;
        private DictionaryNode<K, V> rightChild;
        private DictionaryNode<K, V> leftChild;

        public DictionaryNode(K k, V v) {
            key = k;
            value = v;
            leftChild = rightChild = null;
        }

        public int compareTo(DictionaryNode<K, V> obj) {
            if (obj.key != null)
                return ((Comparable<K>) key).compareTo(obj.key);
            return ((Comparable<V>) value).compareTo(obj.value);

        }
    } // end of DictionaryNode class

    public BinarySearchTree() {
        modCounter = 0;
        clear();
    }

    /**
     * 
     * @param key
     * @return boolean Returns true if the dictionary has an object identified by
     *         the key in it, otherwise false.
     */
    public boolean contains(K key) {
        return (getValue(key) != null);
    }

    /**
     * 
     * @param key
     * @param value
     * @return boolean Adds the given key/value pair to the dictionary. Returns
     *         false if the dictionary is full, or if the key is a duplicate.
     *         Returns true if the addition succeeded.
     */
    public boolean add(K key, V value) {
        if (find(key, null) != null)
            return false;
        if (parent == null)
            parent = new DictionaryNode<>(key, value);
        else
            insert(key, value, parent, null, false);
        size++;
        modCounter++;
        return true;
    }

    private void insert(K k, V v, DictionaryNode<K, V> n, DictionaryNode<K, V> parent, boolean wentLeft) {
        if (n == null) {
            if (wentLeft)
                parent.leftChild = new DictionaryNode<>(k, v);
            else
                parent.rightChild = new DictionaryNode<>(k, v);
        } else if ((k).compareTo(n.key) < 0)
            insert(k, v, n.leftChild, n, true);
        else
            insert(k, v, n.rightChild, n, false);
    }

    /**
     * 
     * @param key
     * @return boolean Deletes the key/value pair identified by the key parameter.
     *         Returns true if the key/value pair was found and removed, otherwise
     *         false.
     */
    public boolean delete(K key) {
        DictionaryNode<K, V> deleteNode = find(key, null);

        if (deleteNode == null)
            return false;

        if (deleteNode.leftChild == null && deleteNode.rightChild == null) {
            DictionaryNode<K, V> pNode = getParent(deleteNode.key);

            if (pNode == null) {
                parent = null;
                size--;
                modCounter++;
                return true;
            }

            if (pNode.leftChild != null && deleteNode.compareTo(pNode.leftChild) == 0)
                pNode.leftChild = null;
            else
                pNode.rightChild = null;

            size--;
            modCounter++;
            return true;

        } else if (deleteNode.leftChild != null && deleteNode.rightChild != null) {
            DictionaryNode<K, V> successor = getSuccessor(deleteNode);
            DictionaryNode<K, V> parentNode = getParent(deleteNode.key);

            if (parentNode == null) {
                successor.leftChild = parent.leftChild;
                successor.rightChild = parent.rightChild;
                parent = successor;

                size--;
                modCounter++;
                return true;
            }

            successor.rightChild = deleteNode.rightChild;
            successor.leftChild = deleteNode.leftChild;
            if (parentNode.leftChild != null && deleteNode.compareTo(parentNode.leftChild) == 0)
                parentNode.leftChild = successor;
            else
                parentNode.rightChild = successor;

            size--;
            modCounter++;
            return true;

        } else if (deleteNode.leftChild != null || deleteNode.rightChild != null) {
            DictionaryNode<K, V> parentNode = getParent(deleteNode.key);

            if (parentNode == null) {
                if (parent.leftChild != null)
                    parent = parent.leftChild;
                else
                    parent = parent.rightChild;

                size--;
                modCounter++;
                return true;
            }

            if (deleteNode.leftChild != null) {
                if (parentNode.leftChild != null && deleteNode.compareTo(parentNode.leftChild) == 0)
                    parentNode.leftChild = deleteNode.leftChild;
                else
                    parentNode.rightChild = deleteNode.leftChild;

            } else {
                if (parentNode.leftChild != null && deleteNode.compareTo(parentNode.leftChild) == 0)
                    parentNode.leftChild = deleteNode.rightChild;
                else
                    parentNode.rightChild = deleteNode.rightChild;
            }

            modCounter++;
            size--;
            return true;
        }

        return false;
    }

    private DictionaryNode<K, V> getSuccessor(DictionaryNode<K, V> root) {

        if (root.rightChild == null || root.leftChild == null)
            return null;

        DictionaryNode<K, V> tempNode = root.rightChild;

        if (tempNode.leftChild == null) {
            if (tempNode.rightChild != null)
                root.rightChild = tempNode.rightChild;
            else
                root.rightChild = null;
            return tempNode;
        }
        while (tempNode.leftChild != null)
            tempNode = tempNode.leftChild;

        DictionaryNode<K, V> parentNode = getParent(tempNode.key);

        if (tempNode.rightChild != null)
            parentNode.leftChild = tempNode.rightChild;
        else if (parentNode.compareTo(parent) != 0)
            parentNode.leftChild = null;

        return tempNode;
    }

    /**
     * 
     * @param key
     * @return V Returns the value associated with the parameter key. Returns null
     *         if the key is not found or the dictionary is empty.
     */
    public V getValue(K key) {
        DictionaryNode<K, V> temp = find(key, null);
        return (temp != null) ? temp.value : null;
    }

    /**
     * 
     * @param value
     * @return K Returns the key associated with the parameter value. Returns null
     *         if the value is not found in the dictionary. If more than one key
     *         exists that matches the given value, returns the first one found.
     */
    public K getKey(V value) {
        DictionaryNode<K, V> temp = find(null, value);
        return (temp != null) ? temp.key : null;
    }

    private DictionaryNode<K, V> getParent(K key) {
        DictionaryNode<K, V> searchNode = parent;
        DictionaryNode<K, V> compNode = new DictionaryNode<>(key, null);
        DictionaryNode<K, V> root = null;

        while (searchNode != null && compNode.compareTo(searchNode) != 0) {
            if (compNode.compareTo(searchNode) > 0) {
                root = searchNode;
                searchNode = searchNode.rightChild;
            } else {
                root = searchNode;
                searchNode = searchNode.leftChild;
            }
        }
        return root;
    }

    private DictionaryNode<K, V> find(K key, V value) {

        DictionaryNode<K, V> searchNode = parent;
        DictionaryNode<K, V> compNode = new DictionaryNode<>(key, value);

        if (key != null) {
            while (searchNode != null && compNode.compareTo(searchNode) != 0) {
                if (compNode.compareTo(searchNode) > 0)
                    searchNode = searchNode.rightChild;
                else
                    searchNode = searchNode.leftChild;
            }
            return searchNode;
        } else {
            Iterator<K> keys = new keysIteratorHelper<>();
            while (keys.hasNext()) {
                DictionaryNode<K, V> temp = find(keys.next(), null);
                if (temp.compareTo(compNode) == 0)
                    return temp;
            }
            return null;
        }
    }

    /**
     * 
     * @return int Returns the number of key/value pairs currently stored in the
     *         dictionary.
     */
    public int size() {
        return size;
    }

    /**
     * 
     * @return boolean Returns true if the dictionary is at max capacity.
     */
    public boolean isFull() {
        return false;
    }

    /**
     * 
     * @return boolean Returns true if the dictionary is empty.
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Returns the Dictionary object to an empty state
     */
    public void clear() {
        size = 0;
        parent = null;
    }

    /**
     * 
     * @return Iterator Returns an iterator of the keys in the dictionary, in
     *         ascending sorted order. The iterator must be fail-fast.
     */
    public Iterator keys() {
        return new keysIteratorHelper();
    }

    /**
     * 
     * @return Iterator Returns an Iterator of the values in the dictionary, The
     *         order of the values must match the order of the keys. The iterator
     *         must be fail-fast.
     */
    public Iterator values() {
        return new valuesIteratorHelper();
    }

    public class keysIteratorHelper<K> extends IteratorHelper<K> {

        public K next() {
            if (modCheck != modCounter)
                throw new ConcurrentModificationException();

            DictionaryNode<K, V> node = (DictionaryNode<K, V>) iteratorNode;
            if (iteratorNode.rightChild != null) {
                iteratorNode = iteratorNode.rightChild;
                while (iteratorNode.leftChild != null)
                    iteratorNode = iteratorNode.leftChild;
                return node.key;

            } else {
                while (true) {
                    if (getParent(iteratorNode.key) == null) {
                        iteratorNode = null;
                        return node.key;
                    }
                    if (getParent(iteratorNode.key).leftChild == iteratorNode) {
                        iteratorNode = getParent(iteratorNode.key);
                        return node.key;
                    }
                    iteratorNode = getParent(iteratorNode.key);
                }
            }
        }

    } // end of keysIteratorHelper class

    public class valuesIteratorHelper<V> extends IteratorHelper<V> {

        public V next() {
            if (modCheck != modCounter)
                throw new ConcurrentModificationException();

            DictionaryNode<K, V> node = (DictionaryNode<K, V>) iteratorNode;
            if (iteratorNode.rightChild != null) {
                iteratorNode = iteratorNode.rightChild;
                while (iteratorNode.leftChild != null)
                    iteratorNode = iteratorNode.leftChild;
                return node.value;
            } else {
                while (true) {
                    if (getParent(iteratorNode.key) == null) {
                        iteratorNode = null;
                        return node.value;
                    }
                    if (getParent(iteratorNode.key).leftChild == iteratorNode) {
                        iteratorNode = getParent(iteratorNode.key);
                        return node.value;
                    }
                    iteratorNode = getParent(iteratorNode.key);
                }
            }
        }

    } // end of valuesIteratorHelper

    abstract class IteratorHelper<E> implements Iterator<E> {
        protected DictionaryNode<K, V> iteratorNode;
        protected long modCheck;

        public IteratorHelper() {
            iteratorNode = parent;
            modCheck = modCounter;

            if (iteratorNode == null)
                return;
            while (iteratorNode.leftChild != null)
                iteratorNode = iteratorNode.leftChild;
        }

        public boolean hasNext() {
            return iteratorNode != null;
        }

        public abstract E next();

    } // end of IteratorHelper class
} // end of BinarySearchTree