package com.github.bohnman.core.lang;

import javax.annotation.Nullable;
import java.util.Objects;

public class CoreObjects {

    private CoreObjects() {
    }

    public static Integer compare(@Nullable Object o1, @Nullable Object o2) {
        return compare(o1, o2, null);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static Integer compare(@Nullable Object o1, @Nullable Object o2, Integer defaultValue) {
        if (equals(o1, o2)) {
            return 0;
        }

        if (o1 == null || o2 == null) {
            return defaultValue;
        }

        if (o1 instanceof Number && o2 instanceof Number) {
            double d1 = ((Number) o1).doubleValue();
            double d2 = ((Number) o2).doubleValue();
            return Double.compare(d1, d2);
        }

        if (!(o1 instanceof Comparable)) {
            return defaultValue;
        }

        if (!(o2 instanceof Comparable)) {
            return defaultValue;
        }

        if (!o1.getClass().isAssignableFrom(o2.getClass())) {
            return defaultValue;
        }

        Comparable c1 = (Comparable) o1;
        Comparable c2 = (Comparable) o2;

        try {
            return c1.compareTo(c2);
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }


    @SuppressWarnings("unchecked")
    @Nullable
    public static boolean equals(@Nullable Object o1, @Nullable Object o2) {
        if (o1 instanceof Number && o2 instanceof Number) {
            double d1 = ((Number) o1).doubleValue();
            double d2 = ((Number) o2).doubleValue();
            return d1 == d2;
        }

        return Objects.equals(o1, o2);
    }


    public static <T> T firstNonNull(T o1, T o2) {
        return (o1 == null) ? o2 : o1;
    }
}
