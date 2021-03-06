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
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 *
 * Created by lars on 14.04.16.
 */
class TupleListImpl<T, U> extends ArrayList<Tuple<T, U>> implements TupleList<T, U> {
    final Class<? extends T> firstClass;

    final Class<? extends U> lastClass;
    int modCount = 0;

    TupleListImpl(Class<? extends T> firstClass, Class<? extends U> lastClass) {
        this.firstClass = firstClass;
        this.lastClass = lastClass;
    }

    @Override
    public TupleList<T, U> alike() {
        @SuppressWarnings("unchecked")
        final TupleList<T, U> tuples = (TupleList<T, U>) TupleList.of(firstClass, lastClass);
        return tuples;
    }

    @Override
    public void replaceFirstElements(List<? extends T> list) throws IllegalArgumentException {
        final int len = list.size();
        if (len != size()) {
            throw new IllegalArgumentException("argument list and sublist are of different lengths");
        }
        for (int i = 0; i < size(); ++i) {
            final U last = this.get(i).last();
            final T newEl = list.get(i);
            this.set(i, newEl, last);
        }
    }

    @Override
    public void replaceLastElements(List<? extends U> list) throws IllegalArgumentException {
        final int len = list.size();
        if (len != size()) {
            throw new IllegalArgumentException("argument list and sublist are of different lengths");
        }
        for (int i = 0; i < size(); ++i) {
            final T first = this.get(i).first();
            final U newEl = list.get(i);
            this.set(i, first, newEl);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public TupleListImpl<T, U> slurp(final Iterable<?> iterable) throws IllegalArgumentException {
        for (final Iterator<?> it = iterable.iterator(); it.hasNext();) {
            T firstEl;
            U secondEl;
            try {
                firstEl = firstClass.cast(it.next());
                secondEl = lastClass.cast(it.next());
            } catch (NoSuchElementException e) {
                throw new IllegalArgumentException("TupleList.fromList expects an iterable of even legnth");
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("TupleList.fromList: list elements are of wrong type");
            }
            this.put(firstEl, secondEl);
        }
        return this;
    }

    @Override
    public TupleList<T, U> fromMap(Map<T, U> map) {
        map.forEach(this::put);
        return this;
    }

    @Override
    public TupleList<T, U> zip(Iterable<? extends T> first, Iterable<? extends U> last) throws IllegalArgumentException {
        final Iterator<? extends T> it1 = first.iterator();
        final Iterator<? extends U> it2 = last.iterator();
        final int len = size();
        while (it1.hasNext() && it2.hasNext()) {
            final T t = it1.next();
            final U u = it2.next();
            this.put(t, u);
        }
        if (it1.hasNext() || it2.hasNext()) {
            // remove all inserted elements
            // we only accept complete or no insertion at all
            removeRange(len, size());
            throw new IllegalArgumentException("cannot zip iterables of different length together");
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    static <S, W> TupleListImpl<S, W> of(Class<? extends S> firstClass, Class<? extends W> secondClass) {
        return new TupleListImpl<>(firstClass, secondClass);
    }


    @SuppressWarnings("unchecked")
    static <S> TupleListImpl<S, S> of(Class<? extends S> elementClass) {
        return new TupleListImpl<>(elementClass, elementClass);
    }

    @Override
    public boolean add(Tuple<T, U> tuple) {
        add(size(), tuple);
        return true;
    }

    @Override
    public void add(int index, Tuple<T, U> element) {
        modCount++;
        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends Tuple<T, U>> c) {
        if (c.size() > 0) modCount++;
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Tuple<T, U>> c) {
        if (c.size() > 0) modCount++;
        return super.addAll(index, c);
    }

    @Override
    public void clear() {
        if (size() > 0) modCount++;
        super.clear();
    }

    @Override
    public Tuple<T, U> remove(int index) {
        Tuple<T, U> tuple = super.remove(index);
        modCount++;
        return tuple;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);
        if (result) modCount++;
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        final int initialSize = size();
        boolean result = super.removeAll(c);
        if (initialSize != size()) modCount++;
        return result;
    }

    @Override
    public boolean removeIf(Predicate<? super Tuple<T, U>> filter) {
        final int initialSize = size();
        boolean result = super.removeIf(filter);
        if (initialSize != size()) modCount++;
        return result;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
        if (toIndex > fromIndex) modCount++;
    }

    @Override
    public void replaceAll(UnaryOperator<Tuple<T, U>> operator) {
        super.replaceAll(operator);
        modCount++;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        final int initialSize = size();
        boolean result = super.retainAll(c);
        if (initialSize != size()) modCount++;
        return result;
    }

    @Override
    public Tuple<T, U> set(int index, Tuple<T, U> element) {
        if (get(index) != element) modCount++;
        return super.set(index, element);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.hashCode() != o.hashCode()) return false;
        if (!(o instanceof TupleList)) return false;
        TupleList<?, ?> that = (TupleList<?, ?>) o;

        if (this.size() != that.size()) return false;
        else for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(that.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstClass != null ? firstClass.hashCode() : 0);
        result = 31 * result + (lastClass != null ? lastClass.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("TupleList<%s, %s>%s",
                firstClass.getSimpleName(),
                lastClass.getSimpleName(),
                super.toString());
    }

    @Override
    public TupleList<T, U> subList(final int fromIndex, final int toIndex) {
        return new TupleSubList<>(this, fromIndex, toIndex);
    }

    private static class TupleSubList<T, U> extends AbstractList<Tuple<T, U>> implements RandomAccess, TupleList<T, U> {
        private final TupleList<T, U> parent;
        private final int offset;
        private int lastIndex;
        private int size;
        int modCount;

        @Override
        public TupleList<T, U> slurp(Iterable<?> iterable) throws IllegalArgumentException {
            throw new UnsupportedOperationException("Tuple subLists can only be constructed by calling subList()");
        }

        @Override
        public TupleList<T, U> fromMap(Map<T, U> map) {
            throw new UnsupportedOperationException("Tuple subLists can only be constructed by calling subList()");
        }

        TupleSubList(final TupleList<T, U> tupleList, final int firstIndex, final int lastIndex) throws IndexOutOfBoundsException {
            final int parentSize = tupleList.size();
            if (firstIndex > parentSize) {
                throw new IndexOutOfBoundsException("firstIndex > tupleList.size()");
            }
            if (lastIndex > parentSize) {
                throw new IndexOutOfBoundsException("lastIndex > tupleList.size()");
            }
            if (lastIndex < firstIndex) {
                throw new IndexOutOfBoundsException("firstIndex > lastIndex");
            }
            this.parent = tupleList;
            this.offset = firstIndex;
            this.lastIndex = lastIndex;
            this.size = lastIndex - offset;
            this.modCount = getParentModCount();
        }

        public Tuple<T, U> set(final int index, final Tuple<T, U> tuple) {
            this.rangeCheck(index);
            this.checkForComodification();
            Tuple<T, U> oldVal = get(index);
            if (oldVal != tuple) {
                parent.set(offset + index, tuple);
                incrementModCount();
            }
            return oldVal;
        }

        public Tuple<T, U> get(int index) {
            this.rangeCheck(index);
            this.checkForComodification();
            return parent.get(offset + index);
        }

        public int size() {
            this.checkForComodification();
            return this.size;
        }

        public void add(int index, Tuple<T, U> tuple) {
            this.rangeCheckForAdd(index);
            this.checkForComodification();
            this.parent.add(this.offset + index, tuple);
            resize(1);
        }

        public Tuple<T, U> remove(int index) {
            this.rangeCheck(index);
            this.checkForComodification();
            Tuple<T, U> tuple = parent.remove(this.offset + index);
            resize(-1);
            return tuple;
        }

        public boolean addAll(Collection<? extends Tuple<T, U>> tuples) {
            return addAll(size, tuples);
        }

        public boolean addAll(int index, Collection<? extends Tuple<T, U>> tuples) {
            rangeCheckForAdd(index);
            int numTuples = tuples.size();
            if(numTuples == 0) {
                return false;
            } else {
                checkForComodification();
                boolean success = parent.addAll(offset + index, tuples);
                resize(numTuples);
                return success;
            }
        }

        public Iterator<Tuple<T, U>> iterator() {
            return new Iterator<Tuple<T, U>>() {
                int curIdx = 0;
                boolean removalAllowed = false;

                @Override
                public boolean hasNext() {
                    return curIdx < size;
                }

                @Override
                public void remove() {
                    if (!removalAllowed) {
                        throw new IllegalStateException("trying to remove Tuple, before calling next()");
                    }
                    TupleSubList.this.remove(curIdx - 1);
                    removalAllowed = false;
                }

                @Override
                public Tuple<T, U> next() {
                    removalAllowed = true;
                    return get(curIdx++);
                }
            };
        }

        @Override
        public TupleList<T, U> alike() {
            return parent.alike();
        }

        @Override
        public void replaceFirstElements(List<? extends T> list) throws IllegalArgumentException {
            final int len = list.size();
            if (len != size()) {
                throw new IllegalArgumentException("argument list and sublist are of different lengths");
            }
            for (int i = 0; i < size(); ++i) {
                final U last = this.get(i).last();
                final T newEl = list.get(i);
                this.set(i, newEl, last);
            }
        }

        @Override
        public void replaceLastElements(List<? extends U> list) throws IllegalArgumentException {
            final int len = list.size();
            if (len != size()) {
                throw new IllegalArgumentException("argument list and sublist are of different lengths");
            }
            for (int i = 0; i < size(); ++i) {
                final T first = this.get(i).first();
                final U newEl = list.get(i);
                this.set(i, first, newEl);
            }
        }

        public TupleSubList<T, U> subList(int firstIndex, int lastIndex) {
            if (firstIndex >= size) {
                throw new IndexOutOfBoundsException("start of sublist: " + firstIndex + " is larger than parent list's size: " + size);
            }
            if (lastIndex > size) {
                throw new IndexOutOfBoundsException("end of sublist: " + lastIndex + " is larger than parent list's size: " + size);
            }

            return new TupleSubList<>(this, firstIndex, lastIndex);
        }

        private void rangeCheck(final int index) {
            if(index < 0 || index >= this.size) {
                throw new IndexOutOfBoundsException(this.outOfBoundsMsg(index));
            }
        }

        private void rangeCheckForAdd(int index) {
            if(index < 0 || index > this.size) {
                throw new IndexOutOfBoundsException(this.outOfBoundsMsg(index));
            }
        }

        private String outOfBoundsMsg(int index) {
            return "Index: " + index + ", Size: " + this.size;
        }

        @Override
        public TupleList<T, U> zip(Iterable<? extends T> first, Iterable<? extends U> last) throws IllegalArgumentException {
            throw new UnsupportedOperationException("Tuple subLists can only be constructed by calling subList()");
        }

        void resize(final int amount) {
            size += amount;
            lastIndex += amount;
            incrementModCount();
        }

        private int getParentModCount() throws UnsupportedOperationException {
            if (parent instanceof TupleListImpl) {
                return ((TupleListImpl<T, U>) parent).modCount;
            }
            else if (parent instanceof TupleSubList) {
                TupleSubList<T, U> parentTuples = (TupleSubList<T, U>) this.parent;
                if (parentTuples.getParentModCount() != parentTuples.modCount) throw new ConcurrentModificationException();
                else return ((TupleSubList) parent).modCount;
            }
            else {
                throw new UnsupportedOperationException();
            }
        }

        private void checkForComodification() {
            if (modCount != getParentModCount()) {
                throw new ConcurrentModificationException();
            }
        }

        private void incrementModCount() {
            modCount = getParentModCount();
        }
    }
}
