package com.github.bohnman.core.logging;

import com.github.bohnman.core.lang.CoreClasses;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.logging.Logger;

public class CoreLogs {

    private static final Function<String, CoreLog> factory;

    static  {
        if (CoreClasses.isPresent("org.slf4j.Logger", CoreLogs.class.getClassLoader())) {
            factory = (name) -> new Slf4jLog(LoggerFactory.getLogger(name));
        } else if (CoreClasses.isPresent("org.apache.commons.logging.Log", CoreLogs.class.getClassLoader())) {
            factory = (name) -> new CommonsLoggingLog(LogFactory.getLog(name));
        } else if (CoreClasses.isPresent("org.apache.logging.log4j.Logger", CoreLogs.class.getClassLoader())) {
            factory = (name) -> new Log4j2Log(LogManager.getLogger(name));
        } else if (CoreClasses.isPresent("org.apache.log4j.Logger", CoreLogs.class.getClassLoader())) {
            factory = (name) -> new Log4j1Log(org.apache.log4j.Logger.getLogger(name));
        } else {
            factory = (name) -> new JulLog(Logger.getLogger(name));
        }
    }


    public  static CoreLog forName(String name) {
        return factory.apply(name);
    }

    public static CoreLog forClass(Class clazz) {
        return forName(clazz.getName());
    }
}
