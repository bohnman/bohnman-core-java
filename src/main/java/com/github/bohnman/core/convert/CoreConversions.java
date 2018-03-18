package com.github.bohnman.core.convert;

import com.github.bohnman.core.collect.CoreArrays;
import com.github.bohnman.core.function.CoreLambda;
import com.github.bohnman.core.lang.CoreClasses;
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
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.function.Predicate;

public class CoreConversions {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    @Nullable
    public static String toString(@Nullable Object o) {
        if (o == null) {
            return null;
        }

        if (o.getClass().isArray()) {
            return CoreArrays.wrap(o).toString();
        }

        return o.toString();
    }

    public static String safeToString(@Nullable Object o) {
        if (o == null) {
            return "";
        }

        if (o.getClass().isArray()) {
            return "";
        }

        if (o instanceof Map) {
            return "";
        }

        if (o instanceof Number) {
            return o.toString();
        }

        if (o instanceof String) {
            return o.toString();
        }

        if (CoreClasses.isPrimitiveOrWrapper(o.getClass())) {
            return o.toString();
        }

        String str;

        if ((str = dateToString(o)) != null) {
            return str;
        }

        return "";
    }

    private static String dateToString(Object o) {
        if (o instanceof Date) {
            return ((Date) o).toInstant().atOffset(ZoneOffset.UTC).format(DATE_TIME_FORMATTER);
        }

        if (o instanceof Calendar) {
            return ((Calendar) o).toInstant().atOffset(ZoneOffset.UTC).format(DATE_TIME_FORMATTER);
        }

        if (o instanceof Instant) {
            return ((Instant) o).atOffset(ZoneOffset.UTC).format(DATE_TIME_FORMATTER);
        }

        if (o instanceof OffsetDateTime) {
            return ((OffsetDateTime) o).format(DATE_TIME_FORMATTER);
        }

        if (o instanceof ZonedDateTime) {
            return ((ZonedDateTime) o).format(DATE_TIME_FORMATTER);
        }

        if (o instanceof LocalDateTime) {
            return ((LocalDateTime) o).format(LOCAL_DATE_TIME_FORMATTER);
        }

        if (o instanceof LocalDate) {
            return ((LocalDate) o).format(LOCAL_DATE_FORMATTER);
        }

        if (o instanceof LocalTime) {
            return ((LocalTime) o).format(LOCAL_TIME_FORMATTER);
        }

        if (o instanceof TimeZone) {
            return ((TimeZone) o).getID();
        }

        if (CoreLibraries.isJodaTimePresent()) {
            return jodaDateToString(o);
        }

        return null;
    }

    private static String jodaDateToString(Object o) {
        if (o instanceof DateTime) {
            DateTime dateTime = (DateTime) o;
            return Instant.ofEpochMilli(dateTime.getMillis()).atZone(dateTime.getZone().toTimeZone().toZoneId()).format(DATE_TIME_FORMATTER);
        }

        if (o instanceof Instant) {
            return Instant.ofEpochMilli(((Instant) o).toEpochMilli()).toString();
        }

        if (o instanceof org.joda.time.LocalDateTime) {
            org.joda.time.LocalDateTime dt = (org.joda.time.LocalDateTime) o;
            return LocalDateTime.of(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(), dt.getMinuteOfHour(), dt.getSecondOfMinute(), dt.getMillisOfSecond() * 1000000).format(LOCAL_DATE_FORMATTER);
        }

        if (o instanceof org.joda.time.LocalDate) {
            org.joda.time.LocalDate dt = (org.joda.time.LocalDate) o;
            return LocalDate.of(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth()).format(LOCAL_DATE_FORMATTER);
        }

        if (o instanceof org.joda.time.LocalTime) {
            org.joda.time.LocalTime dt = (org.joda.time.LocalTime) o;
            return LocalTime.of(dt.getHourOfDay(), dt.getMinuteOfHour(), dt.getSecondOfMinute(), dt.getMillisOfSecond() * 1000000).format(LOCAL_TIME_FORMATTER);
        }

        return null;
    }


    @Nullable
    public static Number toNumber(@Nullable Object o) {
        return toNumber(o, 0);
    }

    @Nullable
    public static Number toNumber(@Nullable Object o, @Nullable Number defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        if (o instanceof Number) {
            return (Number) o;
        }

        if (o instanceof String) {
            try {
                return Double.parseDouble((String) o);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        return defaultValue;
    }

    @SuppressWarnings("unchecked")
    public static boolean toBoolean(@Nullable Object o) {
        if (o == null) {
            return false;
        }

        if (o instanceof Boolean) {
            return (Boolean) o;
        }

        if (o instanceof Number) {
            return ((Number) o).doubleValue() != 0;
        }

        if (o instanceof String) {
            return !"".equals(o);
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    public static Function toFunction(Object o) {
        if (o instanceof Function) {
            return (Function) o;
        }

        if (o instanceof Predicate) {
            return ((Predicate) o)::test;
        }

        return (in) -> o;
    }

    @SuppressWarnings("unchecked")
    public static CoreLambda toLambda(Object o) {
        if (o instanceof CoreLambda) {
            return (CoreLambda) o;
        }

        if (o instanceof Function) {
            Function f = (Function) o;
            return args -> args.length == 0 ? f.apply(null) : f.apply(args[0]);
        }

        if (o instanceof Predicate) {
            Predicate p = (Predicate) o;
            return args -> args.length == 0 ? p.test(null) : p.test(args[0]);
        }

        return args -> o;
    }

    @SuppressWarnings("unchecked")
    public static Predicate toPredicate(Object o) {
        if (o instanceof Predicate) {
            return (Predicate) o;
        }

        if (o instanceof Function) {
            return (in) -> toBoolean(((Function) o).apply(in));
        }

        return (in) -> toBoolean(o);
    }

    /**
     * Parse the given {@code timeZoneString} value into a {@link TimeZone}.
     *
     * @param timeZoneString the time zone {@code String}, following {@link TimeZone#getTimeZone(String)}
     *                       but throwing {@link IllegalArgumentException} in case of an invalid time zone specification
     * @return a corresponding {@link TimeZone} instance
     * @throws IllegalArgumentException in case of an invalid time zone specification
     */
    public static TimeZone toTimeZone(String timeZoneString) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        if ("GMT".equals(timeZone.getID()) && !timeZoneString.startsWith("GMT")) {
            // We don't want that GMT fallback...
            throw new IllegalArgumentException("Invalid time zone specification '" + timeZoneString + "'");
        }
        return timeZone;
    }
}
