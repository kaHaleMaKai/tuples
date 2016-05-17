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

import java.util.*;

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
        assertEquals(Tuple.of("a", 1), t1.get(0));
        assertEquals(Tuple.of("b", 2), t1.get(1));
    }

    @Test(expected = IllegalStateException.class)
    public void testZipThrowsStateException() throws Exception {
        t0.zip(new LinkedList<>(), new LinkedList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZipThrowsArgumentException() throws Exception {
        final LinkedList<Integer> i = new LinkedList<>(Arrays.asList(1, 2));
        final LinkedList<String> s = new LinkedList<>(Arrays.asList("a", "b", "c"));
        TupleList<Integer, String> tuples = TupleList
                .of(Integer.class, String.class)
                .zip(i, s);
        assertEquals(3, tuples.size());
        assertEquals(Tuple.of(1, "a"), tuples.get(0));
        assertEquals(Tuple.of(2, "b"), tuples.get(1));
        assertEquals(Tuple.of(3, "c"), tuples.get(2));
    }

    @Test
    public void testZip() throws Exception {
        final LinkedList<Integer> i = new LinkedList<>(Arrays.asList(1, 2, 3));
        final LinkedList<String> s = new LinkedList<>(Arrays.asList("a", "b", "c"));
        TupleList<Integer, String> tuples = TupleList
                .of(Integer.class, String.class)
                .zip(i, s);
        assertEquals(3, tuples.size());
        assertEquals(Tuple.of(1, "a"), tuples.get(0));
        assertEquals(Tuple.of(2, "b"), tuples.get(1));
        assertEquals(Tuple.of(3, "c"), tuples.get(2));
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
        t0 = TupleList
                .of(String.class, Integer.class)
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

    @Test
    public void testElementSlicing() throws Exception {
        final List<String> firsts = t0.firstElements();
        assertEquals("a", firsts.get(0));
        assertEquals("b", firsts.get(1));
        final List<Integer> lasts = t0.lastElements();
        assertEquals(Integer.valueOf(1), lasts.get(0));
        assertEquals(Integer.valueOf(2), lasts.get(1));
    }

    @Test
    public void testAlike() throws Exception {
        final TupleList<String, Integer> alike = t0.alike();
        final TupleList<String, Integer> empty = TupleList.emptyList();
        assertEquals(empty, alike);
    }

    @Test
    public void testSet() throws Exception {
        t0.set(1, "A", 23);
        assertEquals(Tuple.of("A", 23), t0.get(1));
    }

    @Test
    public void testReplaceAll() throws Exception {
        t0.replaceAll(t -> Tuple.of(t.first().toUpperCase(), t.last() + 10));
        assertEquals("A", t0.get(0).first());
        assertEquals("B", t0.get(1).first());
        assertEquals(Integer.valueOf(11), t0.get(0).last());
        assertEquals(Integer.valueOf(12), t0.get(1).last());
    }

    @Test
    public void testAsMap() throws Exception {
        final Map<String, Integer> m = t0.asMap();
        assertEquals(Integer.valueOf(1), m.get("a"));
        assertEquals(Integer.valueOf(2), m.get("b"));
    }

    @Test
    public void testRemove() throws Exception {
        t0.remove(0);
        assertEquals(1, t0.size());
        assertEquals(Tuple.of("b", 2), t0.get(0));
        t0.removeIf(t -> false);
        assertEquals(1, t0.size());
        assertEquals(Tuple.of("b", 2), t0.get(0));
        t0.removeIf(t -> true);
        assertEquals(0, t0.size());
    }

    @Test
    public void testAsList() throws Exception {
        final List<List<? super Object>> list = t0.asList();
        assertEquals("a", list.get(0).get(0));
        assertEquals(1, list.get(0).get(1));
        assertEquals("b", list.get(1).get(0));
        assertEquals(2, list.get(1).get(1));
    }

    @Test
    public void testFlatten() throws Exception {
        final List<? super Object> list = t0.flatten();
        assertEquals("a", list.get(0));
        assertEquals(1, list.get(1));
        assertEquals("b", list.get(2));
        assertEquals(2, list.get(3));
    }
}
