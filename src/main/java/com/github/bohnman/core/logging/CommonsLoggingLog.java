package com.github.bohnman.core.logging;

import com.github.bohnman.core.lang.CoreAssert;
import org.apache.commons.logging.Log;

public class CommonsLoggingLog implements CoreLog {

    private final Log logger;

    public CommonsLoggingLog(Log logger) {
        this.logger = CoreAssert.notNull(logger);
    }

    @Override
    public String getName() {
        return "Unknown";
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

        if (logger.isFatalEnabled()) {
            return CoreLogLevel.ERROR;
        }

        throw new IllegalStateException("Unable to get core log level");
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
        if (arg instanceof Throwable) {
            log(level, format, (Throwable) arg);
        } else if (isEnabled(level)) {
            log(level, CoreLogMessageFormat.format(format, arg));
        }
    }

    @Override
    public void log(CoreLogLevel level, String format, Object arg1, Object arg2) {
        if (!isEnabled(level)) {
            return;
        }

        if (arg2 instanceof Throwable) {
            log(level, CoreLogMessageFormat.format(format, arg1), (Throwable) arg2);
        } else {
            log(level, CoreLogMessageFormat.format(format, arg1, arg2));
        }
    }

    @Override
    public void log(CoreLogLevel level, String format, Object... arguments) {
        if (arguments.length == 0) {
            log(level, format);
            return;
        }

        if (!isEnabled(level)) {
            return;
        }

        Object lastArg = arguments[arguments.length - 1];

        if (lastArg instanceof Throwable) {
            if (arguments.length == 1) {
                log(level, format, (Throwable) lastArg);
            } else {
                log(level, CoreLogMessageFormat.format(format, arguments), (Throwable) lastArg);
            }
        } else {
            log(level, CoreLogMessageFormat.format(format, arguments));
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
