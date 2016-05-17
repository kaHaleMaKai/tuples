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

import java.util.LinkedList;
import java.util.List;

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

    @Test(expected = UnsupportedOperationException.class)
    public void testSubList() throws Exception {
        TupleList<Integer, String> subList = t1.subList(0, 1);
        subList.put(3, "c");
    }

    @Before
    public void setUp() throws Exception {
        TupleList<Integer, String> t0 = TupleList.of(Integer.class, String.class);
        t0.put(1, "a");
        t0.put(2, "b");
        t1 = TupleList.unmodifiableTupleList(t0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddThrows() throws Exception {
        t1.put(1, "2");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAllThrows() throws Exception {
        t1.addAll(t1);
    }

    @Test
    public void testElementSlicing() throws Exception {
        final List<Integer> firsts = t1.firstElements();
        final List<String> lasts = t1.lastElements();
        assertEquals(Integer.valueOf(1), firsts.get(0));
        assertEquals(Integer.valueOf(2), firsts.get(1));
        assertEquals("a", lasts.get(0));
        assertEquals("b", lasts.get(1));
    }

    @Test
    public void testAlike() throws Exception {
        final TupleList<Integer, String> alike = t1.alike();
        final TupleList<Integer, String> empty = TupleList.emptyList();
        assertEquals(empty, alike);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testReplaceFirstElements() throws Exception {
        TupleList.emptyList().replaceFirstElements(new LinkedList<>());;
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testReplaceLastElements() throws Exception {
        TupleList.emptyList().replaceLastElements(new LinkedList<>());;
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFromList() throws Exception {
        t1.slurp(new LinkedList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testZip() throws Exception {
        t1.zip(new LinkedList<>(), new LinkedList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testReplaceFirstElements1() throws Exception {
        t1.replaceFirstElements(new LinkedList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testReplaceLastElements1() throws Exception {
        t1.replaceLastElements(new LinkedList<>());
    }
}
