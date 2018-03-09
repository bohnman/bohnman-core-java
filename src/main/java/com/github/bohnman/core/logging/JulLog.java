package com.github.bohnman.core.logging;

import com.github.bohnman.core.lang.CoreAssert;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JulLog implements CoreLog {

    private final Logger logger;

    public JulLog(Logger logger) {
        this.logger = CoreAssert.notNull(logger);
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public CoreLogLevel getLevel() {
        Level level = logger.getLevel();

        switch (level.getName()) {
            case "OFF":
            case "SEVERE":
                return CoreLogLevel.ERROR;
            case "WARNING":
                return CoreLogLevel.WARN;
            case "INFO":
                return CoreLogLevel.INFO;
            case "CONFIG":
            case "FINE":
            case "FINER":
                return CoreLogLevel.DEBUG;
            case "FINEST":
            case "ALL":
                return CoreLogLevel.TRACE;
            default:
                throw new IllegalStateException("Unhandled level: " + level.getName());
        }

    }

    private Level toJulLevel(CoreLogLevel level) {
        switch (level) {
            case TRACE:
                return Level.FINEST;
            case DEBUG:
                return Level.FINE;
            case INFO:
                return Level.INFO;
            case WARN:
                return Level.WARNING;
            case ERROR:
                return Level.SEVERE;
            default:
                throw new IllegalStateException("Unhandled core level: " + level);
        }
    }

    @Override
    public boolean isEnabled(CoreLogLevel level) {
        return logger.isLoggable(toJulLevel(level));
    }

    @Override
    public void log(CoreLogLevel level, String msg) {
        logger.log(toJulLevel(level), msg);
    }

    @Override
    public void log(CoreLogLevel level, String format, Object arg) {
        Level julLevel = toJulLevel(level);

        if (!logger.isLoggable(julLevel)) {
            return;
        }

        if (arg instanceof Throwable) {
            logger.log(julLevel, format, (Throwable) arg);
        } else {
            logger.log(julLevel, CoreLogMessageFormat.format(format, arg));
        }
    }

    @Override
    public void log(CoreLogLevel level, String format, Object arg1, Object arg2) {
        Level julLevel = toJulLevel(level);

        if (!logger.isLoggable(julLevel)) {
            return;
        }

        if (arg2 instanceof Throwable) {
            logger.log(julLevel, CoreLogMessageFormat.format(format, arg1), (Throwable) arg2);
        } else {
            logger.log(julLevel, CoreLogMessageFormat.format(format, arg1, arg2));
        }
    }

    @Override
    public void log(CoreLogLevel level, String format, Object... arguments) {
        Level julLevel = toJulLevel(level);

        if (!logger.isLoggable(julLevel)) {
            return;
        }

        if (arguments.length == 0) {
            logger.log(julLevel, format);
            return;
        }

        Object lastArg = arguments[arguments.length - 1];

        if (lastArg instanceof Throwable) {
            if (arguments.length == 1) {
                logger.log(julLevel, format, (Throwable) lastArg);
            } else {
                logger.log(julLevel, CoreLogMessageFormat.format(format, arguments), (Throwable) lastArg);
            }

        } else {
            logger.log(julLevel, CoreLogMessageFormat.format(format, arguments));
        }
    }

    @Override
    public void log(CoreLogLevel level, String msg, Throwable t) {
        logger.log(toJulLevel(level), msg, t);
    }
}
