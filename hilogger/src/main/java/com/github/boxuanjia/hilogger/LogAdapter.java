package com.github.boxuanjia.hilogger;

/**
 * Provides a common interface to emits logs through. This is a required contract for HiLogger.
 *
 * @see HarmonyLogAdapter
 * @see DiskLogAdapter
 */
public interface LogAdapter {

    /**
     * Used to determine whether log should be printed out or not.
     *
     * @param priority is the log level e.g. DEBUG, WARNING
     * @param domain   is the given tag for the domain message
     * @param tag      is the given tag for the log message
     * @return is used to determine if log should printed.
     * If it is true, it will be printed, otherwise it'll be ignored.
     */
    boolean isLoggable(int priority, int domain, String tag);

    /**
     * Each log will use this pipeline
     *
     * @param priority is the log level e.g. DEBUG, WARNING
     * @param domain   is the given domain for the log domain.
     * @param tag      is the given tag for the log message.
     * @param message  is the given message for the log message.
     */
    void log(int priority, int domain, String tag, String message);
}