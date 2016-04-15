package kahalemakai.tuples;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 *
 * Created by lars on 14.04.16.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@Accessors(fluent = true)
@Getter
public class Tuple<T, U> {
    final private T first;
    final private U last;

    @Override
    public String toString() {
        return String.format("[%s, %s]", first, last);
    }
}
