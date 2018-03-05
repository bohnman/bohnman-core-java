package com.github.bohnman.core.json.node;

@FunctionalInterface
public interface CoreJsonNodePredicate<T> {

    boolean test(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node);
}
