package com.github.bohnman.core.logging;

import com.github.bohnman.core.lang.CoreAssert;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4j1Log implements CoreLog {

    private final Logger logger;

    public Log4j1Log(Logger logger) {
        this.logger = CoreAssert.notNull(logger);
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public CoreLogLevel getLevel() {
        Level level = logger.getLevel();

        switch (level.toString()) {
            case "OFF":
                return CoreLogLevel.ERROR;
            case "FATAL":
                return CoreLogLevel.ERROR;
            case "ERROR":
                return CoreLogLevel.ERROR;
            case "WARN":
                return CoreLogLevel.WARN;
            case "INFO":
                return CoreLogLevel.INFO;
            case "DEBUG":
                return CoreLogLevel.DEBUG;
            case "TRACE":
                return CoreLogLevel.TRACE;
            case "ALL":
                return CoreLogLevel.TRACE;
            default:
                throw new IllegalStateException("Unhandled level " + level);
        }
    }

    private Level toLog4jLevel(CoreLogLevel level) {
        switch (level) {
            case TRACE:
                return Level.TRACE;
            case DEBUG:
                return Level.DEBUG;
            case INFO:
                return Level.INFO;
            case WARN:
                return Level.WARN;
            case ERROR:
                return Level.ERROR;
            default:
                throw new IllegalStateException("Unhandled core level: " + level);
        }

    }

    @Override
    public boolean isEnabled(CoreLogLevel level) {
        return logger.isEnabledFor(toLog4jLevel(level));
    }

    @Override
    public void log(CoreLogLevel level, String msg) {
        logger.log(toLog4jLevel(level), msg);
    }

    @Override
    public void log(CoreLogLevel level, String format, Object arg) {
        if (arg instanceof Throwable) {
            logger.log(toLog4jLevel(level), format, (Throwable) arg);
        } else {
            logger.log(toLog4jLevel(level), arg);
        }
    }

    @Override
    public void log(CoreLogLevel level, String format, Object arg1, Object arg2) {
        if (!isEnabled(level)) {
            return;
        }

        if (arg2 instanceof Throwable) {
            logger.log(toLog4jLevel(level), CoreLogMessageFormat.format(format, arg1), (Throwable) arg2);
        } else {
            logger.log(toLog4jLevel(level), CoreLogMessageFormat.format(format, arg1, arg2));
        }

    }

    @Override
    public void log(CoreLogLevel level, String format, Object... arguments) {
        if (arguments.length == 0) {
            logger.log(toLog4jLevel(level), format);
        }

        if (!isEnabled(level)) {
            return;
        }

        Object lastArg = arguments[arguments.length - 1];

        if (lastArg instanceof Throwable) {
            if (arguments.length == 1) {
                logger.log(toLog4jLevel(level), format, (Throwable) lastArg);
            } else {
                logger.log(toLog4jLevel(level), CoreLogMessageFormat.format(format, arguments), (Throwable) lastArg);
            }
        } else {
            logger.log(toLog4jLevel(level), CoreLogMessageFormat.format(format, arguments));
        }
    }

    @Override
    public void log(CoreLogLevel level, String msg, Throwable t) {
        logger.log(toLog4jLevel(level), msg, t);
    }
}
