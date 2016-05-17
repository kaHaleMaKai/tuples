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

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by lars on 19.04.16.
 */
public class TupleSubListTest {

    private TupleList<String, Integer> t1;
    private TupleList<String, Integer> sl1;
    private Tuple<String, Integer> tuple1;
    private Tuple<String, Integer> tuple2;
    private Tuple<String, Integer> tuple3;
    private Tuple<String, Integer> tuple4;

    @Before
    public void setUp() throws Exception {
        t1 = TupleList.of(String.class, Integer.class);
        t1.put("a", 1);
        t1.put("b", 2);
        t1.put("c", 3);
        tuple1 = Tuple.of("a", 1);
        tuple2 = Tuple.of("b", 2);
        tuple3 = Tuple.of("c", 3);
        tuple4 = Tuple.of("d", 4);
        sl1 = t1.subList(0, 2);
    }

    @Test
    public void testInitialization() throws Exception {
        assertEquals(2, sl1.size());
    }

    @Test
    public void testRemove() throws Exception {
        Tuple<String, Integer> originalTuple = t1.get(0);
        Tuple<String, Integer> removedElement = sl1.remove(0);
        assertEquals(1, sl1.size());
        assertEquals(2, t1.size());
        assertEquals(t1.get(0), sl1.get(0));
        assertEquals(originalTuple, removedElement);
    }

    @Test
    public void testAdd() throws Exception {
        sl1.add(1, Tuple.of("d", 4));
        assertEquals(3, sl1.size());
        assertEquals(4, t1.size());
        assertEquals(tuple4, t1.get(1));
        assertEquals(tuple4, sl1.get(1));
        assertEquals(tuple2, t1.get(2));
        assertEquals(tuple2, sl1.get(2));
    }

    @Test
    public void testAddLast() throws Exception {
        sl1.put("d", 4);
        assertEquals(3, sl1.size());
        assertEquals(4, t1.size());
        assertEquals(tuple4, t1.get(2));
        assertEquals(tuple4, sl1.get(2));
    }

