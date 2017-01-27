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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testGet() throws Exception {
        assertEquals(t1.first(), t1.get(0));
        assertEquals(t1.last(), t1.get(1));
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

    @Test
    public void testContains() throws Exception {
        assertTrue(t1.contains(1));
        assertTrue(t1.contains("a"));
        assertTrue(t5.contains(1L));
        assertFalse(t1.contains("error"));
    }
}