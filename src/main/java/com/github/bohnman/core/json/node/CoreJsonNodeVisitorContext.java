package com.github.bohnman.core.json.node;

import com.github.bohnman.core.json.path.CoreJsonPath;
import com.github.bohnman.core.json.path.CoreJsonPathElement;

public class CoreJsonNodeVisitorContext {

    private static final Object ROOT_KEY = new Object();


    private final int depth;
    private Object key;
    private final CoreJsonPath objectPath;
    private final CoreJsonPath absolutePath;
    private final Object parent;

    public CoreJsonNodeVisitorContext() {
        this(0, ROOT_KEY, null, CoreJsonPath.empty(), CoreJsonPath.empty());
    }

    private CoreJsonNodeVisitorContext(int depth, Object key, Object parent, CoreJsonPath absolutePath, CoreJsonPath objectPath) {
        this.depth = depth;
        this.key = key;
        this.absolutePath = absolutePath;
        this.objectPath = objectPath;
        this.parent = parent;
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

    public Object getParent() {
        return parent;
    }

    public CoreJsonNodeVisitorContext descend(Object key, Object parent, CoreJsonPathElement absolutePathElement, CoreJsonPathElement objectPathElement) {
        CoreJsonPath absolutePath = (absolutePathElement == null) ? this.absolutePath : this.absolutePath.add(absolutePathElement);
        CoreJsonPath objectPath = (objectPathElement == null) ? this.objectPath : this.objectPath.add(objectPathElement);
        return new CoreJsonNodeVisitorContext(depth + 1, key, parent, absolutePath, objectPath);
    }
}
