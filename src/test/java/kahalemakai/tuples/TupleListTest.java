package kahalemakai.tuples;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lars on 14.04.16.
 */
public class TupleListTest {

    private List<Object> li0;
    private TupleList<String, Integer> t0;

    @Test
    public void testFrom() throws Exception {
        List<Object> li = new ArrayList<>();
        TupleList<String, Integer> t0 = TupleList.of(String.class, Integer.class).fromList(li);
        assertEquals(t0.size(), 0);
        assertEquals(TupleList.emptyList(), t0);

        li.add("a");
        li.add(1);
        li.add("b");
        li.add(2);
        TupleList<String, Integer> t1 = TupleList.of(String.class, Integer.class).fromList(li);
        assertEquals(t1.size(), 2);
        assertEquals(new Tuple<>("a", 1), t1.get(0));
        assertEquals(new Tuple<>("b", 2), t1.get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromThrowsOnOddNumber() throws Exception {
        List<Object> li = new ArrayList<>();
        li.add("a");
        li.add(1);
        li.add("b");
        TupleList.of(String.class, Integer.class).fromList(li);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromThrowsOnBadTypes() throws Exception {
        List<Object> li = new ArrayList<>();
        li.add("a");
        li.add(1);
        li.add("b");
        li.add("b");
        TupleList<String, Integer> t = TupleList.of(String.class, Integer.class).fromList(li);
    }

    @Before
    public void setUp() throws Exception {
        li0 = new ArrayList<>();
        li0.add("a");
        li0.add(1);
        li0.add("b");
        li0.add(2);
        t0 = TupleList.of(String.class, Integer.class)
                .fromList(li0);

    }

    @Test
    public void testClear() throws Exception {
        final int firstModCount = ((TupleListImpl<String, Integer>) t0).modCount;
        t0.clear();
        final int secondModCount = ((TupleListImpl<String, Integer>) t0).modCount;
        assertEquals(0, t0.size());
        t0.clear();
        final int thirdModCount = ((TupleListImpl<String, Integer>) t0).modCount;
        assertNotEquals(firstModCount, secondModCount);
        assertEquals(secondModCount, thirdModCount);
    }

    @Test(expected = IllegalStateException.class)
    public void testFromListThrows() throws Exception {
        t0.fromList(li0);
    }

    @Test
    public void testHashCode() throws Exception {
        TupleList<String, Integer> t1 = TupleList.of(String.class, Integer.class)
                .fromList(li0);
        TupleList<String, Integer> t2 = TupleList.of(String.class, Integer.class)
                .fromList(li0);
        TupleList<String, Number> t3 = TupleList.of(String.class, Number.class)
                .fromList(li0);
        assertTrue(t1.hashCode() == t2.hashCode());
        assertFalse(t1.hashCode() == t3.hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        TupleList<String, Integer> t1 = TupleList.of(String.class, Integer.class)
                .fromList(li0);
        assertEquals(t0, t1);
        t1.remove(0);
        assertNotEquals(t0, t1);
        assertEquals(t0, t0);
        assertNotEquals(t0, 1);
        t1.add("p", 9);
        assertNotEquals(t0, t1);
        TupleList<String, Number> t3 = TupleList.of(String.class, Number.class).fromList(li0);
        assertNotEquals(t0, t3);
    }
}