    @Test
    public void testSet() throws Exception {
        sl1.set(1, Tuple.of("d", 4));
        assertEquals(tuple4, sl1.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetThrows() throws Exception {
        sl1.get(2);
        sl1.get(-1);
        sl1.get(3);
    }

    @Test
    public void testGet() throws Exception {
        assertEquals(tuple1, sl1.get(0));
        assertEquals(tuple2, sl1.get(1));
    }

    @Test
    public void testAddAllLast() throws Exception {
        sl1.addAll(t1);
        assertEquals(5, sl1.size());
        assertEquals(6, t1.size());
        assertEquals(tuple1, sl1.get(2));
        assertEquals(tuple2, sl1.get(3));
        assertEquals(tuple3, sl1.get(4));
        assertEquals(tuple1, t1.get(2));
        assertEquals(tuple2, t1.get(3));
        assertEquals(tuple3, t1.get(4));
        assertEquals(tuple3, t1.get(5));
    }

    @Test
    public void testAddAll() throws Exception {
        sl1.addAll(1, t1);
        assertEquals(5, sl1.size());
        assertEquals(6, t1.size());
        assertEquals(tuple1, sl1.get(0));
        assertEquals(tuple1, sl1.get(1));
        assertEquals(tuple2, sl1.get(2));
        assertEquals(tuple3, sl1.get(3));
        assertEquals(tuple2, sl1.get(4));
    }

    @Test
    public void testForEach() throws Exception {
        TupleList<String, Integer> tupleList = TupleList.of(String.class, Integer.class);
        for (Tuple<String, Integer> tuple : sl1) {
            tupleList.add(tuple);
        }
        assertEquals(tuple1, tupleList.get(0));
        assertEquals(tuple2, tupleList.get(1));
    }

    @Test
    public void testRemoveFromIterator() throws Exception {
        for (Iterator<Tuple<String, Integer>> it = sl1.iterator(); it.hasNext();) {
            Tuple<String, Integer> tuple = it.next();
            if (tuple.first() == "b") {
                it.remove();
            }
        }
        assertEquals(1, sl1.size());
        assertEquals(2, t1.size());
        assertEquals(tuple1, sl1.get(0));
        assertEquals(tuple1, t1.get(0));
        assertEquals(tuple3, t1.get(1));

    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveFromIteratorThrowsOnEarlyInvocation() throws Exception {
        Iterator<Tuple<String, Integer>> it = sl1.iterator();
        it.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveFromIteratorThrowsOnMultipleInvocations() throws Exception {
        Iterator<Tuple<String, Integer>> it = sl1.iterator();
        it.next();
        it.remove();
        it.remove();
    }

    @Test
    public void testSubList() throws Exception {
        TupleList<String, Integer> sl2 = sl1.subList(1, 2);
        assertEquals(1, sl2.size());
        assertEquals(tuple2, sl2.get(0));
        sl2.add(0, Tuple.of("d", 4));

        assertEquals(2, sl2.size());
        assertEquals(3, sl1.size());
        assertEquals(4, t1.size());

        assertEquals(tuple4, sl2.get(0));
        assertEquals(tuple2, sl2.get(1));

        assertEquals(tuple1, sl1.get(0));
        assertEquals(tuple4, sl1.get(1));

        assertEquals(tuple1, t1.get(0));
        assertEquals(tuple4, sl1.get(1));
        assertEquals(tuple2, sl1.get(2));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testGetThrowsConcurrentModificationException() throws Exception {
        t1.add(tuple1);
        sl1.size();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testForEachThrowsConcurrentModificationException() throws Exception {
        for (Tuple<String, Integer> tuple : sl1) {
            t1.remove(0);
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testAddOnSubListThrowsConcurrentModificationExceotion() throws Exception {
        TupleList<String, Integer> sl2 = sl1.subList(1, 2);
        sl1.add(tuple1);
        sl2.size();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testAddOnListThrowsConcurrentModificationExceotionOnSublist() throws Exception {
        TupleList<String, Integer> sl2 = sl1.subList(1, 2);
        t1.add(tuple1);
        sl2.size();
    }

    @Test
    public void testEquals() throws Exception {
        TupleList<String, Integer> sl2 = t1.subList(0, 2);
        assertEquals(sl1, sl2);
    }

    @Test
    public void testElementSlicing() throws Exception {
        final List<String> firsts = sl1.firstElements();
        assertEquals("a", firsts.get(0));
        assertEquals("b", firsts.get(1));
        final List<Integer> lasts = sl1.lastElements();
        assertEquals(Integer.valueOf(1), lasts.get(0));
        assertEquals(Integer.valueOf(2), lasts.get(1));
    }

    @Test
    public void testAlike() throws Exception {
        final TupleList<String, Integer> alike = sl1.alike();
        final TupleList<String, Integer> empty = TupleList.emptyList();
        assertEquals(empty, alike);
    }

    @Test
    public void testSetOnSublist() throws Exception {
        sl1.set(1, "A", 23);
        final Tuple<String, Integer> a = Tuple.of("A", 23);
        assertEquals(a, sl1.get(1));
        assertEquals(a, t1.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetOnSublistThrows() throws Exception {
        sl1.set(2, "A", 23);
    }

    @Test
    public void testReplaceFirstElements() throws Exception {
        final LinkedList<String> list = new LinkedList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        t1.replaceFirstElements(list);
        assertEquals(3, t1.size());
        assertEquals("x", t1.get(0).first());
        assertEquals("y", t1.get(1).first());
        assertEquals("z", t1.get(2).first());
        assertEquals(Integer.valueOf(1), t1.get(0).last());
        assertEquals(Integer.valueOf(2), t1.get(1).last());
        assertEquals(Integer.valueOf(3), t1.get(2).last());
    }

    @Test
    public void testReplaceFirstElementsOnSublist() throws Exception {
        final LinkedList<String> list = new LinkedList<>();
        list.add("x");
        list.add("y");
        sl1.replaceFirstElements(list);
        assertEquals(2, sl1.size());
        assertEquals("x", sl1.get(0).first());
        assertEquals("y", sl1.get(1).first());
        assertEquals(Integer.valueOf(1), sl1.get(0).last());
        assertEquals(Integer.valueOf(2), sl1.get(1).last());
        assertEquals(3, t1.size());
        assertEquals("x", t1.get(0).first());
        assertEquals("y", t1.get(1).first());
        assertEquals("c", t1.get(2).first());
        assertEquals(Integer.valueOf(1), t1.get(0).last());
        assertEquals(Integer.valueOf(2), t1.get(1).last());
        assertEquals(Integer.valueOf(3), t1.get(2).last());
    }

    @Test
    public void testReplaceLastElements() throws Exception {
        final LinkedList<Integer> list = new LinkedList<>();
        list.add(11);
        list.add(12);
        list.add(13);
        t1.replaceLastElements(list);
        assertEquals(3, t1.size());
        assertEquals("a", t1.get(0).first());
        assertEquals("b", t1.get(1).first());
        assertEquals("c", t1.get(2).first());
        assertEquals(Integer.valueOf(11), t1.get(0).last());
        assertEquals(Integer.valueOf(12), t1.get(1).last());
        assertEquals(Integer.valueOf(13), t1.get(2).last());
    }

    @Test
    public void testReplaceLastElementsOnSublist() throws Exception {
        final LinkedList<Integer> list = new LinkedList<>();
        list.add(11);
        list.add(12);
        sl1.replaceLastElements(list);
        assertEquals(2, sl1.size());
        assertEquals("a", sl1.get(0).first());
        assertEquals("b", sl1.get(1).first());
        assertEquals(Integer.valueOf(11), sl1.get(0).last());
        assertEquals(Integer.valueOf(12), sl1.get(1).last());
        assertEquals(3, t1.size());
        assertEquals("a", t1.get(0).first());
        assertEquals("b", t1.get(1).first());
        assertEquals("c", t1.get(2).first());
        assertEquals(Integer.valueOf(11), t1.get(0).last());
        assertEquals(Integer.valueOf(12), t1.get(1).last());
        assertEquals(Integer.valueOf(3), t1.get(2).last());
    }

}