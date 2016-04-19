package kahalemakai.tuples;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by lars on 14.04.16.
 */
public class TupleListImpl<T, U> extends ArrayList<Tuple<T, U>> implements TupleList<T, U> {
    final Class<? extends T> firstClass;
    final Class<? extends U> lastClass;

    TupleListImpl(Class<? extends T> firstClass, Class<? extends U> lastClass) {
        this.firstClass = firstClass;
        this.lastClass = lastClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TupleListImpl<T, U> fromList(final List<Object> list) throws IllegalArgumentException {
        final int len = list.size();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Tuple.fromList expects a list of even legnth");
        }
        int i = 0;
        while (i < len) {
            T firstEl;
            U secondEl;
            try {
                firstEl = firstClass.cast(list.get(i++));
                secondEl = lastClass.cast(list.get(i++));
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Tuple.fromList: list elements are of wrong type");
            }
            this.add(new Tuple<>(firstEl, secondEl));
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    static <S, W> TupleListImpl<S, W> of(Class<S> firstClass, Class<W> secondClass) {
        return new TupleListImpl<>(firstClass, secondClass);
    }


    @SuppressWarnings("unchecked")
    static <S> TupleListImpl<S, S> of(Class<S> elementClass) {
        return new TupleListImpl<>(elementClass, elementClass);
    }

    public void add(final T first, final U last)  {
        add(new Tuple<>(first, last));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
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
        return super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("TupleList<%s, %s>%s",
                firstClass.getSimpleName(),
                lastClass.getSimpleName(),
                super.toString());
    }

}
