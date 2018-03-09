package com.github.bohnman.core.logging;

import com.github.bohnman.core.lang.CoreAssert;
import org.slf4j.Logger;

@SuppressWarnings("Duplicates")
public class Slf4jLog implements CoreLog {

    private final Logger logger;

    public Slf4jLog(Logger logger) {
        this.logger = CoreAssert.notNull(logger);
    }


    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public CoreLogLevel getLevel() {
        if (logger.isTraceEnabled()) {
            return CoreLogLevel.TRACE;
        }

        if (logger.isDebugEnabled()) {
            return CoreLogLevel.DEBUG;
        }

        if (logger.isInfoEnabled()) {
            return CoreLogLevel.INFO;
        }

        if (logger.isWarnEnabled()) {
            return CoreLogLevel.WARN;
        }

        if (logger.isErrorEnabled()) {
            return CoreLogLevel.ERROR;
        }

        throw new IllegalStateException("Could not determine logging level.");
    }

    @Override
    public boolean isEnabled(CoreLogLevel level) {
        switch (level) {
            case TRACE:
                return logger.isTraceEnabled();
            case DEBUG:
                return logger.isDebugEnabled();
            case INFO:
                return logger.isInfoEnabled();
            case WARN:
                return logger.isWarnEnabled();
            case ERROR:
                return logger.isErrorEnabled();
            default:
                throw new IllegalStateException("Unhandled log level: " + level);
        }
    }

    @Override
    public void log(CoreLogLevel level, String msg) {
        switch (level) {
            case TRACE:
                logger.trace(msg);
                break;
            case DEBUG:
                logger.debug(msg);
                break;
            case INFO:
                logger.info(msg);
                break;
            case WARN:
                logger.warn(msg);
                break;
            case ERROR:
                logger.error(msg);
                break;
            default:
                throw new IllegalStateException("Unhandled log level: " + level);
        }
    }

    @Override
    public void log(CoreLogLevel level, String format, Object arg) {
        switch (level) {
            case TRACE:
                logger.trace(format, arg);
                break;
            case DEBUG:
                logger.debug(format, arg);
                break;
            case INFO:
                logger.info(format, arg);
                break;
            case WARN:
                logger.warn(format, arg);
                break;
            case ERROR:
                logger.error(format, arg);
                break;
            default:
                throw new IllegalStateException("Unhandled log level: " + level);
        }
    }

    @Override
    public void log(CoreLogLevel level, String format, Object arg1, Object arg2) {
        switch (level) {
            case TRACE:
                logger.trace(format, arg1, arg2);
                break;
            case DEBUG:
                logger.debug(format, arg1, arg2);
                break;
            case INFO:
                logger.info(format, arg1, arg2);
                break;
            case WARN:
                logger.warn(format, arg1, arg2);
                break;
            case ERROR:
                logger.error(format, arg1, arg2);
                break;
            default:
                throw new IllegalStateException("Unhandled log level: " + level);
        }
    }

    @Override
    public void log(CoreLogLevel level, String format, Object... arguments) {
        switch (level) {
            case TRACE:
                logger.trace(format, arguments);
                break;
            case DEBUG:
                logger.debug(format, arguments);
                break;
            case INFO:
                logger.info(format, arguments);
                break;
            case WARN:
                logger.warn(format, arguments);
                break;
            case ERROR:
                logger.error(format, arguments);
                break;
            default:
                throw new IllegalStateException("Unhandled log level: " + level);
        }
    }

    @Override
    public void log(CoreLogLevel level, String msg, Throwable t) {
        switch (level) {
            case TRACE:
                logger.trace(msg, t);
                break;
            case DEBUG:
                logger.debug(msg, t);
                break;
            case INFO:
                logger.info(msg, t);
                break;
            case WARN:
                logger.warn(msg, t);
                break;
            case ERROR:
                logger.error(msg, t);
                break;
            default:
                throw new IllegalStateException("Unhandled log level: " + level);
        }
    }
}
