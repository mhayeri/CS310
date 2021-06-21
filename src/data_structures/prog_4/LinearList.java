package data_structures.prog_4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinearList<E extends Comparable<E>> implements LinearListADT<E> {

    private class Node<T> {
        T data;
        Node<T> next, previous;

        public Node(T obj) {
            data = obj;
            next = previous = null;
        }
    }

    private Node<E> head, tail;
    private int currentSize;
    private long modCounter;

    public LinearList() {
        head = tail = null;
        currentSize = 0;
        modCounter = 0;
    }

    /**
     * 
     * @param obj
     * @return boolean Successful add to beginning of list.
     */
    public boolean addFirst(E obj) {
        Node<E> node = new Node<>(obj);
        if (head == null)
            head = tail = node;
        else {
            node.next = head;
            head.previous = node;
            head = node;
        }
        currentSize++;
        modCounter++;
        return true;
    }

    /**
     * 
     * @param obj
     * @return boolean Successful add to end of list.
     */
    public boolean addLast(E obj) {
        Node<E> node = new Node<>(obj);
        if (head == null)
            head = tail = node;
        else {
            node.previous = tail;
            tail.next = node;
            tail = node;
        }
        currentSize++;
        modCounter++;
        return true;
    }

    /**
     * 
     * @return E The first element in the list is returned.
     */
    public E removeFirst() {
        if (head == null)
            return null;
        E temp = head.data;
        if (head == tail)
            head = tail = null;
        else {
            head = head.next;
            head.previous = null;
        }
        currentSize--;
        modCounter++;
        return temp;
    }

    /**
     * 
     * @return E The last element in the list is returned.
     */
    public E removeLast() {
        if (head == null)
            return null;

        E temp = tail.data;
        if (head == tail)
            head = tail = null;
        else {
            tail = tail.previous;
            tail.next = null;
        }
        currentSize--;
        modCounter++;
        return temp;

    }

    /**
     * 
     * @param obj
     * @return E Removes and returns the first matching element found when
     *         traversing through the list. Note that you may have to shift elements
     *         to fill in the slot where the deleted element was located.
     */
    public E remove(E obj) {
        Node<E> current = head;
        while (current != null && obj.compareTo(current.data) != 0)
            current = current.next;

        if (current == null)
            return null;
        if (current == head)
            return removeFirst();
        if (current == tail)
            return removeLast();
        current.previous.next = current.next;
        current.next.previous = current.previous;
        currentSize--;
        modCounter++;

        return current.data;
    }

    /**
     * 
     * @return E Returns the first element in the list, null if the list is empty.
     *         The list is not modified.
     */
    public E peekFirst() {
        return ((head == null) ? null : head.data);
    }

    /**
     * 
     * @return E Returns the last element in the list, null if the list is empty.
     *         The list is not modified.
     */
    public E peekLast() {
        return ((head == null) ? null : tail.data);
    }

    /**
     * 
     * @param obj
     * @return boolean Returns true if the parameter object obj is in the list,
     *         false otherwise. The list is not modified.
     */
    public boolean contains(E obj) {
        return (find(obj) != null);
    }

    /**
     * 
     * @param obj
     * @return E Returns the element matching the object obj if it is in the list,
     *         null otherwise. In the case of duplicates, this method returns the
     *         element closest to the front. The list is not modified.
     */
    public E find(E obj) {
        Node<E> current = head;
        while (current != null) {
            if (obj.compareTo(current.data) == 0)
                return current.data;
            current = current.next;
        }
        return null;
    }

    /**
     * The list is returned to an empty state.
     */
    public void clear() {
        head = tail = null;
        currentSize = 0;
        modCounter++;
    }

    /**
     * 
     * @return boolean Returns true if the list is empty, otherwise false.
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * 
     * @return boolean Returns true if the list is full, otherwise false.
     */
    public boolean isFull() {
        return false;
    }

    /**
     * 
     * @return int Returns the number of Objects currently in the list.
     */
    public int size() {
        return currentSize;
    }

    /**
     * Returns an Iterator of the values in the list, presented in the same order as
     * the underlying order of the list (front first, rear last).
     */
    public Iterator<E> iterator() {
        return new IteratorHelper();
    }

    class IteratorHelper implements Iterator<E> {
        private Node<E> iterPointer;
        private long modCheck;

        public IteratorHelper() {
            modCheck = modCounter;
            iterPointer = head;
        }

        public boolean hasNext() {
            if (modCheck != modCounter)
                throw new ConcurrentModificationException();
            return iterPointer != null;
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            E temp = iterPointer.data;
            iterPointer = iterPointer.next;
            return temp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
