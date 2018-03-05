package com.github.bohnman.core.lang.array.iterator;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implements an {@link java.util.Iterator Iterator} over any array.
 * <p>
 * The array can be either an array of object or of primitives. If you know
 * that you have an object array, the
 * {@link CoreObjectArrayIterator ObjectArrayIterator}
 * class is a better choice, as it will perform better.
 * <p>
 * The iterator implements a {@link #reset} method, allowing the reset of
 * the iterator back to the start if required.
 *
 * @version $Id: ArrayIterator.java 1494282 2013-06-18 20:09:15Z sebb $
 * @since 1.0
 */
@SuppressWarnings("PointlessBooleanExpression")
public class CoreArrayIterator<E> implements Iterator<E> {

    /**
     * The array to iterate over
     */
    final Object array;
    /**
     * The start index to loop from
     */
    final int startIndex;
    /**
     * The end index to loop to
     */
    final int endIndex;
    /**
     * The current iterator index
     */
    int index = 0;

    // Constructors
    // ----------------------------------------------------------------------

    /**
     * Constructs an ArrayIterator that will iterate over the values in the
     * specified array.
     *
     * @param array the array to iterate over.
     * @throws IllegalArgumentException if <code>array</code> is not an array.
     * @throws NullPointerException     if <code>array</code> is <code>null</code>
     */
    public CoreArrayIterator(final Object array) {
        this(array, 0);
    }

    /**
     * Constructs an ArrayIterator that will iterate over the values in the
     * specified array from a specific start index.
     *
     * @param array      the array to iterate over.
     * @param startIndex the index to start iterating at.
     * @throws IllegalArgumentException  if <code>array</code> is not an array.
     * @throws NullPointerException      if <code>array</code> is <code>null</code>
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public CoreArrayIterator(final Object array, final int startIndex) {
        this(array, startIndex, Array.getLength(array));
    }

    /**
     * Construct an ArrayIterator that will iterate over a range of values
     * in the specified array.
     *
     * @param array      the array to iterate over.
     * @param startIndex the index to start iterating at.
     * @param endIndex   the index to finish iterating at.
     * @throws IllegalArgumentException  if <code>array</code> is not an array.
     * @throws NullPointerException      if <code>array</code> is <code>null</code>
     * @throws IndexOutOfBoundsException if either index is invalid
     */
    public CoreArrayIterator(final Object array, final int startIndex, final int endIndex) {
        super();

        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.index = startIndex;

        final int len = Array.getLength(array);
        checkBound(startIndex, len, "start");
        checkBound(endIndex, len, "end");
        if (endIndex < startIndex) {
            throw new IllegalArgumentException("End index must not be less than start index.");
        }
    }

    /**
     * Checks whether the index is valid or not.
     *
     * @param bound the index to check
     * @param len   the length of the array
     * @param type  the index type (for error messages)
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    protected void checkBound(final int bound, final int len, final String type) {
        if (bound > len) {
            throw new ArrayIndexOutOfBoundsException(
                    "Attempt to make an ArrayIterator that " + type +
                            "s beyond the end of the array. "
            );
        }
        if (bound < 0) {
            throw new ArrayIndexOutOfBoundsException(
                    "Attempt to make an ArrayIterator that " + type +
                            "s before the start of the array. "
            );
        }
    }

    // Iterator interface
    //-----------------------------------------------------------------------

    /**
     * Returns true if there are more elements to return from the array.
     *
     * @return true if there is a next element to return
     */
    public boolean hasNext() {
        return index < endIndex;
    }

    /**
     * Returns the next element in the array.
     *
     * @return the next element in the array
     * @throws NoSuchElementException if all the elements in the array
     *                                have already been returned
     */
    @SuppressWarnings("unchecked")
    public E next() {
        if (hasNext() == false) {
            throw new NoSuchElementException();
        }
        return (E) Array.get(array, index++);
    }

    /**
     * Throws {@link UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException always
     */
    public void remove() {
        throw new UnsupportedOperationException("remove() method is not supported");
    }

    // Properties
    //-----------------------------------------------------------------------

    /**
     * Gets the array that this iterator is iterating over.
     *
     * @return the array this iterator iterates over.
     */
    public Object getArray() {
        return array;
    }

    /**
     * Gets the start index to loop from.
     *
     * @return the start index
     * @since 4.0
     */
    public int getStartIndex() {
        return this.startIndex;
    }

    /**
     * Gets the end index to loop to.
     *
     * @return the end index
     * @since 4.0
     */
    public int getEndIndex() {
        return this.endIndex;
    }

    /**
     * Resets the iterator back to the start index.
     */
    public void reset() {
        this.index = this.startIndex;
    }

}
