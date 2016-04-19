package kahalemakai.tuples;

import java.util.List;

/**
 * Created by lars on 14.04.16.
 */
public interface TupleList<T, U> extends List<Tuple<T, U>> {

    TupleList<T, U> fromList(final List<Object> list);
    void add(final T first, final U last);

    @SuppressWarnings("unchecked")
    static <S, W> TupleList<S, W> of(Class<S> firstClass, Class<W> secondClass) {
        return new TupleListImpl<>(firstClass, secondClass);
    }

    @SuppressWarnings("unchecked")
    static <S> TupleList<S, S> of(Class<S> elementClass) {
        return TupleList.of(elementClass, elementClass);
    }

    @SuppressWarnings("unchecked")
    static <S, W> TupleList<S, W> emptyList() {
        return (TupleList<S, W>) UnmodifiableTupleList.EMPTY_LIST;
    }

    static <S, W> TupleList<S, W> unmodifiableTupleList(final TupleList<S, W> tuples) {
        return new UnmodifiableTupleList<>(tuples);
    }

}
