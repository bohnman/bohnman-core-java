package com.github.bohnman.core.cache;

@FunctionalInterface
public interface CoreRemovalListener<K, V> {
    void onRemoval(CoreRemovalNotification<K, V> notification);
}
