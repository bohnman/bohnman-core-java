package com.github.bohnman.core.json.node;

public interface CoreJsonNodeFunction<T> {

    CoreJsonNode<T> apply(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node);
}
