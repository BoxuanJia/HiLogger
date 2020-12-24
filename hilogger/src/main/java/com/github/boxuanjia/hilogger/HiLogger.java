package com.github.boxuanjia.hilogger;


import ohos.hiviewdfx.HiLog;

import static com.github.boxuanjia.hilogger.Utils.checkNotNull;

/**
 * <pre>
 *  ┌────────────────────────────────────────────
 *  │ HILOGGER
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Standard logging mechanism
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ But more pretty, simple and powerful
 *  └────────────────────────────────────────────
 * </pre>
 *
 * <h3>How to use it</h3>
 * Initialize it first
 * <pre><code>
 *   HiLogger.addLogAdapter(new HarmonyLogAdapter());
 * </code></pre>
 * <p>
 * And use the appropriate static HiLogger methods.
 *
 * <pre><code>
 *   HiLogger.d("debug");
 *   HiLogger.e("error");
 *   HiLogger.w("warning");
 *   HiLogger.i("information");
 *   HiLogger.wtf("What a Terrible Failure");
 * </code></pre>
 *
 * <h3>String format arguments are supported</h3>
 * <pre><code>
 *   HiLogger.d("hello %s", "world");
 * </code></pre>
 *
 * <h3>Collections are support ed(only available for debug logs)</h3>
 * <pre><code>
 *   HiLogger.d(MAP);
 *   HiLogger.d(SET);
 *   HiLogger.d(LIST);
 *   HiLogger.d(ARRAY);
 * </code></pre>
 *
 * <h3>Json support (output will be in debug level)</h3>
 * <pre><code>
 *   HiLogger.org.json(JSON_CONTENT);
 * </code></pre>
 *
 * <h3>Customize HiLogger</h3>
 * Based on your needs, you can change the following settings:
 * <ul>
 *   <li>Different {@link LogAdapter}</li>
 *   <li>Different {@link FormatStrategy}</li>
 *   <li>Different {@link LogStrategy}</li>
 * </ul>
 *
 * @see LogAdapter
 * @see FormatStrategy
 * @see LogStrategy
 */
public final class HiLogger {

    public static final int DEBUG = HiLog.DEBUG;
    public static final int INFO = HiLog.INFO;
    public static final int WARN = HiLog.WARN;
    public static final int ERROR = HiLog.ERROR;
    public static final int FATAL = HiLog.FATAL;

    private static Printer printer = new LoggerPrinter();

    private HiLogger() {
        //no instance
    }

    public static void printer(Printer printer) {
        HiLogger.printer = checkNotNull(printer);
    }

    public static void addLogAdapter(LogAdapter adapter) {
        printer.addAdapter(checkNotNull(adapter));
    }

    public static void clearLogAdapters() {
        printer.clearLogAdapters();
    }

    /**
     * Given tag will be used as tag only once for this method call regardless of the tag that's been
     * set during initialization. After this invocation, the general tag that's been set will
     * be used for the subsequent log calls
     */
    public static Printer t(int domain, String tag) {
        return printer.t(domain, tag);
    }

    /**
     * General log function that accepts all configurations as parameter
     */
    public static void log(int priority, int domain, String tag, String message, Throwable throwable) {
        printer.log(priority, domain, tag, message, throwable);
    }

    public static void d(String message, Object... args) {
        printer.d(message, args);
    }

    public static void d(Object object) {
        printer.d(object);
    }

    public static void e(String message, Object... args) {
        printer.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        printer.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        printer.i(message, args);
    }

    public static void w(String message, Object... args) {
        printer.w(message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(String message, Object... args) {
        printer.wtf(message, args);
    }

    /**
     * Formats the given org.json content and print it
     */
    public static void json(String json) {
        printer.json(json);
    }
}
