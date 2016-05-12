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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * A typed tuple class.
 */
@EqualsAndHashCode
@Accessors(fluent = true)
public class Tuple<T, U> {
    /**
     * Get the first element of the tuple.
     *
     * @return the first element
     */
    @Getter
    final private T first;

    /**
     * Get the second element of the tuple.
     *
     * @return the second element
     */
    @Getter
    final private U last;

    private Tuple(T first, U last) {
        this.first = first;
        this.last = last;
    }

    /**
     * Return a new tuple instance.
     *
     * @param first first tuple element
     * @param last second tuple element
     * @param <S> class of first element
     * @param <W> class of second element
     * @return a new tuple
     */
    public static <S, W> Tuple<S, W> of(final S first, final W last) {
        return new Tuple<>(first, last);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", first, last);
    }

    /**
     * Check if a tuple contains an object.
     *
     * @param o object to be looked up
     * @return whether object is present or not
     */
    public boolean contains(Object o) {
        return first.equals(o) || last.equals(o);
    }
}
