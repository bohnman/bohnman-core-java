package com.github.bohnman.core.function;

import com.github.bohnman.core.collect.CoreArrays;

import javax.annotation.Nullable;

public interface CoreLambda extends FunctionPredicateBridge<Object, Object> {

    static CoreLambda identity() {
        return new CoreLambda() {
            @Nullable
            @Override
            public Object invoke(Object... arguments) {
                return arguments.length == 0 ? null : arguments[0];
            }
        };
    }

    @Nullable
    Object invoke(Object... arguments);

    @Override
    @Nullable
    default Object apply(@Nullable Object arguments) {
        if (arguments == null) {
            return invoke(new Object[]{null});
        }

        if (arguments instanceof Object[]) {
            return invoke((Object[]) arguments);
        }

        if (arguments.getClass().isArray()) {
            invoke(CoreArrays.wrap(arguments).toArray());
        }

        return invoke(arguments);
    }

}
