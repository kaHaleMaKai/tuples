package kahalemakai.tuples;

import java.util.*;
import java.util.function.Consumer;

/**
 *
 * Created by lars on 14.04.16.
 */
class UnmodifiableTupleList<T, U> extends TupleListImpl<T, U> {
    private final TupleList<T, U> tuples;

    UnmodifiableTupleList(final TupleList<T, U> tuples) {
        this.tuples = tuples;
    }

    static TupleList<Object, Object> EMPTY_LIST = new UnmodifiableTupleList<>(new TupleListImpl<>(Object.class, Object.class));

    @Override
    public TupleList<T, U> fromList(List<Object> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(T first, U last) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return tuples.size();
    }

    @Override
    public boolean isEmpty() {
        return tuples.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Tuple<T, U>> iterator() {
        // copy+paste from Collections.unmodifiableCollection
        return new Iterator<Tuple<T, U>>() {
            private final Iterator<? extends Tuple<T, U>> it = tuples.iterator();

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
        return tuples.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return tuples.toArray(a);
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
        return tuples.containsAll(c);
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
        return tuples.get(index);
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
        return tuples.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return tuples.lastIndexOf(o);
    }

    @Override
    public ListIterator<Tuple<T, U>> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<Tuple<T, U>> listIterator(int index) {
        return new ListIterator<Tuple<T, U>>() {
            private final ListIterator<? extends Tuple<T, U>> it
                    = tuples.listIterator(index);

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
    public List<Tuple<T, U>> subList(int fromIndex, int toIndex) {
        return Collections.unmodifiableList(tuples.subList(fromIndex, toIndex));
    }
}
