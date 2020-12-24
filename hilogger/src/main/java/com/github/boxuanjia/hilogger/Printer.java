package com.github.boxuanjia.hilogger;


/**
 * A proxy interface to enable additional operations.
 * Contains all possible Log message usages.
 */
public interface Printer {

    void addAdapter(LogAdapter adapter);

    Printer t(int domain, String tag);

    void d(String message, Object... args);

    void d(Object object);

    void e(String message, Object... args);

    void e(Throwable throwable, String message, Object... args);

    void w(String message, Object... args);

    void i(String message, Object... args);

    void wtf(String message, Object... args);

    /**
     * Formats the given org.json content and print it
     */
    void json(String json);

    void log(int priority, int domain, String tag, String message, Throwable throwable);

    void clearLogAdapters();
}
