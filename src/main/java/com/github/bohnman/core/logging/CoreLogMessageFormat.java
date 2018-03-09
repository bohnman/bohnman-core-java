package com.github.bohnman.core.logging;

import java.util.HashMap;
import java.util.Map;

public class CoreLogMessageFormat {

    private static final char DELIM_START = '{';
    private static final char DELIM_STOP = '}';
    private static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';


    public static String format(String msg, Object... args) {
        return format(msg, 0, args.length, args);
    }

    public static String format(String msg, int start, int end, Object... args) {
        if (msg == null || msg.isEmpty()) {
            return "";
        }

        if (args.length == 0) {
            return msg;
        }

        int pos = 0;
        StringBuilder sb = new StringBuilder(msg.length() + 50);

        int argsIndex;

        for (argsIndex = start; argsIndex < end; argsIndex++) {
            int delimStart = msg.indexOf(DELIM_STR, pos);

            if (delimStart < 0) {
                if (pos == 0) {
                    return msg;
                }

                sb.append(msg, pos, msg.length());
                return sb.toString();
            }

            if (isEscapedDelimiter(msg, delimStart)) {
                if (!isDoubleEscaped(msg, delimStart)) {
                    argsIndex--;
                    sb.append(msg, pos, delimStart - 1);
                    sb.append(DELIM_START);
                    pos = delimStart + 1;
                } else {
                    sb.append(msg, pos, delimStart - 1);
                    deeplyAppendParameter(sb, args[argsIndex]);
                    pos = delimStart + DELIM_STR.length();
                }
            } else {
                sb.append(msg, pos, delimStart);
                deeplyAppendParameter(sb, args[argsIndex]);
                pos += delimStart + DELIM_STR.length();
            }


        }

        sb.append(msg, pos, msg.length());

        return sb.toString();
    }

    private static boolean isEscapedDelimiter(String messagePattern, int delimiterStartIndex) {

        if (delimiterStartIndex == 0) {
            return false;
        }
        char potentialEscape = messagePattern.charAt(delimiterStartIndex - 1);
        return potentialEscape == ESCAPE_CHAR;
    }

    private static boolean isDoubleEscaped(String messagePattern, int delimiterStartIndex) {
        return delimiterStartIndex >= 2 && messagePattern.charAt(delimiterStartIndex - 2) == ESCAPE_CHAR;
    }

    private static void deeplyAppendParameter(StringBuilder sbuf, Object o) {
        deeplyAppendParameter(sbuf, o, new HashMap<>());
    }

    private static void deeplyAppendParameter(StringBuilder sbuf, Object o, Map<Object[], Object> seenMap) {
        if (o == null) {
            sbuf.append("null");
            return;
        }
        if (!o.getClass().isArray()) {
            safeObjectAppend(sbuf, o);
        } else {
            // check for primitive array types because they
            // unfortunately cannot be cast to Object[]
            if (o instanceof boolean[]) {
                booleanArrayAppend(sbuf, (boolean[]) o);
            } else if (o instanceof byte[]) {
                byteArrayAppend(sbuf, (byte[]) o);
            } else if (o instanceof char[]) {
                charArrayAppend(sbuf, (char[]) o);
            } else if (o instanceof short[]) {
                shortArrayAppend(sbuf, (short[]) o);
            } else if (o instanceof int[]) {
                intArrayAppend(sbuf, (int[]) o);
            } else if (o instanceof long[]) {
                longArrayAppend(sbuf, (long[]) o);
            } else if (o instanceof float[]) {
                floatArrayAppend(sbuf, (float[]) o);
            } else if (o instanceof double[]) {
                doubleArrayAppend(sbuf, (double[]) o);
            } else {
                objectArrayAppend(sbuf, (Object[]) o, seenMap);
            }
        }
    }

    private static void safeObjectAppend(StringBuilder sbuf, Object o) {
        try {
            String oAsString = o.toString();
            sbuf.append(oAsString);
        } catch (Throwable t) {
            sbuf.append("[FAILED toString()]");
        }

    }

    private static void objectArrayAppend(StringBuilder sbuf, Object[] a, Map<Object[], Object> seenMap) {
        sbuf.append('[');
        if (!seenMap.containsKey(a)) {
            seenMap.put(a, null);
            final int len = a.length;
            for (int i = 0; i < len; i++) {
                deeplyAppendParameter(sbuf, a[i], seenMap);
                if (i != len - 1)
                    sbuf.append(", ");
            }
            // allow repeats in siblings
            seenMap.remove(a);
        } else {
            sbuf.append("...");
        }
        sbuf.append(']');
    }

    private static void booleanArrayAppend(StringBuilder sbuf, boolean[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1)
                sbuf.append(", ");
        }
        sbuf.append(']');
    }

    private static void byteArrayAppend(StringBuilder sbuf, byte[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1)
                sbuf.append(", ");
        }
        sbuf.append(']');
    }

    private static void charArrayAppend(StringBuilder sbuf, char[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1)
                sbuf.append(", ");
        }
        sbuf.append(']');
    }

    private static void shortArrayAppend(StringBuilder sbuf, short[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1)
                sbuf.append(", ");
        }
        sbuf.append(']');
    }

    private static void intArrayAppend(StringBuilder sbuf, int[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1)
                sbuf.append(", ");
        }
        sbuf.append(']');
    }

    private static void longArrayAppend(StringBuilder sbuf, long[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1)
                sbuf.append(", ");
        }
        sbuf.append(']');
    }

    private static void floatArrayAppend(StringBuilder sbuf, float[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1)
                sbuf.append(", ");
        }
        sbuf.append(']');
    }

    private static void doubleArrayAppend(StringBuilder sbuf, double[] a) {
        sbuf.append('[');
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            sbuf.append(a[i]);
            if (i != len - 1)
                sbuf.append(", ");
        }
        sbuf.append(']');
    }
}
