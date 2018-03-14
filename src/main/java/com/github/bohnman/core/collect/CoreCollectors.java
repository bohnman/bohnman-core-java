package com.github.bohnman.core.collect;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class CoreCollectors {

    private CoreCollectors() {
    }


    static final Set<Collector.Characteristics> CH_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));

    @SuppressWarnings("unchecked")
    public static <T> Collector<T, ?, Iterable<T>> toIterable(Class<?> collectionType) {
        return (Collector) toCollection(collectionType);
    }

    @SuppressWarnings("unchecked")
    public static <T> Collector<T, ?, Collection<T>> toCollection(Class<?> collectionType) {
        if (Set.class.isAssignableFrom(collectionType)) {
            return (Collector) toSet();
        }

        return (Collector) toList();
    }


    private static class InternalCollector<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        InternalCollector(Supplier<A> supplier,
                          BiConsumer<A, T> accumulator,
                          BinaryOperator<A> combiner,
                          Function<A, R> finisher,
                          Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        InternalCollector(Supplier<A> supplier,
                          BiConsumer<A, T> accumulator,
                          BinaryOperator<A> combiner,
                          Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

    @SuppressWarnings("unchecked")
    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }
}
