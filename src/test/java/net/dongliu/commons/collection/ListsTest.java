package net.dongliu.commons.collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ListsTest {

    @Test
    public void convertTo() {
        assertEquals(Lists.of(1, 2, 3), Lists.convertTo(Lists.of("1", "2", "3"), Integer::valueOf));
        assertEquals(Lists.of(1), Lists.convertTo(Lists.of("1"), Integer::valueOf));
        assertEquals(Lists.of(), Lists.convertTo(Lists.<String>of(), Integer::valueOf));
    }

    @Test
    public void filter() {
        assertEquals(Lists.of(1, 2, 3), Lists.filter(Lists.of(1, 2, 3, 4), i -> i < 4));
        assertEquals(Lists.of(), Lists.filter(Lists.of(1, 2, 3, 4), i -> i > 5));
    }

    @Test
    public void split() {
        assertEquals(Lists.of(Lists.of(1), Lists.of(2), Lists.of(3), Lists.of(4)), Lists.split(Lists.of(1, 2, 3, 4), 1));
        assertEquals(Lists.of(Lists.of(1, 2), Lists.of(3, 4)), Lists.split(Lists.of(1, 2, 3, 4), 2));
        assertEquals(Lists.of(Lists.of(1, 2, 3), Lists.of(4)), Lists.split(Lists.of(1, 2, 3, 4), 3));
        assertEquals(Lists.of(Lists.of(1, 2, 3, 4)), Lists.split(Lists.of(1, 2, 3, 4), 4));
        assertEquals(Lists.of(Lists.of(1, 2, 3, 4)), Lists.split(Lists.of(1, 2, 3, 4), 5));
        assertEquals(Lists.of(Lists.of()), Lists.split(Lists.<Integer>of(), 5));
    }

    @Test
    public void partition() {
        assertEquals(Pair.of(Lists.of(1), Lists.of(2, 3, 4)), Lists.partition(Lists.of(1, 2, 3, 4), i -> i < 2));
        assertEquals(Pair.of(Lists.of(), Lists.of(1, 2, 3, 4)), Lists.partition(Lists.of(1, 2, 3, 4), i -> i < 1));
        assertEquals(Pair.of(Lists.of(), Lists.of()), Lists.partition(Lists.<Integer>of(), i -> i < 1));
    }

    @Test
    public void first() {
        assertEquals(Optional.empty(), Lists.first(Lists.of()));
        assertEquals(Optional.of(1), Lists.first(Lists.of(1)));
        assertEquals(Optional.of(1), Lists.first(Lists.of(1, 2)));
    }

    @Test
    public void firstOrNull() {
        assertNull(Lists.firstOrNull(Lists.of()));
        assertEquals(Integer.valueOf(1), Lists.firstOrNull(Lists.of(1)));
        assertEquals(Integer.valueOf(1), Lists.firstOrNull(Lists.of(1, 2)));
    }

    @Test
    public void find() {
        assertEquals(Optional.empty(), Lists.find(Lists.<Integer>of(), i -> i > 1));
        assertEquals(Optional.empty(), Lists.find(Lists.of(1), i -> i > 1));
        assertEquals(Optional.of(2), Lists.find(Lists.of(1, 2), i -> i > 1));
    }

    @Test
    public void findOrNull() {
        assertNull(Lists.findOrNull(Lists.<Integer>of(), i -> i > 1));
        assertNull(Lists.findOrNull(Lists.of(1), i -> i > 1));
        assertEquals(Integer.valueOf(2), Lists.findOrNull(Lists.of(1, 2), i -> i > 1));
    }

    @Test
    public void nullToEmpty() {
        assertEquals(Lists.of(), Lists.nullToEmpty(null));
        assertEquals(Lists.of(), Lists.nullToEmpty(Lists.of()));
        assertEquals(Lists.of(1, 2), Lists.nullToEmpty(Lists.of(1, 2)));
    }

    @Test
    public void of() {
    }

    @Test
    public void copy() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<Integer> newList = Lists.copy(list);
        assertEquals(Lists.of(1, 2, 3), newList);
        list.set(0, 10);
        assertEquals(Lists.of(1, 2, 3), newList);
    }

    @Test
    public void concat() {
        assertEquals(Lists.of(1, 2, 3, 4), Lists.concat(Lists.of(1, 2), Lists.of(3, 4)));
        assertEquals(Lists.of(1, 2), Lists.concat(Lists.of(1, 2), Lists.of()));
        assertEquals(Lists.of(3, 4), Lists.concat(Lists.of(), Lists.of(3, 4)));
        assertEquals(Lists.of(), Lists.concat(Lists.of(), Lists.of()));
    }

    @Test
    public void toArray() {
        assertArrayEquals(new String[]{"1", "2"}, Lists.toArray(Lists.of("1", "2"), String[]::new));
        assertArrayEquals(new String[]{"1"}, Lists.toArray(Lists.of("1"), String[]::new));
        assertArrayEquals(new String[]{}, Lists.toArray(Lists.of(), String[]::new));
    }
}