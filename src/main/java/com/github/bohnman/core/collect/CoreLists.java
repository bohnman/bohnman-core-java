package com.github.bohnman.core.collect;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class CoreLists {

    public CoreLists() {
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(Iterable<? extends T> iterable) {
        if (iterable instanceof List) {
            return (List<T>) iterable;
        }

        return create(iterable);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> create(Iterable<? extends T> iterable) {
        if (iterable instanceof Collection) {
            return new ArrayList<>((Collection<T>) iterable);
        }

        return CoreStreams.of(iterable).collect(Collectors.toList());
    }

    public static <T> CoreListWrapper<T> wrap(Iterable<T> iterable) {
        return wrap(toList(iterable));
    }

    public static <T> CoreListWrapper<T> wrap(List<T> list) {
        return new CoreListWrapper<>(list);
    }
}
