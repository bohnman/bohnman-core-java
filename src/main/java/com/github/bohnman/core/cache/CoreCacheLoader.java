package com.github.bohnman.core.cache;

@FunctionalInterface
public interface CoreCacheLoader<K, V> {
    V load(K key) throws Exception;
}