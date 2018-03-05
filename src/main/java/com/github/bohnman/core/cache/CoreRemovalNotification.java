package com.github.bohnman.core.cache;

public class CoreRemovalNotification<K, V> {
    public enum RemovalReason {REPLACED, INVALIDATED, EVICTED}

    private final K key;
    private final V value;
    private final RemovalReason removalReason;

    public CoreRemovalNotification(K key, V value, RemovalReason removalReason) {
        this.key = key;
        this.value = value;
        this.removalReason = removalReason;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public RemovalReason getRemovalReason() {
        return removalReason;
    }
}
