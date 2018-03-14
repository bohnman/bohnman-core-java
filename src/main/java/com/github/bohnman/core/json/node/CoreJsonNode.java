package com.github.bohnman.core.json.node;

import com.github.bohnman.core.tuple.CorePair;

import java.util.List;

public interface CoreJsonNode<T> {

    CoreJsonNodeType getType();

    Object getValue();

    T getRawNode();

    List<CorePair<String, CoreJsonNode<T>>> getObjectElements();

    CoreJsonNode<T> create(Object value);

    CoreJsonNode<T> createArray(List<CoreJsonNode<T>> elements);

    CoreJsonNode<T> createObject(List<CorePair<String, CoreJsonNode<T>>> elements);

    List<CoreJsonNode<T>> getArrayElements();

    List<CoreJsonNode<T>> find(CoreJsonNodePredicate<T> predicate);

    CoreJsonNode<T> transform(CoreJsonNodeFunction<T> function);

    default boolean isType(CoreJsonNodeType type) {
        return getType() == type;
    }

    default boolean isArray() {
        return isType(CoreJsonNodeType.ARRAY);
    }

    default boolean isBinary() {
        return isType(CoreJsonNodeType.BINARY);
    }

    default boolean isBoolean() {
        return isType(CoreJsonNodeType.BOOLEAN);
    }

    default boolean isNull() {
        return isType(CoreJsonNodeType.NULL);
    }

    default boolean isNumber() {
        return isType(CoreJsonNodeType.NUMBER);
    }

    default boolean isObject() {
        return isType(CoreJsonNodeType.OBJECT);
    }

    default boolean isString() {
        return isType(CoreJsonNodeType.STRING);
    }
}
