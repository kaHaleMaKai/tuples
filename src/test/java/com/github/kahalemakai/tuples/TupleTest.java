package com.github.kahalemakai.tuples;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by lars on 14.04.16.
 */
public class TupleTest {
    private Tuple<Integer, String> t1;
    private Tuple<Integer, String> t2;
    private Tuple<Integer, String> t3;
    private Tuple<Integer, String> t4;
    private Tuple<Long, String> t5;

    @Test
    public void testEquals() throws Exception {
        assertTrue(t1.equals(t1));
        assertTrue(t2.equals(t2));
        assertTrue(t1.equals(t2));
        assertTrue(t2.equals(t1));

        assertFalse(t1.equals(t3));
        assertFalse(t1.equals(t4));
        assertFalse(t1.equals(t5));
    }

    @Before
    public void setUp() throws Exception {
        t1 = Tuple.of(1, "a");
        t2 = Tuple.of(1, "a");
        t3 = Tuple.of(2, "a");
        t4 = Tuple.of(1, "b");
        t5 = Tuple.of(1L, "a");
    }

    @Test
    public void testGetters() throws Exception {
        assertEquals(Integer.valueOf(1), t1.first());
        assertEquals("a", t1.last());
    }
}