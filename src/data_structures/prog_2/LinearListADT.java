package data_structures.prog_2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface LinearListADT<E> extends Iterable<E> {

    /**
     * Default size of the array
     */
    public static final int DEFAULT_MAX_CAPACITY = 100;

    /**
     * 
     * @param obj
     * @return boolean Successful add to beginning of array.
     */
    public boolean addFirst(E obj);

    /**
     * 
     * @param obj
     * @return boolean Successful add to end of array.
     */
    public boolean addLast(E obj);

    /**
     * 
     * @return E The first element in the array is returned.
     */
    public E removeFirst();

    /**
     * 
     * @return E The last element in the array is returned.
     */
    public E removeLast();

    /**
     * 
     * @param obj
     * @return E Removes and returns the first matching element found when
     *         traversing through the list. Note that you may have to shift elements
     *         to fill in the slot where the deleted element was located.
     */
    public E remove(E obj);

    /**
     * 
     * @return E Returns the first element in the list, null if the list is empty.
     *         The list is not modified.
     */
    public E peekFirst();

    /**
     * 
     * @return E Returns the last element in the list, null if the list is empty.
     *         The list is not modified.
     */
    public E peekLast();

    /**
     * 
     * @param obj
     * @return boolean Returns true if the parameter object obj is in the list,
     *         false otherwise. The list is not modified.
     */
    public boolean contains(E obj);

    /**
     * 
     * @param obj
     * @return E Returns the element matching the object obj if it is in the list,
     *         null otherwise. In the case of duplicates, this method returns the
     *         element closest to the front. The list is not modified.
     */
    public E find(E obj);

    /**
     * The list is returned to an empty state.
     */
    public void clear();

    /**
     * 
     * @return boolean Returns true if the list is empty, otherwise false.
     */
    public boolean isEmpty();

    /**
     * 
     * @return boolean Returns true if the list is full, otherwise false.
     */
    public boolean isFull();

    /**
     * 
     * @return int Returns the number of Objects currently in the list.
     */
    public int size();

    /**
     * Returns an Iterator of the values in the list, presented in the same order as
     * the underlying order of the list (front first, rear last).
     */
    public Iterator<E> iterator();

}
