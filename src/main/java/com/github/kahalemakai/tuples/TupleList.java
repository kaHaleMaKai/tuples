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

import java.util.List;

/**
 * A type-safe, compile-time checked list of tuples.
 */
public interface TupleList<T, U> extends List<Tuple<T, U>> {

    /**
     * Create a new tuple list from an existing list of even-length.
     *
     * @param list list of even length
     * @return new tuple list
     * @throws IllegalStateException if list is of odd length or
     * list is not a sequence of types {@code T, U}
     */
    TupleList<T, U> fromList(final List<Object> list) throws IllegalStateException;

    /**
     * Add a new tuple to the end of the list.
     *
     * @param first first tuple element
     * @param last second tuple element
     */
    default void add(final T first, final U last) {
        add(size(), first, last);
    }

    /**
     * Add a new tuple at some position of the tuple list.
     *
     * @param index position where to add the tuple
     * @param first first tuple element
     * @param last second tuple element
     */
    default void add(final int index, final T first, final U last) {
        add(index, Tuple.of(first, last));
    }

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
    static <S, W> TupleList<S, W> of(Class<S> firstClass, Class<W> secondClass) {
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
    static <S> TupleList<S, S> of(Class<S> elementClass) {
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
