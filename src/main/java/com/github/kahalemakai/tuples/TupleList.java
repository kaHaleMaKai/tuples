/*
Tuples – a type-safe tuple and tuple list implementation
Copyright (C) 2016  Lars Winderling

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.github.kahalemakai.tuples;

import java.util.*;

/**
 * A type-safe, compile-time checked list of tuples.
 */
public interface TupleList<T, U> extends List<Tuple<T, U>> {

    /**
     * Convert elements from an iterable of even length into tuples and put them to the {@code TupleList}.
     *
     * @param iterable iterable of even length
     * @return the {@code TupleList} instance
     * @throws IllegalArgumentException if {@code iterable} is of odd length or
     * {@code iterable} is not a sequence of types {@code T, U}
     */
    TupleList<T, U> slurp(final Iterable<?> iterable) throws IllegalArgumentException;


    /**
     * Convert a map into a {@code TupleList}.
     *
     * @param map map key-value pairs to tuples
     * @return the {@code TupleList} instance
     */
    TupleList<T, U> fromMap(final Map<T, U> map);

    /**
     * Zip elements from two lists into tuples and put them to the {@code TupleList}.
     *
     * @param first list whose elements are used as first elements in the tuples
     * @param last list whose elements are used as second elements in the tuples
     * @return the {@code TupleList} instance
     * @throws IllegalArgumentException if lists are of different length
     */
    TupleList<T, U> zip(final Iterable<? extends T> first, final Iterable<? extends U> last) throws IllegalArgumentException;

    /**
     * Add a new tuple to the end of the list.
     *
     * @param first first tuple element
     * @param last second tuple element
     */
    default void put(final T first, final U last) {
        put(size(), first, last);
    }

    /**
     * Add a new tuple at some position of the tuple list.
     *
     * @param index position where to put the tuple
     * @param first first tuple element
     * @param last second tuple element
     */
    default void put(final int index, final T first, final U last) {
        add(index, Tuple.of(first, last));
    }

    /**
     * Return a new empty {@code TupleList} of equal type.
     * @return new {@code TupleList} instance
     */
    TupleList<T, U> alike();

    /**
     * Set tuple at the given position to {@code Tuple.of(first, last)}.
     * @param index position where to change the list's value
     * @param first first element of inserted {@code Tuple} instance
     * @param last last element of inserted {@code Tuple} instance
     */
    default void set(int index, T first, U last) {
        set(index, Tuple.of(first, last));
    }

    /**
     * Replace all first elements of the {@code TupleList} instance.
     * @param list list of new elements inserted into first slot of the Tuples
     * @throws IllegalArgumentException if {@code list} and {@code TupleList} instance have different lengths
     */
    void replaceFirstElements(final List<? extends T> list) throws IllegalArgumentException;

    /**
     * Replace all last elements of the {@code TupleList} instance.
     * @param list list of new elements inserted into last slot of the Tuples
     * @throws IllegalArgumentException if {@code list} and {@code TupleList} instance have different lengths
     */
    void replaceLastElements(final List<? extends U> list) throws IllegalArgumentException;

    /**
     * Create a new TupleList instance.
     *
     * @param firstClass class of first element of all included tuples
     * @param secondClass class of second element of all included tuples
     * @param <S> type corresponding to {@code firstClass}
     * @param <W> type corresponding to {@code secondClass}
     * @return new {@code TupleList}
     */
    @SuppressWarnings("unchecked")
    static <S, W> TupleList<S, W> of(Class<? extends S> firstClass, Class<? extends W> secondClass) {
        return new TupleListImpl<>(firstClass, secondClass);
    }

    /**
     * Create a new TupleList instance.
     *
     * @param elementClass class of all tuple elements to include
     * @param <S> type corresponding to {@code elementClass}
     * @return new {@code TupleList}
     */
    @SuppressWarnings("unchecked")
    static <S> TupleList<S, S> of(Class<? extends S> elementClass) {
        return TupleList.of(elementClass, elementClass);
    }

    /**
     * Return an empty and unmodifiable TupleList instance.
     * <p>
     * The empty list is a singleton.
     *
     * @param <S> class of first element in the tuples
     * @param <W> class of second element in the tuples
     * @return unmodifiable TupleList
     */
    @SuppressWarnings("unchecked")
    static <S, W> TupleList<S, W> emptyList() {
        return (TupleList<S, W>) UnmodifiableTupleList.EMPTY_LIST;
    }

    /**
     * Return a sublist view of the tuple list.
     *
     * @param fromIndex first index of list to be included
     * @param toIndex final index, not included by itself
     * @return a sublist view
     */
    @Override
    TupleList<T, U> subList(int fromIndex, int toIndex);

    /**
     * Return a new, unmodifiable map with each tuple turned into a key-value pair.
     * <p>
     * In case of key collisions, the tuple with the higher index
     * in the TupleList is given preference.
     * @return
     */
    default Map<T, U> asMap() {
        Map<T, U> m = new HashMap<>();
        for (Tuple<T, U> tuple : this) {
            m.put(tuple.first(), tuple.last());
        }
        return Collections.unmodifiableMap(m);
    }

    /**
     * Return a new, unmodifiable list with all tuples flattened out.
     * @return unmodifiable list.
     */
    default List<? super Object> flatten() {
        List<? super Object> li = new ArrayList<>(size() * 2);
        for (Tuple<T, U> tuple : this) {
            li.add(tuple.first());
            li.add(tuple.last());
        }
        return Collections.unmodifiableList(li);
    }

    /**
     * Return a new unmodifiable list of Tuples instead of a TupleList.
     * @return unmodifiable List
     */
    default List<List<? super Object>> asList() {
        List<List<? super Object>> li = new ArrayList<>(size());
        for (Tuple<T, U> tuple : this) {
            final List<? super Object> elementList = new ArrayList<>(2);
            elementList.add(tuple.first());
            elementList.add(tuple.last());
            li.add(elementList);
        }
        return Collections.unmodifiableList(li);
    }

    /**
     * Return an unmodifiable list of all first elements in the tuples.
     * @return list of first elements.
     */
    default List<T> firstElements() {
        final List<T> li = new ArrayList<>(size());
        for (Tuple<T, U> tuple : this) {
            li.add(tuple.first());
        }
        return Collections.unmodifiableList(li);
    }

    /**
     * Return an unmodifiable list of all second elements in the tuples.
     * @return list of second elements.
     */
    default List<U> lastElements() {
        final List<U> li = new ArrayList<>(size());
        for (Tuple<T, U> tuple : this) {
            li.add(tuple.last());
        }
        return Collections.unmodifiableList(li);
    }

    /**
     * Return an unmodifiable version of a tuple list.
     *
     * @param tuples the original tuple list
     * @param <S> class of first element in included tuples
     * @param <W> class of second element in included tuples
     * @return unmodifiable version of {@code tuples}
     */
    static <S, W> TupleList<S, W> unmodifiableTupleList(final TupleList<S, W> tuples) {
        return new UnmodifiableTupleList<>(tuples);
    }

}
