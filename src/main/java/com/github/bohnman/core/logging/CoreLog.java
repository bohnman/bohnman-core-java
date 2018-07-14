package com.github.bohnman.core.logging;


public interface CoreLog {

    String getName();

    CoreLogLevel getLevel();

    boolean isEnabled(CoreLogLevel level);

    void log(CoreLogLevel level, String msg);

    void log(CoreLogLevel level, String format, Object arg);

    void log(CoreLogLevel level, String format, Object arg1, Object arg2);

    void log(CoreLogLevel level, String format, Object... arguments);

    void log(CoreLogLevel level, String msg, Throwable t);


    // Trace
    default boolean isTraceEnabled() {
        return isEnabled(CoreLogLevel.TRACE);
    }

    default void trace(String msg) {
        log(CoreLogLevel.TRACE, msg);
    }

    default void trace(String format, Object arg) {
        log(CoreLogLevel.TRACE, format, arg);
    }

    default void trace(String format, Object arg1, Object arg2) {
        log(CoreLogLevel.TRACE, format, arg1, arg2);
    }

    default void trace(String format, Object... arguments) {
        log(CoreLogLevel.TRACE, format, arguments);
    }

    default void trace(String msg, Throwable t) {
        log(CoreLogLevel.TRACE, msg, t);
    }


    // Debug
    default boolean isDebugEnabled() {
        return isEnabled(CoreLogLevel.DEBUG);
    }

    default void debug(String msg) {
        log(CoreLogLevel.DEBUG, msg);
    }

    default void debug(String format, Object arg) {
        log(CoreLogLevel.DEBUG, format, arg);
    }

    default void debug(String format, Object arg1, Object arg2) {
        log(CoreLogLevel.DEBUG, format, arg1, arg2);
    }

    default void debug(String format, Object... arguments) {
        log(CoreLogLevel.DEBUG, format, arguments);
    }

    default void debug(String msg, Throwable t) {
        log(CoreLogLevel.DEBUG, msg, t);
    }


    // Info
    default boolean isInfoEnabled() {
        return isEnabled(CoreLogLevel.INFO);
    }

    default void info(String msg) {
        log(CoreLogLevel.INFO, msg);
    }

    default void info(String format, Object arg) {
        log(CoreLogLevel.INFO, format, arg);
    }

    default void info(String format, Object arg1, Object arg2) {
        log(CoreLogLevel.INFO, format, arg1, arg2);
    }

    default void info(String format, Object... arguments) {
        log(CoreLogLevel.INFO, format, arguments);
    }

    default void info(String msg, Throwable t) {
        log(CoreLogLevel.INFO, msg, t);
    }

    // Warn
    default boolean isWarnEnabled() {
        return isEnabled(CoreLogLevel.WARN);
    }

    default void warn(String msg) {
        log(CoreLogLevel.WARN, msg);
    }

    default void warn(String format, Object arg) {
        log(CoreLogLevel.WARN, format, arg);
    }

    default void warn(String format, Object arg1, Object arg2) {
        log(CoreLogLevel.WARN, format, arg1, arg2);
    }

    default void warn(String format, Object... arguments) {
        log(CoreLogLevel.WARN, format, arguments);
    }

    default void warn(String msg, Throwable t) {
        log(CoreLogLevel.WARN, msg, t);
    }

    // Error
    default boolean isErrorEnabled() {
        return isEnabled(CoreLogLevel.ERROR);
    }

    default void error(String msg) {
        log(CoreLogLevel.ERROR, msg);
    }

    default void error(String format, Object arg) {
        log(CoreLogLevel.ERROR, format, arg);
    }

    default void error(String format, Object arg1, Object arg2) {
        log(CoreLogLevel.ERROR, format, arg1, arg2);
    }

    default void error(String format, Object... arguments) {
        log(CoreLogLevel.ERROR, format, arguments);
    }

    default void error(String msg, Throwable t) {
        log(CoreLogLevel.ERROR, msg, t);
    }

}
