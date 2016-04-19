package kahalemakai.tuples;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by lars on 17.04.16.
 */
public class UnmodifiableTupleListTest {
    private TupleList<Integer, String> t1;

    @Test
    public void testGet() throws Exception {
        assertEquals(Tuple.of(1, "a"), t1.get(0));
        assertEquals(Tuple.of(2, "b"), t1.get(1));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetThrows() throws Exception {
        t1.set(0, Tuple.of(0, "0"));
    }

    @Before
    public void setUp() throws Exception {
        TupleList<Integer, String> t0 = TupleList.of(Integer.class, String.class);
        t0.add(1, "a");
        t0.add(2, "b");
        t1 = TupleList.unmodifiableTupleList(t0);
    }
}