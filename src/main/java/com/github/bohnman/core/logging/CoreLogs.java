package com.github.bohnman.core.logging;

import com.github.bohnman.core.library.CoreLibraries;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.logging.Logger;

public class CoreLogs {

    private static final Function<String, CoreLog> factory;

    static {
        if (CoreLibraries.isSlf4jPresent()) {
            factory = (name) -> new Slf4jLog(LoggerFactory.getLogger(name));
        } else if (CoreLibraries.isCommonsLoggingPresent()) {
            factory = (name) -> new CommonsLoggingLog(LogFactory.getLog(name));
        } else if (CoreLibraries.isLog4j2Present()) {
            factory = (name) -> new Log4j2Log(LogManager.getLogger(name));
        } else if (CoreLibraries.isLog4j1Present()) {
            factory = (name) -> new Log4j1Log(org.apache.log4j.Logger.getLogger(name));
        } else {
            factory = (name) -> new JulLog(Logger.getLogger(name));
        }
    }


    public static CoreLog forName(String name) {
        return factory.apply(name);
    }

    public static CoreLog forClass(Class clazz) {
        return forName(clazz.getName());
    }
}
