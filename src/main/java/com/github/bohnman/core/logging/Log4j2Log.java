package com.github.bohnman.core.logging;

import com.github.bohnman.core.lang.CoreAssert;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.StandardLevel;

public class Log4j2Log implements CoreLog {

    private final Logger logger;

    public Log4j2Log(Logger logger) {
        this.logger = CoreAssert.notNull(logger);
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public CoreLogLevel getLevel() {
        Level level = logger.getLevel();
        StandardLevel standardLevel = level.getStandardLevel();

        if (standardLevel == null) {
            return CoreLogLevel.ERROR;
        }

        switch (standardLevel) {
            case OFF:
                return CoreLogLevel.ERROR;
            case FATAL:
                return CoreLogLevel.ERROR;
            case ERROR:
                return CoreLogLevel.ERROR;
            case WARN:
                return CoreLogLevel.WARN;
            case INFO:
                return CoreLogLevel.INFO;
            case DEBUG:
                return CoreLogLevel.DEBUG;
            case TRACE:
                return CoreLogLevel.TRACE;
            case ALL:
                return CoreLogLevel.TRACE;
            default:
                throw new IllegalStateException("Unhandled level " + standardLevel);
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
        return logger.isEnabled(toLog4jLevel(level));
    }

    @Override
    public void log(CoreLogLevel level, String msg) {
        logger.log(toLog4jLevel(level), msg);
    }

    @Override
    public void log(CoreLogLevel level, String format, Object arg) {
        logger.log(toLog4jLevel(level), arg);
    }

    @Override
    public void log(CoreLogLevel level, String format, Object arg1, Object arg2) {
        logger.log(toLog4jLevel(level), format, arg1, arg2);
    }

    @Override
    public void log(CoreLogLevel level, String format, Object... arguments) {
        logger.log(toLog4jLevel(level), format, arguments);
    }

    @Override
    public void log(CoreLogLevel level, String msg, Throwable t) {
        logger.log(toLog4jLevel(level), msg, t);
    }
}
