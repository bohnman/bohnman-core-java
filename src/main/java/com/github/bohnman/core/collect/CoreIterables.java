package com.github.bohnman.core.collect;

import com.github.bohnman.core.lang.CoreAssert;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CoreIterables {

    private CoreIterables() {
    }

    @Nullable
    public static <T> T getFirst(Iterable<? extends T> iterable, @Nullable T defaultValue) {
        return CoreIterators.getNext(iterable.iterator(), defaultValue);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T getLast(Iterable<? extends T> iterable, @Nullable T defaultValue) {
        if (iterable instanceof Collection) {
            Collection<? extends T> c = (Collection) iterable;
            if (c.isEmpty()) {
                return defaultValue;
            } else if (iterable instanceof List) {
                return getLastInNonemptyList((List<T>) iterable);
            }
        }

        return CoreIterators.getLast(iterable.iterator(), defaultValue);
    }

    private static <T> T getLastInNonemptyList(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        if (iterable instanceof Collection) {
            return ((Collection<?>) iterable).isEmpty();
        }
        return !iterable.iterator().hasNext();
    }

    public static int size(Iterable<?> iterable) {
        return (iterable instanceof Collection)
                ? ((Collection<?>) iterable).size()
                : CoreIterators.size(iterable.iterator());
    }

    public static <T> T get(Iterable<T> iterable, int position) {
        CoreAssert.notNull(iterable);
        return (iterable instanceof List)
                ? ((List<T>) iterable).get(position)
                : CoreIterators.get(iterable.iterator(), position);
    }

    public static boolean isIterableLike(Object value) {
        if (value == null) {
            return false;
        }

        if (value instanceof Iterable) {
            return true;
        }

        if (value.getClass().isArray()) {
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    public static CoreIndexedIterableWrapper<Object, ?> wrap(Object value) {
        if (value == null) {
            return CoreLists.wrap(Collections.emptyList());
        }

        if (value instanceof Iterable) {
            return CoreLists.wrap((Iterable) value);
        }

        if (value.getClass().isArray()) {
            return CoreArrays.wrap(value);
        }

        return CoreLists.wrap(Collections.singletonList(value));
    }

}
