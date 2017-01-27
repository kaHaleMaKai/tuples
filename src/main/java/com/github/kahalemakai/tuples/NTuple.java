package com.github.kahalemakai.tuples;

/**
 * Allow for retrieval of arbitrary tuple elements.
 */
public interface NTuple {

    /**
     * Retrieve a tuple element by index.
     *
     * @param index
     *     the index to lookup
     * @return
     *     the associated tuple element
     * @throws IndexOutOfBoundsException
     *     should be thrown if index is not in
     *     range (0, ..., (tuple size) - 1)
     */
    Object get(int index) throws IndexOutOfBoundsException;

    /**
     * Check if a {@code Tuple} contains an object.
     *
     * @param o
     *     object to be looked up
     * @return
     *     whether object is present or not
     */
    boolean contains(Object o);
}
