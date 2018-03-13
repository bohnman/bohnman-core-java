package com.github.bohnman.core.collect;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;

public interface CoreIndexedIterableWrapper<E, V> extends List<E> {

    V getValue();

    Object collect(Stream<?> stream);

    CoreIndexedIterableWrapper<E, V> create(int size);

    default CoreIndexedIterableWrapper<E, V> slice(int start) {
        return slice(start, size());
    }

    default CoreIndexedIterableWrapper<E, V> slice(int start, int end) {
        if (start < 0) {
            throw new IndexOutOfBoundsException(String.format("start [%s] < 0", start));
        }

        if (end < 0) {
            throw new IndexOutOfBoundsException(String.format("end [%s] < 0", end));
        }

        int size = size();

        if (size == 0) {
            return create(0);
        }

        if (start >= size) {
            throw new IndexOutOfBoundsException(String.format("start [%s] >= size [%s]", start, size));
        }

        if (end > size) {
            throw new IndexOutOfBoundsException(String.format("end [%s] > size [%s]", start, size));
        }

        if (start > end) {
            throw new IndexOutOfBoundsException(String.format("start [%s] > end [%s]", start, end));
        }

        if (start == end) {
            return create(0);
        }

        int len = (end - start);

        if (len <= 0) {
            return create(0);
        }

        CoreIndexedIterableWrapper<E, V> wrapper = create(len);

        for (int i = start; i < end; i++) {
            wrapper.set(i - start, get(i));
        }

        return wrapper;
    }

}
