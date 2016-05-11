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
     */
    public static <S, W> Tuple<S, W> of(final S first, final W last) {
        return new Tuple<>(first, last);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", first, last);
    }

}
