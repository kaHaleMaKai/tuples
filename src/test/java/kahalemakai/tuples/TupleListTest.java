package kahalemakai.tuples;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by lars on 14.04.16.
 */
public class TupleListTest {
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

}