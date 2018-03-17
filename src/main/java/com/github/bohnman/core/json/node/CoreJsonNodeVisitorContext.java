package com.github.bohnman.core.json.node;

import com.github.bohnman.core.json.path.CoreJsonPath;
import com.github.bohnman.core.json.path.CoreJsonPathElement;

public class CoreJsonNodeVisitorContext<T> {

    private static final Object ROOT_KEY = new Object();


    private final int depth;
    private Object key;
    private final CoreJsonPath objectPath;
    private final CoreJsonPath absolutePath;
    private final CoreJsonNode<T> parentNode;

    public CoreJsonNodeVisitorContext() {
        this(0, ROOT_KEY, null, CoreJsonPath.empty(), CoreJsonPath.empty());
    }

    private CoreJsonNodeVisitorContext(int depth, Object key, CoreJsonNode<T> parentNode, CoreJsonPath absolutePath, CoreJsonPath objectPath) {
        this.depth = depth;
        this.key = key;
        this.absolutePath = absolutePath;
        this.objectPath = objectPath;
        this.parentNode = parentNode;
    }

    public int getDepth() {
        return depth;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public CoreJsonPath getObjectPath() {
        return objectPath;
    }

    public CoreJsonNode<T> getParentNode() {
        return parentNode;
    }

    public CoreJsonNodeVisitorContext<T> descend(Object key, CoreJsonNode<T> parent, CoreJsonPathElement absolutePathElement, CoreJsonPathElement objectPathElement) {
        CoreJsonPath absolutePath = (absolutePathElement == null) ? this.absolutePath : this.absolutePath.add(absolutePathElement);
        CoreJsonPath objectPath = (objectPathElement == null) ? this.objectPath : this.objectPath.add(objectPathElement);
        return new CoreJsonNodeVisitorContext<>(depth + 1, key, parent, absolutePath, objectPath);
    }
}
