package data_structures.prog_1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E> {

    private E[] storage;
    private int front;
    private int rear;
    private int maxSize;
    private int currentSize;

    public ArrayLinearList() {
        this(DEFAULT_MAX_CAPACITY);
    }

    public ArrayLinearList(int maxCapacity) {
        currentSize = front = rear = 0;
        maxSize = maxCapacity;
        storage = (E[]) new Object[maxSize];
    }

    /**
     * 
     * @param obj
     * @return boolean Successful add to beginning of array.
     */
    public boolean addFirst(E obj) {
        if (isFull())
            return false;

        if (currentSize != 0) {
            front--;
            if (front < 0)
                front = maxSize - 1;
        }
        storage[front] = obj;
        currentSize++;
        return true;
    }

    /**
     * 
     * @param obj
     * @return boolean Successful add to end of array.
     */
    public boolean addLast(E obj) {
        if (isFull())
            return false;

        if (currentSize != 0) {
            rear++;
            if (rear == maxSize)
                rear = 0;
        }
        storage[rear] = obj;
        currentSize++;
        return true;
    }

    /**
     * 
     * @return E The first element in the array is returned.
     */
    public E removeFirst() {
        if (isEmpty())
            return null;

        E temp = storage[front];
        if (front == rear)
            front = rear = 0;
        else {
            front++;
            if (front == maxSize)
                front = 0;
        }
        currentSize--;
        return temp;
    }

    /**
     * 
     * @return E The last element in the array is returned.
     */
    public E removeLast() {
        if (isEmpty())
            return null;

        E temp = storage[rear];
        if (rear == front)
            rear = front = 0;
        else {
            rear--;
            if (rear == -1)
                rear = maxSize - 1;
        }
        currentSize--;
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
        if (find(obj) == null)
            return null;

        int where = -1;

        int counter = 0;

        int tempVal = front;

        while (counter <= currentSize) {
            if (((Comparable<E>) obj).compareTo(storage[tempVal]) == 0) {
                where = tempVal;
                break;
            }
            tempVal++;
            if (tempVal == maxSize)
                tempVal = 0;
            counter++;
        }

        E temp = storage[where];

        if (temp == storage[front])
            return removeFirst();
        else if (temp == storage[rear])
            return removeLast();
        else {
            while (where != rear) {
                if (where + 1 == maxSize) {
                    storage[where] = storage[0];
                    where = 0;
                }
                storage[where] = storage[where + 1];
                where++;
            }

            currentSize--;
            rear--;
            if (rear == -1)
                rear = maxSize - 1;
        }

        return temp;
    }

    /**
     * 
     * @return E Returns the first element in the list, null if the list is empty.
     *         The list is not modified.
     */
    public E peekFirst() {
        return isEmpty() ? null : storage[front];
    }

    /**
     * 
     * @return E Returns the last element in the list, null if the list is empty.
     *         The list is not modified.
     */
    public E peekLast() {
        return isEmpty() ? null : storage[rear];
    }

    /**
     * 
     * @param obj
     * @return boolean Returns true if the parameter object obj is in the list,
     *         false otherwise. The list is not modified.
     */
    public boolean contains(E obj) {
        return find(obj) != null;
    }

    /**
     * 
     * @param obj
     * @return E Returns the element matching the object obj if it is in the list,
     *         null otherwise. In the case of duplicates, this method returns the
     *         element closest to the front. The list is not modified.
     */
    public E find(E obj) {
        for (E temp : this) {
            if (((Comparable<E>) obj).compareTo(temp) == 0)
                return temp;
        }
        return null;
    }

    /**
     * The list is returned to an empty state.
     */
    public void clear() {
        front = rear = currentSize = 0;

    }

    /**
     * 
     * @return boolean Returns true if the list is empty, otherwise false.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * 
     * @return boolean Returns true if the list is full, otherwise false.
     */
    public boolean isFull() {
        return currentSize == maxSize;
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
        private int count, index;

        public IteratorHelper() {
            index = front;
            count = 0;
        }

        public boolean hasNext() {
            return count != currentSize;
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            E temp = storage[index++];

            if (index == maxSize)
                index = 0;
            count++;
            return temp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}