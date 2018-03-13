package com.github.bohnman.core.json.node;

import com.github.bohnman.core.convert.CoreConversions;
import com.github.bohnman.core.json.path.CoreJsonPathElement;
import com.github.bohnman.core.lang.CoreAssert;
import com.github.bohnman.core.tuple.CorePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BaseCoreJsonNodeVisitor<T> {
    public CoreJsonNode<T> visit(CoreJsonNode<T> node) {
        CoreJsonNode<T> result = visitGenericNode(new CoreJsonNodeVisitorContext(), CoreAssert.notNull(node));
        return (result == null) ? node.create(null) : result;
    }

    protected CoreJsonNode<T> visitGenericNode(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node) {
        CoreJsonNode<T> result;

        switch (node.getType()) {
            case ARRAY:
                result = visitArray(context, node);
                break;
            case BINARY:
                result = visitBinary(context, node);
                break;
            case BOOLEAN:
                result = visitBoolean(context, node);
                break;
            case NULL:
                result = visitNull(context, node);
                break;
            case NUMBER:
                result = visitNumber(context, node);
                break;
            case STRING:
                result = visitString(context, node);
                break;
            case OBJECT:
                result = visitObject(context, node);
                break;
            default:
                throw new IllegalStateException("Unhandled node type: " + node.getType());
        }

        if (result == null) {
            return null;
        }

        return visitChildren(context, result);
    }

    protected CoreJsonNode<T> visitChildren(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node) {
        if (node.getType() == CoreJsonNodeType.ARRAY) {
            return visitArrayChildren(context, node);
        }

        if (node.getType() == CoreJsonNodeType.OBJECT) {
            return visitObjectChildren(context, node);
        }

        return node;
    }

    protected CoreJsonNode<T> visitArray(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node) {
        return visitAtomNode(context, node);
    }

    protected CoreJsonNode<T> visitArrayChildren(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node) {
        List<CoreJsonNode<T>> newElements = null;
        Object array = node.getValue();

        for (int i = 0; i < node.getArrayElements().size(); i++) {
            CoreJsonNode<T> child = node.getArrayElements().get(i);
            CoreJsonNode<T> newChild = visitGenericNode(context.descend(i, array, new CoreJsonPathElement(Integer.toString(i), array), null), child);

            if (child != newChild) {
                if (newElements == null) newElements = new ArrayList<>(node.getArrayElements());
                newElements.set(i, newChild);
            }
        }

        if (newElements != null) {
            newElements.removeIf(Objects::isNull);
            return node.createArray(newElements);
        }

        return node;
    }

    protected CoreJsonNode<T> visitBinary(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node) {
        return visitAtomNode(context, node);
    }

    protected CoreJsonNode<T> visitBoolean(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node) {
        return visitAtomNode(context, node);
    }

    protected CoreJsonNode<T> visitNull(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node) {
        return visitAtomNode(context, node);
    }

    protected CoreJsonNode<T> visitNumber(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node) {
        return visitAtomNode(context, node);
    }

    protected CoreJsonNode<T> visitString(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node) {
        return visitAtomNode(context, node);
    }

    protected CoreJsonNode<T> visitObject(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node) {
        return visitAtomNode(context, node);
    }

    protected CoreJsonNode<T> visitObjectChildren(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node) {
        List<CorePair<String, CoreJsonNode<T>>> newElements = null;
        Object object = node.getValue();

        for (int i = 0; i < node.getObjectElements().size(); i++) {
            CorePair<String, CoreJsonNode<T>> pair = node.getObjectElements().get(i);
            CoreJsonNode<T> child = pair.getRight();
            String key = pair.getLeft();
            CoreJsonPathElement objectPathElement = new CoreJsonPathElement(key, object);
            CoreJsonNodeVisitorContext newContext = context.descend(key, object, objectPathElement, objectPathElement);
            CoreJsonNode<T> newChild = visitGenericNode(newContext, pair.getRight());
            key = CoreConversions.toString(newContext.getKey());

            if (child != newChild || !Objects.equals(pair.getLeft(), key)) {
                if (newElements == null) newElements = new ArrayList<>(node.getObjectElements());
                if (key == null || newChild == null) {
                    newElements.set(i, null);
                } else {
                    newElements.set(i, CorePair.of(key, newChild));
                }
            }
        }

        if (newElements != null) {
            newElements.removeIf(Objects::isNull);
            return node.createObject(newElements);
        }

        return node;
    }


    protected CoreJsonNode<T> visitAtomNode(CoreJsonNodeVisitorContext context, CoreJsonNode<T> node) {
        return node;
    }

}
