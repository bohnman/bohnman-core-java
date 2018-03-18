package com.github.bohnman.core.lang;

import com.github.bohnman.core.library.CoreLibraries;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
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

        Integer compare;

        if ((compare = dateCompare(o1, o2)) != null) {
            return compare;
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

    private static Integer dateCompare(Object o1, Object o2) {
        Object d1 = toDate(o1);
        Object d2 = toDate(o2);

        if (d1 == null || d2 == null) {
            return null;
        }

        if (d1 instanceof LocalDateTime && d2 instanceof LocalDateTime) {
            return ((LocalDateTime) d1).compareTo((LocalDateTime) d2);
        }

        if (d1 instanceof LocalDateTime && d2 instanceof OffsetDateTime) {
            OffsetDateTime odt2 = ((OffsetDateTime) d2).atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime();
            OffsetDateTime odt1 = ((LocalDateTime) d1).atOffset(ZoneOffset.UTC);
            return odt1.compareTo(odt2);
        }

        if (d1 instanceof OffsetDateTime && d2 instanceof LocalDateTime) {
            OffsetDateTime odt1 = ((OffsetDateTime) d1).atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime();
            OffsetDateTime odt2 = ((LocalDateTime) d2).atOffset(ZoneOffset.UTC);
            return odt1.compareTo(odt2);
        }

        return null;
    }

    private static Object toDate(Object o) {
        if (o instanceof Date) {
            return Instant.ofEpochMilli(((Date) o).getTime()).atOffset(ZoneOffset.UTC);
        }

        if (o instanceof Calendar) {
            return Instant.ofEpochMilli(((Calendar) o).getTimeInMillis()).atOffset(ZoneOffset.UTC);
        }

        if (o instanceof Instant) {
            return ((Instant) o).atOffset(ZoneOffset.UTC);
        }

        if (o instanceof OffsetDateTime) {
            return o;
        }

        if (o instanceof ZonedDateTime) {
            return ((ZonedDateTime) o).toOffsetDateTime();
        }

        if (o instanceof LocalDateTime) {
            return o;
        }

        if (o instanceof LocalDate) {
            return ((LocalDate) o).atStartOfDay();
        }

        if (o instanceof LocalTime) {
            return ((LocalTime) o).atDate(LocalDate.now());
        }

        if (CoreLibraries.isJodaTimePresent()) {
            return jodaToDate(o);
        }

        return null;
    }

    private static Object jodaToDate(Object o) {
        if (o instanceof org.joda.time.Instant) {
            return Instant.ofEpochMilli(((org.joda.time.Instant) o).getMillis()).atOffset(ZoneOffset.UTC);
        }

        if (o instanceof DateTime) {
            DateTime dt = (DateTime) o;
        }

        if (o instanceof org.joda.time.LocalDateTime) {
            org.joda.time.LocalDateTime dt = (org.joda.time.LocalDateTime) o;
            return LocalDateTime.of(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(), dt.getMinuteOfHour(), dt.getSecondOfMinute(), dt.getMillisOfSecond() * 1000000);
        }

        if (o instanceof org.joda.time.LocalDate) {
            org.joda.time.LocalDateTime dt = ((org.joda.time.LocalDate) o).toLocalDateTime(org.joda.time.LocalTime.MIDNIGHT);
            return LocalDateTime.of(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(), dt.getMinuteOfHour(), dt.getSecondOfMinute(), dt.getMillisOfSecond() * 1000000);
        }

        if (o instanceof org.joda.time.LocalTime) {
            org.joda.time.LocalDateTime dt = new org.joda.time.LocalDate().toLocalDateTime((org.joda.time.LocalTime) o);
            return LocalDateTime.of(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(), dt.getMinuteOfHour(), dt.getSecondOfMinute(), dt.getMillisOfSecond() * 1000000);
        }

        return null;
    }


    @SuppressWarnings("unchecked")
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
