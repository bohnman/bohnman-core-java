package com.github.bohnman.core.collect;

import com.github.bohnman.core.collect.iterator.CoreArrayIterator;
import com.github.bohnman.core.collect.iterator.CoreArrayListIterator;
import com.github.bohnman.core.collect.iterator.CoreObjectArrayIterator;
import com.github.bohnman.core.collect.iterator.CoreObjectArrayListIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@SuppressWarnings({"ForLoopReplaceableByForEach", "UseBulkOperation", "NullableProblems"})
public interface CoreArrayWrapper extends CoreIndexedIterableWrapper<Object, Object> {

    Object get(int index);

    int size();

    int hashCode();

    String toString();

    Object getValue();

    @Override
    default Object collect(Stream<?> stream) {
        return stream.toArray();
    }

    @Override
    default boolean contains(Object o) {
        for (int i = 0; i < size(); i++) {
            if (Objects.equals(o, get(i))) {
                return true;
            }
        }

        return false;
    }

    @Override
    default Iterator<Object> iterator() {
        Object array = getValue();

        if (array instanceof Object[]) {
            return new CoreObjectArrayIterator<>(array);
        }

        return new CoreArrayIterator<>(array);
    }

    @Override
    default Object[] toArray() {
        Object[] array = new Object[size()];

        for (int i = 0; i < size(); i++) {
            array[i] = get(i);
        }

        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    default <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("toArray(T[] a");
    }

    @Override
    default boolean add(Object o) {
        throw new UnsupportedOperationException("add");
    }

    @Override
    default boolean remove(Object o) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    default boolean containsAll(Collection<?> c) {
        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }

    @Override
    default boolean addAll(Collection<?> c) {
        throw new UnsupportedOperationException("addAll(Collection)");
    }

    @Override
    default boolean addAll(int index, Collection<?> c) {
        throw new UnsupportedOperationException("addAll(int, Collection)");
    }

    @Override
    default boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("removeAll");
    }

    @Override
    default boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll");
    }

    @Override
    default void replaceAll(UnaryOperator<Object> operator) {
        throw new UnsupportedOperationException("replaceAll");
    }

    @Override
    default void sort(Comparator<? super Object> c) {
        throw new UnsupportedOperationException("sort");
    }

    @Override
    default void clear() {
        throw new UnsupportedOperationException("clear");
    }

    @Override
    default void add(int index, Object element) {
        throw new UnsupportedOperationException("add");
    }

    @Override
    default Object remove(int index) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    default int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            if (Objects.equals(o, get(i))) {
                return i;
            }
        }

        return -1;
    }

    @Override
    default int lastIndexOf(Object o) {
        for (int i = size() - 1; i >= 0; i--) {
            if (Objects.equals(o, get(i))) {
                return i;
            }
        }

        return -1;
    }

    @Override
    default ListIterator<Object> listIterator() {
        return listIterator(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    default ListIterator<Object> listIterator(int index) {
        Object array = getValue();

        if (array instanceof Object[]) {
            return new CoreObjectArrayListIterator<>(array, index);
        }

        return new CoreArrayListIterator(array, index);
    }

    @Override
    default List<Object> subList(int fromIndex, int toIndex) {
        CoreIndexedIterableWrapper wrapper = slice(fromIndex, toIndex);
        List<Object> list = new ArrayList<>(wrapper.size());

        for (int i = 0; i < wrapper.size(); i++) {
            list.add(wrapper.get(i));
        }

        return Collections.unmodifiableList(list);
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    default CoreArrayWrapper create(int size) {
        return CoreArrays.wrap(CoreArrays.newArray(getValue().getClass().getComponentType(), size));
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    default CoreArrayWrapper copy() {
        int size = size();
        CoreArrayWrapper wrapper = create(size);

        for (int i = 0; i < size; i++) {
            wrapper.set(i, get(i));
        }
        return wrapper;
    }

    default CoreArrayWrapper reverse() {
        int size = size();
        CoreArrayWrapper wrapper = create(size);

        for (int i = 0; i < size; i++) {
            wrapper.set(i, get(size - i + 1));
        }

        return wrapper;
    }

}
