package com.github.bohnman.core.library;

import com.github.bohnman.core.lang.CoreClasses;

public class CoreLibraries {

    private static final boolean COMMONS_LOGGING_PRESENT = CoreClasses.isPresent("org.apache.commons.logging.Log", CoreLibraries.class.getClassLoader());
    private static final boolean JODA_TIME_PRESENT = CoreClasses.isPresent("org.joda.time.LocalDate", CoreLibraries.class.getClassLoader());
    private static final boolean LOG4J1_PRESENT = CoreClasses.isPresent("org.apache.log4j.Logger", CoreLibraries.class.getClassLoader());
    private static final boolean LOG4J2_PRESENT = CoreClasses.isPresent("org.apache.logging.log4j.Logger", CoreLibraries.class.getClassLoader());
    private static final boolean SLF4J_PRESENT = CoreClasses.isPresent("org.slf4j.Logger", CoreLibraries.class.getClassLoader());

    private CoreLibraries() {
    }

    public static boolean isCommonsLoggingPresent() {
        return COMMONS_LOGGING_PRESENT;
    }

    public static boolean isJodaTimePresent() {
        return JODA_TIME_PRESENT;
    }

    public static boolean isLog4j1Present() {
        return LOG4J1_PRESENT;
    }

    public static boolean isLog4j2Present() {
        return LOG4J2_PRESENT;
    }

    public static boolean isSlf4jPresent() {
        return SLF4J_PRESENT;
    }
}
