/*
Triplets – a type-safe tuple and tuple list implementation
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
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A typed triplet class.
 */
@EqualsAndHashCode
@Accessors(fluent = true)
@RequiredArgsConstructor(staticName = "of")
public final class Triplet<T, U, V> implements NTuple {
    private static final String ERROR_MSG = "expected: index in (0, 1, 2). got: ";

    /**
     * Get the first element of the {@code Triplet}.
     *
     * @return the first element
     */
    @Getter
    final private T first;

    /**
     * Get the second element of the {@code Triplet}.
     *
     * @return the second element
     */
    @Getter
    final private U second;

    /**
     * Get the third element of the {@code Triplet}.
     *
     * @return the third element
     */
    @Getter
    final private V last;

    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", first, second, last);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        return first.equals(o) ||
                second.equals(o) ||
                last.equals(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(int index) {
        if (index == 0) {
            return first;
        }
        else if (index == 1) {
            return second;
        }
        else if (index == 2) {
            return last;
        }
        throw new IllegalArgumentException(ERROR_MSG + index);
    }
}
