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
import java.util.function.Consumer;

/**
 *
 * Created by lars on 14.04.16.
 */
class UnmodifiableTupleList<T, U> implements TupleList<T, U> {
    final TupleList<T, U> parent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TupleList)) return false;
        TupleList<?, ?> that = (TupleList<?, ?>) o;

        if (parent.size() != that.size()) return false;
        else for (int i = 0; i < parent.size(); i++) {
            if (!parent.get(i).equals(that.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return parent != null ? parent.hashCode() : 0;
    }

    UnmodifiableTupleList(final TupleList<T, U> tuples) {
        this.parent = tuples;
    }

    static TupleList<Object, Object> EMPTY_LIST = new UnmodifiableTupleList<>(new TupleListImpl<>(Object.class, Object.class));

    @Override
    public TupleList<T, U> slurp(Iterable<?> iterable) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public TupleList<T, U> fromMap(Map<T, U> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TupleList<T, U> zip(Iterable<? extends T> first, Iterable<? extends U> last) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Tuple subLists can only be constructed by calling subList()");
    }

    @Override
    public TupleList<T, U> alike() {
        return parent.alike();
    }

    @Override
    public void replaceFirstElements(List<? extends T> list) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceLastElements(List<? extends U> list) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return parent.size();
    }

    @Override
    public boolean isEmpty() {
        return parent.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return parent.contains(o);
    }

    @Override
    public Iterator<Tuple<T, U>> iterator() {
        // copy+paste from Collections.unmodifiableCollection
        return new Iterator<Tuple<T, U>>() {
            private final Iterator<? extends Tuple<T, U>> it = parent.iterator();

            public boolean hasNext() {return it.hasNext();}
            public Tuple<T, U> next()          {return it.next();}
            public void remove() {
                throw new UnsupportedOperationException();
            }
            @Override
            public void forEachRemaining(Consumer<? super Tuple<T, U>> action) {
                // Use backing collection version
                it.forEachRemaining(action);
            }
        };
    }

    @Override
    public Object[] toArray() {
        return parent.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return parent.toArray(a);
    }

    @Override
    public boolean add(Tuple<T, U> tuTuple) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return parent.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Tuple<T, U>> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends Tuple<T, U>> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Tuple<T, U> get(int index) {
        return parent.get(index);
    }

    @Override
    public Tuple<T, U> set(int index, Tuple<T, U> element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, Tuple<T, U> element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Tuple<T, U> remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        return parent.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return parent.lastIndexOf(o);
    }

    @Override
    public ListIterator<Tuple<T, U>> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<Tuple<T, U>> listIterator(int index) {
        return new ListIterator<Tuple<T, U>>() {
            private final ListIterator<? extends Tuple<T, U>> it
                    = parent.listIterator(index);

            public boolean hasNext()     {return it.hasNext();}
            public Tuple<T, U> next()              {return it.next();}
            public boolean hasPrevious() {return it.hasPrevious();}
            public Tuple<T, U> previous()          {return it.previous();}
            public int nextIndex()       {return it.nextIndex();}
            public int previousIndex()   {return it.previousIndex();}

            public void remove() {
                throw new UnsupportedOperationException();
            }
            public void set(Tuple<T, U> e) {
                throw new UnsupportedOperationException();
            }
            public void add(Tuple<T, U> e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super Tuple<T, U>> action) {
                it.forEachRemaining(action);
            }
        };
    }

    @Override
    public UnmodifiableTupleList<T, U> subList(int fromIndex, int toIndex) {
        return new UnmodifiableTupleList<>(parent.subList(fromIndex, toIndex));
    }

    @Override
    public String toString() {
        return String.format("Unmodifiable%s", parent.toString());
    }
}
