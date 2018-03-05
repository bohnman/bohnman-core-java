package com.github.bohnman.core.json.node;

public interface CoreJsonNodeVisitor<T, R> {

    R visit(CoreJsonNode<T> node);
}
